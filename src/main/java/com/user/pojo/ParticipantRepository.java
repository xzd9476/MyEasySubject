package com.user.pojo;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 *�����û��ֿ⣬�洢�����û�
 */
@Component
public class ParticipantRepository {
    private Map<String, User> activeSessions = new ConcurrentHashMap<String, User>(); //�����û�map�������û����ƣ�ֵ���û�����
    public Map<String, User> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(Map<String, User> activeSessions) {
        this.activeSessions = activeSessions;
    }

    public void add(String name, User user){
        activeSessions.put(name, user);

    }

    public User remove(String name){
        return activeSessions.remove(name);
    }

    public boolean containsUserName(String name){
        return activeSessions.containsKey(name);
    }
}
