package cut.food.fooddelivery.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
@AllArgsConstructor
public class AdminController {
    @GetMapping(path = "/")
    public String getAdmin(){
        return "admin";
    }
}

//TODO 1. vidit za security endpointe kako ih poredat
//TODO 2. dizajnirat admin stranicu
//TODO 3. vidit kako dizajnirat za restoran, nacrtat s
