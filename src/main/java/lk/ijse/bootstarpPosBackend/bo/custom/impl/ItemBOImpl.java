package lk.ijse.bootstarpPosBackend.bo.custom.impl;

import lk.ijse.bootstarpPosBackend.bo.custom.ItemBO;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemBOImpl implements ItemBO {

    @Override
    public ItemDTO getItem(String itemId, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) {
        return false;
    }

    @Override
    public boolean deleteItem(String itemId, Connection connection) {
        return false;
    }

    @Override
    public boolean updateItem(String itemId, ItemDTO updatedItem, Connection connection) {
        return false;
    }
}
