package com.dubbo.consumer.model;

import java.io.Serializable;

public class TestModel implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -1283118694467541335L;
    
    private String msg;

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

}
