package lk.ijse.bootstarpPosBackend.dao;

import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderData {
    OrderDTO getOrders(String orderId, Connection connection) throws SQLException;
    boolean saveOrder(OrderDTO orderDTO,Connection connection);
    boolean deleteOrder(String orderId,Connection connection);
    boolean updateOrder(String orderId,OrderDTO updatedOrder,Connection connection);
}
