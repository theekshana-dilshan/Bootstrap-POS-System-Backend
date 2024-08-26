package lk.ijse.bootstarpPosBackend.dao.custom.impl;

import lk.ijse.bootstarpPosBackend.dao.custom.OrderDAO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.OrderDTO;
import lk.ijse.bootstarpPosBackend.entity.Customer;
import lk.ijse.bootstarpPosBackend.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    static String GET_ALL = "SELECT * FROM orders";
    static String SAVE_ORDER = "INSERT INTO orders (orderId,orderDate,orderCustomer,discount,subTotal) VALUES (?,?,?,?,?)";
    static String GET_ORDER = "SELECT * FROM orders WHERE orderId=?";
    static String UPDATE_ORDER = "UPDATE orders SET orderDate=?,orderCustomer=?,discount=?,subTotal=? WHERE orderId=?";
    static String DELETE_ORDER = "DELETE FROM orders WHERE orderId=?";

    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        var ps = connection.prepareStatement(GET_ALL);
        var rst = ps.executeQuery();
        ArrayList<Order> getAllOrders=new ArrayList<>();
        while (rst.next()){
            Order entity=new Order(rst.getString("orderId"), rst.getString("orderDate"), rst.getString("orderCustomer"), rst.getString("discount"), rst.getString("subTotal"));
            getAllOrders.add(entity);
        }
        return getAllOrders;
    }

    @Override
    public boolean save(Order dto, Connection connection) throws SQLException, ClassNotFoundException {
        try {
            var ps = connection.prepareStatement(SAVE_ORDER);
            ps.setString(1, dto.getOrderId());
            ps.setString(2, dto.getOrderDate());
            ps.setString(3, dto.getOrderCustomer());
            ps.setString(4, dto.getDiscount());
            ps.setString(5, dto.getSubTotal());
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean update(Order dto, Connection connection) throws SQLException, ClassNotFoundException {
        try {
            var ps = connection.prepareStatement(UPDATE_ORDER);
            ps.setString(1, dto.getOrderDate());
            ps.setString(2, dto.getOrderCustomer());
            ps.setString(3, dto.getDiscount());
            ps.setString(4, dto.getSubTotal());
            ps.setString(5, dto.getOrderId());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        try {
            var ps = connection.prepareStatement(DELETE_ORDER);
            ps.setString(1, id);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public OrderDTO getOrders(String orderId, Connection connection) throws SQLException {
        var orderDTO = new OrderDTO();
        try {
            var ps = connection.prepareStatement(GET_ORDER);
            ps.setString(1, orderId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                orderDTO.setOrderId(resultSet.getString("customerId"));
                orderDTO.setOrderDate(resultSet.getString("customerName"));
                orderDTO.setOrderCustomer(resultSet.getString("customerAddress"));
                orderDTO.setDiscount(resultSet.getString("customerSalary"));
                orderDTO.setSubTotal(resultSet.getString("customerSalary"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDTO;
    }
}
