package com.redscarf.ibone.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * Session_Ticket存储类
 */
@Slf4j
public class IboneSessionTicketStore {

    public IboneSessionTicketStore(){

    }
    private StringRedisTemplate redisTemplate;
    public IboneSessionTicketStore(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    private String TICKET_SESSION_PREFIX = "ticket_session_"; //ST票据_sessionId关系健前缀

    private String SESSION_TICKET_PREFIX = "session_ticket_";  //sessionId_ST票据关系健前缀

    private Long timeout = 1800l; //默认半个小时

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    /**
     * 存储SessionId和ticket的关系
     * @param sessionId
     * @param ticket
     */
    public void store(String sessionId,String ticket){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(TICKET_SESSION_PREFIX + ticket,sessionId);
        valueOperations.set(SESSION_TICKET_PREFIX + sessionId,ticket);
        redisTemplate.expire(TICKET_SESSION_PREFIX + ticket,timeout,TimeUnit.MILLISECONDS);
        redisTemplate.expire(SESSION_TICKET_PREFIX + sessionId,timeout,TimeUnit.MILLISECONDS);

        log.debug("save ticket and session[ticket:{},sessionId:{}]",ticket,sessionId);
    }

    /**
     * 根据SessionId更新Session和ST的过期时间
     * @param sessionId
     */
    public void expireBySession(String sessionId){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        String ticket = valueOperations.get(SESSION_TICKET_PREFIX + sessionId);
        if(StringUtils.isNotBlank(ticket)){
            redisTemplate.expire(TICKET_SESSION_PREFIX + ticket,timeout,TimeUnit.MILLISECONDS);
            redisTemplate.expire(SESSION_TICKET_PREFIX + sessionId,timeout,TimeUnit.MILLISECONDS);
        }
        log.debug("expire ticket and session[ticket:{},sessionId:{}]",ticket,sessionId);
    }

    /**
     * 根据SessionId删除关系
     * @param sessionId
     */
    public void deleteBySession(String sessionId){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        String ticket = valueOperations.get(SESSION_TICKET_PREFIX + sessionId);
        if(StringUtils.isNotBlank(ticket)){
            redisTemplate.delete(TICKET_SESSION_PREFIX + ticket);
            redisTemplate.delete(SESSION_TICKET_PREFIX + sessionId);
        }
        log.debug("delete ticket and session[ticket:{},sessionId:{}]",ticket,sessionId);
    }

    /**
     * 根据SessionId删除关系
     * @param ticket 票据
     */
    public void deleteByTicket(String ticket){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        String sessionId = valueOperations.get(TICKET_SESSION_PREFIX + ticket);
        if(StringUtils.isNotBlank(sessionId)){
            redisTemplate.delete(TICKET_SESSION_PREFIX + ticket);
            redisTemplate.delete(SESSION_TICKET_PREFIX + sessionId);
        }
        log.debug("delete ticket and session[ticket:{},sessionId:{}]",ticket,sessionId);
    }

    /**
     * 根据SessionId获取Ticket
     * @param sessionId
     * @return
     */
    public String getTicket(String sessionId){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        String ticket = valueOperations.get(SESSION_TICKET_PREFIX + sessionId);
        return ticket;
    }

    /**
     * 根据Ticket获取sessionId
     * @param ticket ST
     * @return
     */
    public String getSessionId(String ticket){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        String sessionId = valueOperations.get(TICKET_SESSION_PREFIX + ticket);
        return sessionId;
    }

}
