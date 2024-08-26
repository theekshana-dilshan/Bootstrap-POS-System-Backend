package lk.ijse.bootstarpPosBackend.dao;

import lk.ijse.bootstarpPosBackend.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.bootstarpPosBackend.dao.custom.impl.ItemDAOImpl;
import lk.ijse.bootstarpPosBackend.dao.custom.impl.OrderDAOImpl;

public class DAOFactory{
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER,ITEM,ORDER
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            default:
                return null;
        }
    }
}
