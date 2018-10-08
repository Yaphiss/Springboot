package com.klg.component;

import com.klg.dao.LogsDao;
import com.klg.dao.entity.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by yaphiss on 2018/9/20.
 */
@Component
public class AnnotationLogListener {

    @Autowired private LogsDao logsDao;

    @EventListener
    public void logs (LogEvent logEvent) {
        Object source = logEvent.getSource();
        logsDao.insert((Logs) source);
    }
}
