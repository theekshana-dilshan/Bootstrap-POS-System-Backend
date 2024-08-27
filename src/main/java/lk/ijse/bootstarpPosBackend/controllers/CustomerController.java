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
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {
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
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            /*customerDTO.setCustomerId(UtilProcess.generateId());*/
            var saveData = new CustomerBOImpl();
            if (saveData.saveCustomer(customerDTO, connection)){
                writer.write("Customer saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Save customer failed");
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
            var customerId = req.getParameter("customerId");
            Jsonb jsonb = JsonbBuilder.create();
            var customerBoImpl = new CustomerBOImpl();
            var updatedCustomer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            if(customerBoImpl.updateCustomer(customerId,updatedCustomer,connection)){
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
        String customerId = req.getParameter("customerId");
        var customerBoImpl = new CustomerBOImpl();

        try (var writer = resp.getWriter()) {
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();

            if (customerId == null) {
                List<CustomerDTO> customers = customerBoImpl.getAllCustomer();
                jsonb.toJson(customers, writer);
            } else {
                CustomerDTO customer = customerBoImpl.getCustomer(customerId, connection);
                jsonb.toJson(customer, writer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var customerId = req.getParameter("customerId");
        try (var writer = resp.getWriter()){
            var customerBoImpl = new CustomerBOImpl();
            if(customerBoImpl.deleteCustomer(customerId, connection)){
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
