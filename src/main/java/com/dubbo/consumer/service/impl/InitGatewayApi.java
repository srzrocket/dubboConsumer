package com.dubbo.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import dubbo.face.dubboFaceOne.TestService;







public class InitGatewayApi
{
    @Autowired
    private TestService testService;
    public void setTestService(TestService testService)
    {
        this.testService = testService;
    }
    /**
     * 
     */
    public void contextInitialized() {
        testService.sayHellol("消费");
         System.out.println("消费成功1");
    }
}
