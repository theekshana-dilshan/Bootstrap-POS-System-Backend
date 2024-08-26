package lk.ijse.bootstarpPosBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO implements Serializable {
    private String itemCode;
    private String itemName;
    private String qtyOnHand;
    private String itemPrice;
}
