package lk.ijse.bootstarpPosBackend.dao.custom;

import lk.ijse.bootstarpPosBackend.dao.CrudDAO;
import lk.ijse.bootstarpPosBackend.dto.OrderDTO;
import lk.ijse.bootstarpPosBackend.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order> {
    OrderDTO getOrders(String orderId, Connection connection) throws SQLException;
}
