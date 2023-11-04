package com.ssafy.practice.service;


import com.google.common.cache.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS_COUNT = 3;

    private LoadingCache<String, Integer> loginAttemptCache;


    //사용자 username이 키이고 로그인 싪패횟수가 값인 캐시를 생성한다.
    public LoginAttemptService() {
        //생성 후 1일이 지나면 만료된다.
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<String, Integer>() {

                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }


    public void loginSuccess(String username){
        loginAttemptCache.invalidate(username);
    }

    //로그인 실패시 해당 사용자의 실패횟수를 증가시키고 캐시에 저장한다.
    public void loginFailed(String username){
        int failedAttemptCounter;

        try{
            failedAttemptCounter = loginAttemptCache.get(username);
        }catch(Exception e){
            failedAttemptCounter=0;
        }

        failedAttemptCounter ++;
        loginAttemptCache.put(username, failedAttemptCounter);
    }

    public boolean isBlocked(String username){
        try{
            return loginAttemptCache.get(username) >= MAX_ATTEMPTS_COUNT;
        }catch(Exception e){
            return false;
        }
    }


}
