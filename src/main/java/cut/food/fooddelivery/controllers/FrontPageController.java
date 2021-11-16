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
    @GetMapping(path = "/general")
    public String general(){
        return "general";
    }
    @PostMapping("saveCat")
    public String saveCat(){
        Category category = new Category("heroku test");
        categoryService.save(category);
        return "index";
    }
    @GetMapping("getCat")
    public String getCat(){
        List<Category> categoryList = categoryService.getAll();
        for (Category cat:categoryList
             ) {
            System.out.println(cat.getCategoryName());
        }
        return "index";
    }
    @GetMapping("registracija")
    public String registracija(){
        return "signup";
    }
}
