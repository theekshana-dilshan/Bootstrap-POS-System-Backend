package lk.ijse.bootstarpPosBackend.bo.custom.impl;

import lk.ijse.bootstarpPosBackend.bo.custom.CustomerBO;
import lk.ijse.bootstarpPosBackend.dao.DAOFactory;
import lk.ijse.bootstarpPosBackend.dao.custom.CustomerDAO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    Connection connection;

    CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public List<CustomerDTO> getAllCustomer(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOS=new ArrayList<>();
        ArrayList<Customer>customers=customerDAO.getAll(connection);
        for (Customer customer:customers) {
            customerDTOS.add(new CustomerDTO(customer.getCustomerId(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerSalary()));
        }
        return customerDTOS;
    }

    @Override
    public CustomerDTO getCustomer(String customerId, Connection connection) throws SQLException {
        Customer customer = customerDAO.getCustomer(customerId, connection);
        CustomerDTO dto = new CustomerDTO(customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerSalary());
        return dto;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getCustomerId(),customerDTO.getCustomerName(),customerDTO.getCustomerAddress(),customerDTO.getCustomerSalary()), connection);
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId,connection);
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO updatedCustomer, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(updatedCustomer.getCustomerId(),updatedCustomer.getCustomerName(),updatedCustomer.getCustomerAddress(),updatedCustomer.getCustomerSalary()), connection);
    }

    @Override
    public String getLastCustomerId(Connection connection) throws SQLException {
        String lastId = null;
        String sql = "SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1";

        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet resultSet = pst.executeQuery()) {

            if (resultSet.next()) {
                lastId = resultSet.getString("customerId");
            }
        }

        return lastId;
    }

    @Override
    public CustomerDTO getCustomerByName(String customerName, Connection connection) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer WHERE customerName = ?";
        CustomerDTO customer = null;

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, customerName);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    customer = new CustomerDTO(
                            resultSet.getString("customerId"),
                            resultSet.getString("customerName"),
                            resultSet.getString("customerAddress"),
                            resultSet.getString("customerSalary")
                    );
                }
            }
        }

        return customer;
    }
}
