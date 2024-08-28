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
import lk.ijse.bootstarpPosBackend.bo.custom.impl.ItemBOImpl;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/item")
public class ItemController extends HttpServlet {
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
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            /*itemDTO.setItemCode(UtilProcess.generateId());*/
            var saveData = new ItemBOImpl();
            if (saveData.saveItem(itemDTO, connection)){
                writer.write("Item saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Save item failed");
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
            var itemId = req.getParameter("itemId");
            Jsonb jsonb = JsonbBuilder.create();
            var itemBoImpl = new ItemBOImpl();
            var updatedItem = jsonb.fromJson(req.getReader(), ItemDTO.class);
            if(itemBoImpl.updateItem(itemId,updatedItem,connection)){
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
        var itemId = req.getParameter("itemId");
        var itemName = req.getParameter("itemName");
        String action = req.getParameter("action");

        var itemBoImpl = new ItemBOImpl();
        try (var writer = resp.getWriter()){
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();

            if ("generateId".equals(action)) {
                // Assuming getLastCustomerId is a method in CustomerBOImpl that returns the last customer ID
                ItemBOImpl itemBO = new ItemBOImpl();
                String lastId = itemBO.getLastItemId(connection); // Retrieve the last ID from the database

                String newId;
                if (lastId == null) {
                    newId = "I00-001";
                } else {
                    String[] parts = lastId.split("-");
                    int lastNumber = Integer.parseInt(parts[1]);
                    newId = String.format("I00-%03d", lastNumber + 1);
                }

                resp.setContentType("application/json");
                resp.getWriter().write("{\"itemCode\":\"" + newId + "\"}");
            } else {
                if (itemId == null) {
                    List<ItemDTO> items = itemBoImpl.getAllItem(connection);
                    jsonb.toJson(items, writer);
                } else if (itemName != null) {
                    ItemDTO itemDTO = itemBoImpl.getItemByName(itemName, connection);
                    jsonb.toJson(itemDTO, writer);
                } else {
                    ItemDTO item = itemBoImpl.getItem(itemId, connection);
                    jsonb.toJson(item, writer);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var itemId = req.getParameter("itemCode");
        try (var writer = resp.getWriter()){
            var itemBoImpl = new ItemBOImpl();
            if(itemBoImpl.deleteItem(itemId, connection)){
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
