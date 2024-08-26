package lk.ijse.bootstarpPosBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private String orderId;
    private String orderDate;
    private String orderCustomer;
    private String discount;
    private String subTotal;
}
