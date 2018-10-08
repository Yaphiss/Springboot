package com.klg.dao;

import com.klg.dao.entity.Logs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yaphiss on 2018/9/19.
 */
@Repository
public interface LogsDao extends MongoRepository<Logs, Long> {

}
