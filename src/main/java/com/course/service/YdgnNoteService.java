package com.course.service;

import org.springframework.stereotype.Service;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法 胰岛功能
@Service
public class YdgnNoteService {

    public void ydgnNote(){
        System.out.println("+++++ydgnNote积分计算方法执行+++++");
    }

}
