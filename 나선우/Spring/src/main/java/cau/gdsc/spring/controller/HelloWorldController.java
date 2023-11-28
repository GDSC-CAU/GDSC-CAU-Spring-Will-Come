package cau.gdsc.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
    @GetMapping("first")
    @ResponseBody
    public String index() {
        return "Hello, world!";
    }
}
