package lk.ijse.bootstarpPosBackend.bo.custom;

import lk.ijse.bootstarpPosBackend.bo.SuperBO;
import lk.ijse.bootstarpPosBackend.dto.CustomerDTO;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {

    List<ItemDTO> getAllItem(Connection connection) throws SQLException, ClassNotFoundException;
    ItemDTO getItem(String itemId, Connection connection) throws SQLException;
    boolean saveItem(ItemDTO itemDTO,Connection connection) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String itemId,Connection connection) throws SQLException, ClassNotFoundException;
    boolean updateItem(String itemId,ItemDTO updatedItem,Connection connection) throws SQLException, ClassNotFoundException;
    String getLastItemId(Connection connection) throws SQLException;
    ItemDTO getItemByName(String itemName, Connection connection) throws SQLException, ClassNotFoundException;
}
