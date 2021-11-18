package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.Category;
import cut.food.fooddelivery.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class FrontPageController {
    private final CategoryService categoryService;

    @GetMapping(path = "/index")
    public String index(){
        return "welcome";
    }

    @GetMapping(path = "/")
    public String welcome(){
        return "welcome";
    }
    @GetMapping(path = "/expo")
    public String experiment(){
        return "expriment";
    }

    @GetMapping("/sign-up")
    public String register(){
        return "signup";
    }
    @GetMapping(path="/login")
    public String login(){
        return "login";
    }
}
