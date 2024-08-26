package lk.ijse.bootstarpPosBackend.bo.custom.impl;

import lk.ijse.bootstarpPosBackend.bo.custom.OrderBO;
import lk.ijse.bootstarpPosBackend.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {
    @Override
    public OrderDTO getOrders(String orderId, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO, Connection connection) {
        return false;
    }

    @Override
    public boolean deleteOrder(String orderId, Connection connection) {
        return false;
    }

    @Override
    public boolean updateOrder(String orderId, OrderDTO updatedOrder, Connection connection) {
        return false;
    }
}
