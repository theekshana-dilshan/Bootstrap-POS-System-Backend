package lk.ijse.bootstarpPosBackend.controllers;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.bootstarpPosBackend.bo.custom.impl.CustomerBOImpl;
import lk.ijse.bootstarpPosBackend.bo.custom.impl.OrderBOImpl;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.OrderDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/orders")
public class OrderController extends HttpServlet {
    Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:/comp/env/jdbc/bootstrap-pos-system");

            this.connection =  pool.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        // Persist Data
        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
            /*customerDTO.setCustomerId(UtilProcess.generateId());*/
            var saveData = new OrderBOImpl();
            if (saveData.saveOrder(orderDTO, connection)){
                writer.write("Order saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Save order failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (JsonException | SQLException | ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try (var writer = resp.getWriter()){
            var orderId = req.getParameter("orderId");
            Jsonb jsonb = JsonbBuilder.create();
            var orderBoImpl = new OrderBOImpl();
            var updatedOrder = jsonb.fromJson(req.getReader(), OrderDTO.class);
            if(orderBoImpl.updateOrder(orderId,updatedOrder,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Update Failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var orderId = req.getParameter("orderId");
        String action = req.getParameter("action");
        var orderBoImpl = new OrderBOImpl();

        try (var writer = resp.getWriter()){
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();

            if ("generateId".equals(action)) {
                OrderBOImpl orderBO = new OrderBOImpl();
                String lastId = orderBO.getLastOrderId(connection);

                String newId;
                if (lastId == null) {
                    newId = "O00-001";
                } else {
                    String[] parts = lastId.split("-");
                    int lastNumber = Integer.parseInt(parts[1]);
                    newId = String.format("O00-%03d", lastNumber + 1);
                }

                resp.setContentType("application/json");
                resp.getWriter().write("{\"orderId\":\"" + newId + "\"}");
            } else {
                if (orderId == null) {
                    List<OrderDTO> orders = orderBoImpl.getAllOrders(connection);
                    jsonb.toJson(orders, writer);
                } else {
                    var order = orderBoImpl.getOrders(orderId, connection);
                    System.out.println(order);
                    jsonb.toJson(order, writer);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var orderId = req.getParameter("orderId");
        try (var writer = resp.getWriter()){
            var orderBoImpl = new OrderBOImpl();
            if(orderBoImpl.deleteOrder(orderId, connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Delete Failed");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
