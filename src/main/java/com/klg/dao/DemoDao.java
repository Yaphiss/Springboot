package com.klg.dao;

import com.klg.dao.entity.Demo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yaphiss on 2018/9/17.
 */
@Repository
public interface DemoDao extends MongoRepository<Demo, Long>{

    Demo findByName(String name);
}
