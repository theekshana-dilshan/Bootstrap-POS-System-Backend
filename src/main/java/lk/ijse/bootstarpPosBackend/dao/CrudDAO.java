package lk.ijse.bootstarpPosBackend.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    ArrayList<T> getAll(Connection connection) throws SQLException, ClassNotFoundException;
    boolean save(T dto, Connection connection) throws SQLException, ClassNotFoundException;
    boolean update(T dto, Connection connection) throws SQLException, ClassNotFoundException;
    boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException;
}
