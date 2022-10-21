package com.mw1.testApp.controller;
import com.mw1.testApp.service.MainService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Log4j2
public class MainController {
    @RequestMapping("/index")
    public String testPage(){
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(Model model){
        model.addAttribute("data", "JDK 11 테스트 페이지 입니다" );
        return "main";
    }

    @RequestMapping(value = "/memory", method = RequestMethod.GET)
    public String memoryTest(Model model){
        MainService sv = new MainService();
        sv.usingMemory();
        model.addAttribute("data", "메모리 테스트 완료" );
        return "memory";
    }
}
