package lk.ijse.bootstarpPosBackend.dao;

import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface ItemData permits ItemDataProcess {
    ItemDTO getItem(String itemId, Connection connection) throws SQLException;
    boolean saveItem(ItemDTO itemDTO,Connection connection);
    boolean deleteItem(String itemId,Connection connection);
    boolean updateItem(String itemId,ItemDTO updatedItem,Connection connection);
}
