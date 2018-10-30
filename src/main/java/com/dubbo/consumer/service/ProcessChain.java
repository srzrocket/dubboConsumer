package com.dubbo.consumer.service;

import com.dubbo.consumer.http.HttpResponse;
import com.dubbo.consumer.service.impl.MsgProcessChain;

public interface ProcessChain
{
    /**
     * 添加责任链
     * @param process
     * @return
     */
    public MsgProcessChain addChain(Process process);
    /**
     * 执行处理
     * @param msg
     */
    public HttpResponse process(String msg);
}
