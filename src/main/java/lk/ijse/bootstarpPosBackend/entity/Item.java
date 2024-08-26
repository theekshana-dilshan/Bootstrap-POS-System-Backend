package lk.ijse.bootstarpPosBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    private String itemCode;
    private String itemName;
    private String qtyOnHand;
    private String itemPrice;
}
