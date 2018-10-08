package com.klg.component;

import org.springframework.context.ApplicationEvent;

/**
 * Created by yaphiss on 2018/9/20.
 */
public class LogEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LogEvent(Object source) {
        super(source);
    }
}
