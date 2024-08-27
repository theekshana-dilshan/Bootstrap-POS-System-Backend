package lk.ijse.bootstarpPosBackend.dao.custom;

import lk.ijse.bootstarpPosBackend.dao.CrudDAO;
import lk.ijse.bootstarpPosBackend.dao.custom.impl.ItemDAOImpl;
import lk.ijse.bootstarpPosBackend.dto.ItemDTO;
import lk.ijse.bootstarpPosBackend.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item> {
    Item getItem(String itemId, Connection connection) throws SQLException;
}
