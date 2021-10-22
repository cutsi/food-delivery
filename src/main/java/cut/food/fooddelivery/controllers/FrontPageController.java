package cut.food.fooddelivery.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class FrontPageController {
    @GetMapping(path = "/index")
    public String index(){
        return "index";
    }
}
