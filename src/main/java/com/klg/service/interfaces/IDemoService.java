package com.klg.service.interfaces;

import com.klg.dao.entity.Demo;

/**
 * Created by yaphiss on 2018/9/17.
 */
public interface IDemoService {
    public Demo getUserByName(String name);

    public Demo insert(Demo demo);

    public void removeAllDemos();
}
