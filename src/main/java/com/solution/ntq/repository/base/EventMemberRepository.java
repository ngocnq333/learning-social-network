package com.solution.ntq.repository.base;

import com.solution.ntq.repository.entities.JoinEvent;

import org.springframework.data.repository.Repository;

public interface EventMemberRepository extends Repository<JoinEvent,Integer> {
    JoinEvent findByUserIdAndEventId(String userId,int eventId);

}
