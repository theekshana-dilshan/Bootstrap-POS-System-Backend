package lk.ijse.bootstarpPosBackend.bo.custom.impl;

import lk.ijse.bootstarpPosBackend.bo.custom.ItemBO;
import lk.ijse.bootstarpPosBackend.dao.DAOFactory;
import lk.ijse.bootstarpPosBackend.dao.custom.CustomerDAO;
import lk.ijse.bootstarpPosBackend.dao.custom.ItemDAO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;
import lk.ijse.bootstarpPosBackend.entity.Customer;
import lk.ijse.bootstarpPosBackend.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    Connection connection;

    ItemDAO itemDAO= (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public List<ItemDTO> getAllItem(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS=new ArrayList<>();
        ArrayList<Item>items=itemDAO.getAll(connection);
        for (Item item:items) {
            itemDTOS.add(new ItemDTO(item.getItemCode(),item.getItemName(),item.getQtyOnHand(),item.getItemPrice()));
        }
        return itemDTOS;
    }

    @Override
    public ItemDTO getItem(String itemId, Connection connection) throws SQLException {
        Item item = itemDAO.getItem(itemId, connection);
        ItemDTO dto = new ItemDTO(item.getItemCode(), item.getItemName(), item.getQtyOnHand(), item.getItemPrice());
        return dto;
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(itemDTO.getItemCode(),itemDTO.getItemName(),itemDTO.getQtyOnHand(),itemDTO.getItemPrice()), connection);
    }

    @Override
    public boolean deleteItem(String itemId, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemId,connection);
    }

    @Override
    public boolean updateItem(String itemId, ItemDTO updatedItem, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(updatedItem.getItemCode(),updatedItem.getItemName(),updatedItem.getQtyOnHand(),updatedItem.getItemPrice()), connection);
    }

    @Override
    public String getLastItemId(Connection connection) throws SQLException {
        String lastId = null;
        String sql = "SELECT itemCode FROM item ORDER BY itemCode DESC LIMIT 1";

        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet resultSet = pst.executeQuery()) {

            if (resultSet.next()) {
                lastId = resultSet.getString("itemCode");
            }
        }

        return lastId;
    }

    @Override
    public ItemDTO getItemByName(String itemName, Connection connection) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item WHERE itemName = ?";
        ItemDTO item = null;

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, itemName);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    item = new ItemDTO(
                            resultSet.getString("itemCode"),
                            resultSet.getString("itemName"),
                            resultSet.getString("qtyOnHand"),
                            resultSet.getString("itemPrice")
                    );
                }
            }
        }

        return item;
    }
}
