package lk.ijse.bootstarpPosBackend.bo;

import lk.ijse.bootstarpPosBackend.bo.custom.impl.CustomerBOImpl;
import lk.ijse.bootstarpPosBackend.bo.custom.impl.ItemBOImpl;
import lk.ijse.bootstarpPosBackend.bo.custom.impl.OrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBOFactory(){
        return (boFactory==null)?boFactory=new BOFactory():boFactory;
    }
    public enum BOTypes{
        CUSTOMER,ITEM,ORDER
    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            default:
                return null;
        }
    }
}
