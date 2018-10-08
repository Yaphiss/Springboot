package com.klg.service;

import com.alibaba.fastjson.JSON;
import com.klg.common.Constant;
import com.klg.utils.DateUtil;
import com.mongodb.client.result.UpdateResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by yaphiss on 2018/9/18.
 *
 * 该类主要是用来拓展DAO一些操作
 */
@Service
public class BaseExtends<T> {

    private static Logger logger = Logger.getLogger(BaseExtends.class);
    @Autowired private MongoTemplate mongoTemplate;

    /**
     * 获取等值查询条件
     * @param params 查询条件集合
     * @return Query
     */
    public Query getIsQuery (Map<String, Object> params) {
        Query query = new Query();
        if(null == params) return query;

        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();

        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
        }
        return query;
    }

    /**
     * 根据条件删除数据
     * @param query 查询条件
     * @param tClass 实体
     */
    public void removeAllEntities (Query query, Class<T> tClass) {
        this.mongoTemplate.findAllAndRemove(query, tClass);
    }

    /**
     * 根据条件筛选数据
     * @param query 查询条件
     * @param tClass 实体
     * @return
     */
    public List<T> getEntitiesByQuery(Query query, Class<T> tClass) {
        return this.mongoTemplate.find(query, tClass);
    }

    /**
     * 根据条件更新数据
     * @param query 查询条件
     * @param update 更新字段
     * @param tClass
     * @return
     */
    public UpdateResult findOneAndUpdate(Query query, Update update, Class<T> tClass){ return mongoTemplate.updateFirst(query, update, tClass); }

    public List<Object> getClazzByDate (Class<T> tClass, int limit, Date start, Date end) {
        List<Object> list = new ArrayList<Object>();

        Query query = new Query();
        query = addIsReportedInitAtQuery(query);
        query = addCreatedAtQuery(query, start, end);
        query = addLimit(query, limit);

        List<T> finds = getEntitiesByQuery(query, tClass);
        if(!finds.isEmpty()){
            for (T item : finds){
                list.add(JSON.toJSON(item));
            }
        }
        logger.info("getClazzBeforeToday list length: "+ list.size());
        return list;
    }


    private Query addCreatedAtQuery(Query query, Date start, Date end){
        if (null == start || null == end){
            query.addCriteria(Criteria.where(Constant.getCreatedAt()).lt(DateUtil.getTodayStart()));
        }else{
            query.addCriteria(Criteria.where(Constant.getCreatedAt()).gte(DateUtil.getDateStart(start)).lt(DateUtil.getDateEnd(end)));
        }
        return query;
    }

    private Query addLimit(Query query, int limit){
        if(limit != 0){
            query.limit(limit);
        }
        return query;
    }

    private Query addIsReportedInitAtQuery (Query query) {
        query.addCriteria(Criteria.where(Constant.getIsReported()).is(Constant.getINIT()));
        return query;
    }

    public int getClazzCountByDate(Class<T> tClass, Date start, Date end) {
        Query query = new Query();
        query = addIsReportedInitAtQuery(query);
        query = addCreatedAtQuery(query, start, end);
        return (int) mongoTemplate.count(query, tClass);
    }
}
