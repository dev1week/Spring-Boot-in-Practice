package com.ssafy.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;


//이벤트 퍼블리셔와 리스너는 동일한 스레드에서 실행한다.
//때문에 이벤트 리스너가 이메일을 발송하기 전까지는 사용자 등록 과정이 완료되지 않는다.
//때문에 비동기 실행을 위해서 (=이벤트 리스너는 퍼블리셔와 다른 스레드에서 비동기로 실행)
@Configuration
public class EventConfiguration {

    @Bean(name="applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster(){
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());

        return eventMulticaster;
    }


}
