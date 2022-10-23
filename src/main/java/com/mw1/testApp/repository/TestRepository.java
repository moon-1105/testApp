package com.mw1.testApp.repository;

import com.mw1.testApp.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public class TestRepository {
    public interface InstanceRepository extends JpaRepository<TestEntity, Integer> {

    }
}
