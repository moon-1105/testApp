package com.mw1.testApp.service;

import groovy.util.logging.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MainService {
    public void usingMemory(){
        log.info("Memory TEST start");
        log.info("Total Memory : 자바 가상 머신(JVM)의 모든 메모리 양");
        log.info("Max Memory : 가상머신이 사용하려고 시도했던 가장 큰 메모리 양  -Xmx 값과 같다");
        log.info("Free Memory : 자바 가상머신 내의 남은 메모리의 양");
        log.info("==========================================");
        List<byte[]> list = new ArrayList<>();
        int index = 1;
        // 1MB each loop, 1 x 1024 x 1024 = 1048576
        while(true){
            byte[] b = new byte[1048576];
            list.add(b);
            Runtime rt = Runtime.getRuntime();
            //자바 가상 머신(JVM)의 모든 메모리 양
            log.info("[{}] Total memory: {}MB", index, rt.totalMemory() / (1024 * 1024));
            //가상머신이 사용하려고 시도했던 가장 큰 메모리 양  -Xmx 값과 같다
            log.info("[{}] Max memory: {}MB", index, rt.maxMemory() / (1024 * 1024));
            //자바 가상머신 내의 남은 메모리의 양
            log.info("[{}] Free memory: {}MB", index++, rt.freeMemory() / (1024 * 1024));
            log.info("============================================");
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void stopUsingMemory(){
        Runtime.getRuntime().halt(0);
        return;
    }
}
