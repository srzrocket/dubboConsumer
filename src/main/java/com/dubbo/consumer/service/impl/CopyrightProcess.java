package com.dubbo.consumer.service.impl;

import com.dubbo.consumer.service.Process;

public class CopyrightProcess implements Process
{

    @Override
    public void doProcess(String msg)
    {
        System.out.println(msg+"版权处理！");
    }

}
