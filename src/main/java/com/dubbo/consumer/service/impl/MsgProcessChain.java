package com.dubbo.consumer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dubbo.consumer.http.HttpResponse;
import com.dubbo.consumer.model.TestModel;
import com.dubbo.consumer.service.Process;
import com.dubbo.consumer.service.ProcessChain;
import com.dubbo.consumer.util.JSonTool;
@Service("processChain")
public class MsgProcessChain implements ProcessChain
{
    private List<Process> chains = new ArrayList<>() ;
    /**
     * 添加责任链
     * @param process
     * @return
     */
    public MsgProcessChain addChain(Process process){
        chains.add(process) ;
        return this ;
    }

    /**
     * 执行处理
     * @param msg
     * @return 
     */
    public HttpResponse process(String msg){
        TestModel req = JSonTool.toObject(msg, TestModel.class);
        System.out.println(req.getMsg()+"外部调用成功！");
        for (Process chain : chains) {
            chain.doProcess(msg);
        }
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode("100001");
        httpResponse.setDescription("请求地址成功");
        httpResponse.setSuccess(true);
        return httpResponse;
    }
}
