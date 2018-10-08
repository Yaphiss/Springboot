package com.klg.service;

import com.klg.dao.DemoDao;
import com.klg.dao.entity.Demo;
import com.klg.service.interfaces.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yaphiss on 2018/9/17.
 */
@Service
public class DemoServiceImpl extends BaseExtends<Demo> implements IDemoService {

    @Autowired
    private DemoDao demoDao;

    @Override
    public Demo getUserByName(String name) {
        Demo demo = demoDao.findByName(name);
        return demo;
    }

    @Override
    public Demo insert(Demo demo) {
        return demoDao.insert(demo);
    }

    @Override
    public void removeAllDemos() {
        removeAllEntities(getIsQuery(null), Demo.class);
    }
}
