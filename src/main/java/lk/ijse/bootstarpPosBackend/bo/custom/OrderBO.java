package lk.ijse.bootstarpPosBackend.bo.custom;

import lk.ijse.bootstarpPosBackend.bo.SuperBO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {

    List<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException;
    OrderDTO getOrders(String orderId, Connection connection) throws SQLException;
    boolean saveOrder(OrderDTO orderDTO,Connection connection) throws SQLException, ClassNotFoundException;
    boolean deleteOrder(String orderId,Connection connection) throws SQLException, ClassNotFoundException;
    boolean updateOrder(String orderId,OrderDTO updatedOrder,Connection connection) throws SQLException, ClassNotFoundException;
    String getLastOrderId(Connection connection) throws SQLException;
}
