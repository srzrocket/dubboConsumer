package com.dubbo.consumer.http;

import java.io.Serializable;

public class HttpResponse implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4226619681590905197L;
    private boolean success;//成功标志

    private String code;//信息码

    private String description;//描述

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}
