package lk.ijse.bootstarpPosBackend.dao.custom.impl;

import lk.ijse.bootstarpPosBackend.dao.custom.ItemDAO;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;
import lk.ijse.bootstarpPosBackend.entity.Customer;
import lk.ijse.bootstarpPosBackend.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public final class ItemDAOImpl implements ItemDAO {

    static String GET_ALL = "SELECT * FROM item";
    static String SAVE_ITEM = "INSERT INTO item (itemCode,itemName,qtyOnHand,itemPrice) VALUES (?,?,?,?)";
    static String GET_ITEM = "SELECT * FROM item WHERE itemCode=?";
    static String UPDATE_ITEM = "UPDATE item SET itemName=?,qtyOnHand=?,itemPrice=? WHERE itemCode=?";
    static String DELETE_ITEM = "DELETE FROM item WHERE itemCode=?";

    @Override
    public ItemDTO getItem(String itemId, Connection connection) throws SQLException {
        var itemDTO = new ItemDTO();
        try {
            var ps = connection.prepareStatement(GET_ITEM);
            ps.setString(1, itemId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                itemDTO.setItemCode(resultSet.getString("itemCode"));
                itemDTO.setItemName(resultSet.getString("itemName"));
                itemDTO.setQtyOnHand(resultSet.getString("qtyOnHand"));
                itemDTO.setItemPrice(resultSet.getString("itemPrice"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return itemDTO;
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        var ps = connection.prepareStatement(GET_ALL);
        var rst = ps.executeQuery();
        ArrayList<Item> getAllItems=new ArrayList<>();
        while (rst.next()){
            Item entity=new Item(rst.getString("itemCode"), rst.getString("itemName"), rst.getString("qtyOnHand"), rst.getString("itemPrice"));
            getAllItems.add(entity);
        }
        return getAllItems;
    }

    @Override
    public boolean save(Item dto, Connection connection) throws SQLException, ClassNotFoundException {
        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, dto.getItemCode());
            ps.setString(2, dto.getItemName());
            ps.setString(3, dto.getQtyOnHand());
            ps.setString(4, dto.getItemPrice());
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean update(Item dto, Connection connection) throws SQLException, ClassNotFoundException {
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM);
            ps.setString(1, dto.getItemName());
            ps.setString(2, dto.getQtyOnHand());
            ps.setString(3, dto.getItemPrice());
            ps.setString(4, dto.getItemCode());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        try {
            var ps = connection.prepareStatement(DELETE_ITEM);
            ps.setString(1, id);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
