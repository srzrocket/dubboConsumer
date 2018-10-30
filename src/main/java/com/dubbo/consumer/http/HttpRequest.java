package com.dubbo.consumer.http;

public class HttpRequest
{
    private String param ;//入参
    private String service ;//请求service
    private String method ;//请求方法
    
    public String getParam()
    {
        return param;
    }
    public void setParam(String param)
    {
        this.param = param;
    }
    public String getService()
    {
        return service;
    }
    public void setService(String service)
    {
        this.service = service;
    }
    public String getMethod()
    {
        return method;
    }
    public void setMethod(String method)
    {
        this.method = method;
    }
}
