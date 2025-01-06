package project.springboot.ecom.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/health-check")
    public String health(){
        return "OK, Done ";
    }


}
