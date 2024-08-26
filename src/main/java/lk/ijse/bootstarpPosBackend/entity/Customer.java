package lk.ijse.bootstarpPosBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerSalary;
}
