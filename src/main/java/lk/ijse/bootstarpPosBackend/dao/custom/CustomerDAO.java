package lk.ijse.bootstarpPosBackend.dao.custom;

import lk.ijse.bootstarpPosBackend.dao.CrudDAO;
import lk.ijse.bootstarpPosBackend.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    Customer getCustomer(String customerId, Connection connection) throws SQLException;
}
