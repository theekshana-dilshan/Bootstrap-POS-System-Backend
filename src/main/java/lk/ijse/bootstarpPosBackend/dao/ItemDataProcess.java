package lk.ijse.bootstarpPosBackend.dao;

import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public final class ItemDataProcess implements ItemData{

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
    public boolean saveItem(ItemDTO itemDTO, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, itemDTO.getItemCode());
            ps.setString(2, itemDTO.getItemName());
            ps.setString(3, itemDTO.getQtyOnHand());
            ps.setString(4, itemDTO.getItemPrice());
            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteItem(String itemId, Connection connection) {
        try {
            var ps = connection.prepareStatement(DELETE_ITEM);
            ps.setString(1, itemId);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public boolean updateItem(String itemId, ItemDTO updatedItem, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM);
            ps.setString(1, updatedItem.getItemName());
            ps.setString(2, updatedItem.getQtyOnHand());
            ps.setString(3, updatedItem.getItemPrice());
            ps.setString(4, itemId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
