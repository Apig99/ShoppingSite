package apig.aramian.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private int id;
    private String name;
    private String price;
    private int quantity;
    private String image;


}
