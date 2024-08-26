package lk.ijse.bootstarpPosBackend.bo.custom;

import lk.ijse.bootstarpPosBackend.bo.SuperBO;
import lk.ijse.bootstarpPosBackend.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    OrderDTO getOrders(String orderId, Connection connection) throws SQLException;
    boolean saveOrder(OrderDTO orderDTO,Connection connection);
    boolean deleteOrder(String orderId,Connection connection);
    boolean updateOrder(String orderId,OrderDTO updatedOrder,Connection connection);
}
