package lk.ijse.bootstarpPosBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO implements Serializable {
    private String orderId;
    private String orderDate;
    private String orderCustomer;
    private String discount;
    private String subTotal;
}
