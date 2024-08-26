package lk.ijse.bootstarpPosBackend.bo.custom;

import lk.ijse.bootstarpPosBackend.bo.SuperBO;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemBO extends SuperBO {
    ItemDTO getItem(String itemId, Connection connection) throws SQLException;
    boolean saveItem(ItemDTO itemDTO,Connection connection);
    boolean deleteItem(String itemId,Connection connection);
    boolean updateItem(String itemId,ItemDTO updatedItem,Connection connection);
}
