package lk.ijse.bootstarpPosBackend.bo.custom.impl;

import lk.ijse.bootstarpPosBackend.bo.custom.CustomerBO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
    @Override
    public CustomerDTO getCustomer(String customerId, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) {
        return false;
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO updatedCustomer, Connection connection) {
        return false;
    }
}
