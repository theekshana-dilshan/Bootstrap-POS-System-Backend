package lk.ijse.bootstarpPosBackend.dao.custom.impl;

import lk.ijse.bootstarpPosBackend.dao.custom.CustomerDAO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class CustomerDAOImpl implements CustomerDAO {

    static String GET_ALL = "SELECT * FROM customer";
    static String SAVE_CUSTOMER = "INSERT INTO customer (customerId,customerName,customerAddress,customerSalary) VALUES (?,?,?,?)";
    static String GET_CUSTOMER = "SELECT * FROM customer WHERE customerId=?";
    static String UPDATE_CUSTOMER = "UPDATE customer SET customerName=?,customerAddress=?,customerSalary=? WHERE customerId=?";
    static String DELETE_CUSTOMER = "DELETE FROM customer WHERE customerId=?";

    @Override
    public Customer getCustomer(String customerId, Connection connection) throws SQLException {
        var customer = new Customer();
        try {
            var ps = connection.prepareStatement(GET_CUSTOMER);
            ps.setString(1, customerId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getString("customerId"));
                customer.setCustomerName(resultSet.getString("customerName"));
                customer.setCustomerAddress(resultSet.getString("customerAddress"));
                customer.setCustomerSalary(resultSet.getString("customerSalary"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        var ps = connection.prepareStatement(GET_ALL);
        var rst = ps.executeQuery();
        ArrayList<Customer> getAllCustomer=new ArrayList<>();
        while (rst.next()){
            Customer entity=new Customer(rst.getString("customerId"), rst.getString("customerName"), rst.getString("customerAddress"), rst.getString("customerSalary"));
            getAllCustomer.add(entity);
        }
        return getAllCustomer;
    }

    @Override
    public boolean save(Customer dto, Connection connection) throws SQLException, ClassNotFoundException {
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, dto.getCustomerId());
            ps.setString(2, dto.getCustomerName());
            ps.setString(3, dto.getCustomerAddress());
            ps.setString(4, dto.getCustomerSalary());
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean update(Customer dto, Connection connection) throws SQLException, ClassNotFoundException {
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER);
            ps.setString(1, dto.getCustomerName());
            ps.setString(2, dto.getCustomerAddress());
            ps.setString(3, dto.getCustomerSalary());
            ps.setString(4, dto.getCustomerId());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        try {
            var ps = connection.prepareStatement(DELETE_CUSTOMER);
            ps.setString(1, id);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
