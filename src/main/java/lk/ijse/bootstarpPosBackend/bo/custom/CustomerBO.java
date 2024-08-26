package lk.ijse.bootstarpPosBackend.bo.custom;

import lk.ijse.bootstarpPosBackend.bo.SuperBO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    CustomerDTO getCustomer(String customerId, Connection connection) throws SQLException;
    boolean saveCustomer(CustomerDTO customerDTO,Connection connection);
    boolean deleteCustomer(String customerId,Connection connection);
    boolean updateCustomer(String customerId,CustomerDTO updatedCustomer,Connection connection);
}
