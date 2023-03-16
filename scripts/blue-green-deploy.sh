#!/bin/bash

# Variables
ECS_SERVICE_NAME="<ECS_SERVICE_NAME>"
TASK_FAMILY="<TASK_FAMILY>"
NEW_TASK_DEFINITION="<NEW_TASK_DEFINITION_ARN>"
CONTAINER_NAME="<CONTAINER_NAME>"
CONTAINER_PORT="<CONTAINER_PORT>"
LOAD_BALANCER_NAME="<LOAD_BALANCER_NAME>"
TARGET_GROUP_NAME="<TARGET_GROUP_NAME>"
ECS_CLUSTER="<ECS_CLUSTER>"
DESIRED_COUNT=2
OLD_TASK_DEFINITION=$(aws ecs describe-services --cluster $ECS_CLUSTER --services $ECS_SERVICE_NAME --query "services[0].taskDefinition" --output text)

# Create new task definition from latest revision
NEW_REVISION=$(aws ecs describe-task-definition --task-definition $TASK_FAMILY --query "taskDefinition.revision" --output text)
((NEW_REVISION+=1))
NEW_TASK_DEFINITION_ARN=$(aws ecs register-task-definition --cli-input-json file://task-def.json | jq -r '.taskDefinition.taskDefinitionArn')
aws ecs wait services-stable --cluster $ECS_CLUSTER --services $ECS_SERVICE_NAME
echo "New task definition created: $NEW_TASK_DEFINITION_ARN"

# Create new service with new task definition
aws ecs create-service \
  --cluster $ECS_CLUSTER \
  --service-name $ECS_SERVICE_NAME \
  --task-definition $NEW_TASK_DEFINITION_ARN \
  --load-balancers targetGroupArn=$TARGET_GROUP_NAME,containerName=$CONTAINER_NAME,containerPort=$CONTAINER_PORT \
  --desired-count $DESIRED_COUNT
aws ecs wait services-stable --cluster $ECS_CLUSTER --services $ECS_SERVICE_NAME
echo "New service created with new task definition: $NEW_TASK_DEFINITION_ARN"

# Update load balancer to route traffic to new service
aws elbv2 modify-rule \
  --rule-arn <RULE_ARN> \
  --actions Type=forward,TargetGroupArn=$TARGET_GROUP_NAME
echo "Load balancer rule updated to route traffic to new service"

# Deregister old task definition and update service to use new task definition
aws ecs update-service \
  --cluster $ECS_CLUSTER \
  --service $ECS_SERVICE_NAME \
  --task-definition $NEW_TASK_DEFINITION
aws ecs wait services-stable --cluster $ECS_CLUSTER --services $ECS_SERVICE_NAME
echo "Service updated to use new task definition: $NEW_TASK_DEFINITION"

# Wait for old tasks to be drained and stopped, then delete them
while [ $(aws ecs describe-services --cluster $ECS_CLUSTER --services $ECS_SERVICE_NAME --query "services[0].runningCount" --output text) -ne 0 ]
do
  echo "Waiting for old tasks to be drained and stopped"
  sleep 30
done
aws ecs update-service \
  --cluster $ECS_CLUSTER \
  --service $ECS_SERVICE_NAME \
  --desired-count 0
aws ecs wait services-stable --cluster $ECS_CLUSTER --services $ECS_SERVICE_NAME
aws ecs delete-task-definition --task-definition $OLD_TASK_DEFINITION
echo "Old task definition deleted: $OLD_TASK_DEFINITION"
