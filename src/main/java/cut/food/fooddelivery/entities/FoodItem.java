package cut.food.fooddelivery.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class FoodItem {
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    private Long id;
    private String name;
    private String price;
    private String image;
    private String info;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToMany(mappedBy = "menu")
    private Set<Restaurant> restaurants = new HashSet<>();
    @ManyToMany(mappedBy = "foodItems")
    private Set<Condiments> condiments = new HashSet<>();
    @ManyToMany(mappedBy = "foodPortionItems")
    private List<PortionSize> portionSizes = new ArrayList<>();
    public int getPriceInt(){
        return Integer.valueOf(price);
    }

    public List<PortionSize> getPortionSizes(){
        List<PortionSize> portionSizeList = new ArrayList<>();
        List<PortionSize> portionSizeListFinal = new ArrayList<>();
        for (PortionSize ps:portionSizes) {
            portionSizeList.add(ps);
        }
        int min = 0;
        for (int i = 0; i < portionSizeList.size(); i++){
            for(int j = 0; j < portionSizeList.size(); i++){
                if(portionSizeList.get(j).getId() < portionSizeList.get(i).getId()){
                    min = j;
                }
            }
            portionSizeListFinal.add(portionSizeList.get(min));
            portionSizeList.remove(min);
        }
     return portionSizeListFinal;
    }
}
