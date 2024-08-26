package lk.ijse.bootstarpPosBackend.dao;

import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public final class CustomerDataProcess implements CustomerData{

    static String SAVE_CUSTOMER = "INSERT INTO customer (customerId,customerName,customerAddress,customerSalary) VALUES (?,?,?,?)";
    static String GET_CUSTOMER = "SELECT * FROM customer WHERE customerId=?";
    static String UPDATE_CUSTOMER = "UPDATE customer SET customerName=?,customerAddress=?,customerSalary=? WHERE customerId=?";
    static String DELETE_CUSTOMER = "DELETE FROM customer WHERE customerId=?";

    @Override
    public CustomerDTO getCustomer(String customerId, Connection connection) throws SQLException {
        var customerDTO = new CustomerDTO();
        try {
            var ps = connection.prepareStatement(GET_CUSTOMER);
            ps.setString(1, customerId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                customerDTO.setCustomerId(resultSet.getString("customerId"));
                customerDTO.setCustomerName(resultSet.getString("customerName"));
                customerDTO.setCustomerAddress(resultSet.getString("customerAddress"));
                customerDTO.setCustomerSalary(resultSet.getString("customerSalary"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customerDTO;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, customerDTO.getCustomerId());
            ps.setString(2, customerDTO.getCustomerName());
            ps.setString(3, customerDTO.getCustomerAddress());
            ps.setString(4, customerDTO.getCustomerSalary());
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) {
        try {
            var ps = connection.prepareStatement(DELETE_CUSTOMER);
            ps.setString(1, customerId);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO updatedCustomer, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER);
            ps.setString(1, updatedCustomer.getCustomerName());
            ps.setString(2, updatedCustomer.getCustomerAddress());
            ps.setString(3, updatedCustomer.getCustomerSalary());
            ps.setString(4, customerId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
