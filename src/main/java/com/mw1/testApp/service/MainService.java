package com.mw1.testApp.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    public void usingMemory(){
        List<byte[]> list = new ArrayList<>();
        int index = 1;
        int flag = 0
        while (flag <= 1000) {
            // 1MB each loop, 1 x 1024 x 1024 = 1048576
            byte[] b = new byte[1048576];
            list.add(b);
            Runtime rt = Runtime.getRuntime();
            if (flag%10 == 0){
                System.out.printf("[%d] free memory: %s%n", index++, rt.freeMemory());
            }
            flag++;
        }
    }

    }
}
