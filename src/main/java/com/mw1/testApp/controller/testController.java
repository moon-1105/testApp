package com.mw1.testApp.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping("/home")
    public String mainPage(Model model){
        model.addAttribute("data", "컨트롤러 데이터 테스트");
        /*
          resources/templates/ +{ViewName}+ .html
        */
        return "index";
    }
}
