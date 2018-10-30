package com.dubbo.consumer.http;

import java.util.List;

public class HttpProviderConf
{
    /**
     * 提供http访问的包
     */
    private List<String> usePackage ;

    public List<String> getUsePackage()
    {
        return usePackage;
    }

    public void setUsePackage(List<String> usePackage)
    {
        this.usePackage = usePackage;
    }
}
