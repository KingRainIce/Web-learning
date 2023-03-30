package myssm.myspringmvc;

/**
 * @title:DispatcherServletException
 * @Author Ice
 * @Date: 2022/9/22 17:58
 * @Version 1.0
 */

public class DispatcherServletException extends RuntimeException{
    public DispatcherServletException(String msg){
        super(msg);
    }
}