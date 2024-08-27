package lk.ijse.bootstarpPosBackend.bo.custom;

import lk.ijse.bootstarpPosBackend.bo.SuperBO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {

    List<CustomerDTO> getAllCustomer(Connection connection) throws SQLException, ClassNotFoundException;
    CustomerDTO getCustomer(String customerId, Connection connection) throws SQLException;
    boolean saveCustomer(CustomerDTO customerDTO,Connection connection) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String customerId,Connection connection) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(String customerId,CustomerDTO updatedCustomer,Connection connection) throws SQLException, ClassNotFoundException;
}
