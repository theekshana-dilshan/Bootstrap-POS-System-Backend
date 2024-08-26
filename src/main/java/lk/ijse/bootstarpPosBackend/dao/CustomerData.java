package lk.ijse.bootstarpPosBackend.dao;

import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface CustomerData permits CustomerDataProcess {
    CustomerDTO getCustomer(String customerId, Connection connection) throws SQLException;
    boolean saveCustomer(CustomerDTO customerDTO,Connection connection);
    boolean deleteCustomer(String customerId,Connection connection);
    boolean updateCustomer(String customerId,CustomerDTO updatedCustomer,Connection connection);
}
