package com.dubbo.consumer.service.impl;

import com.dubbo.consumer.service.Process;

public class WordProcess implements Process
{

    @Override
    public void doProcess(String msg)
    {
        System.out.println(msg+"敏感词处理！");
    }

}
