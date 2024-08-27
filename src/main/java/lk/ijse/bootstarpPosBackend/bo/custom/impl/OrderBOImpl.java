package lk.ijse.bootstarpPosBackend.bo.custom.impl;

import lk.ijse.bootstarpPosBackend.bo.custom.OrderBO;
import lk.ijse.bootstarpPosBackend.dao.DAOFactory;
import lk.ijse.bootstarpPosBackend.dao.custom.ItemDAO;
import lk.ijse.bootstarpPosBackend.dao.custom.OrderDAO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;
import lk.ijse.bootstarpPosBackend.dto.OrderDTO;
import lk.ijse.bootstarpPosBackend.entity.Customer;
import lk.ijse.bootstarpPosBackend.entity.Item;
import lk.ijse.bootstarpPosBackend.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    Connection connection;
    OrderDAO orderDAO= (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    @Override
    public List<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> orderDTOS=new ArrayList<>();
        ArrayList<Order>orders=orderDAO.getAll(connection);
        for (Order order:orders) {
            orderDTOS.add(new OrderDTO(order.getOrderId(),order.getOrderDate(),order.getOrderCustomer(),order.getDiscount(),order.getSubTotal()));
        }
        return orderDTOS;
    }

    @Override
    public OrderDTO getOrders(String orderId, Connection connection) throws SQLException {
        Order order = orderDAO.getOrders(orderId, connection);
        OrderDTO dto = new OrderDTO(order.getOrderId(), order.getOrderDate(), order.getOrderCustomer(), order.getDiscount(), order.getSubTotal());
        return dto;
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new Order(orderDTO.getOrderId(),orderDTO.getOrderDate(),orderDTO.getOrderCustomer(),orderDTO.getDiscount(),orderDTO.getSubTotal()), connection);
    }

    @Override
    public boolean deleteOrder(String orderId, Connection connection) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(orderId,connection);
    }

    @Override
    public boolean updateOrder(String orderId, OrderDTO updatedOrder, Connection connection) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new Order(updatedOrder.getOrderId(),updatedOrder.getOrderDate(),updatedOrder.getOrderCustomer(),updatedOrder.getDiscount(),updatedOrder.getSubTotal()), connection);
    }
}
