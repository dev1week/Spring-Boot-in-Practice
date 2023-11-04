package com.ssafy.practice.event;

import com.ssafy.practice.model.ApplicationUser;
import org.springframework.context.ApplicationEvent;


//사용자가 성공적으로 등록되면 해당 클래스의 객체가 생성됨
//이후 리스너가 이 이벤트에 반응하여 이메일 내용을 구성하고 등록된 사용자 이메일 주소로 이메일을 발송한다.
//옵저버 패턴을 이용해 사용자 등록과 이메일 발송이라는 행위를 결합하지 않고 분리할 수 있다.
public class UserRegistrationEvent extends ApplicationEvent {


    private static final long serialVersionUID = -2685172945219633123L;


    private ApplicationUser applicationuser;

    public UserRegistrationEvent(ApplicationUser applicationUser){
        super(applicationUser);
        this.applicationuser = applicationUser;
    }

    public ApplicationUser getUser(){
        return applicationuser;
    }


}
