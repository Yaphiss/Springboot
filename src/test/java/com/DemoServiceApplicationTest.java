package com;

import com.klg.Application;
import com.klg.dao.entity.Demo;
import com.klg.service.DemoServiceImpl;
import com.klg.utils.ReflectionUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by yaphiss on 2018/9/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoServiceApplicationTest {

    private static Logger logger = Logger.getLogger(DemoServiceApplicationTest.class);
    private static String time = String.valueOf(System.currentTimeMillis());

    @Autowired
    private DemoServiceImpl demoService;

    @Before
    public void initMocks () {
        demoService.removeAllDemos();
        Demo demo = new Demo(time, "register");
        demoService.insert(demo);
        logger.debug("DemoServiceApplicationTest initMocks finish.......");
    }

    @After
    public void removeMocks(){
        demoService.removeAllDemos();
        logger.debug("DemoServiceApplicationTest removeMocks finish.......");
    }

    @Test
    public void findByUserName() {
        Demo demo = this.demoService.getUserByName(time);
        logger.debug("findByUserName: "+demo);
        Assert.assertNotNull(demo);
    }

    @Test
    public void reflection () {
        int test = (int) ReflectionUtil.invokeMethod(demoService, "getClazzCountByDate", new Class[]{Demo.class.getClass(), Date.class, Date.class}, new Object[]{Demo.class, new Date(), new Date()});
        Assert.assertEquals(test, 0);
    }
}
