package myssm.baseDao;

/**
 * @title:DAOException
 * @Author Ice
 * @Date: 2022/9/22 18:03
 * @Version 1.0
 */

public class DAOException extends RuntimeException{
    public DAOException(String message){
        super(message);
    }
}