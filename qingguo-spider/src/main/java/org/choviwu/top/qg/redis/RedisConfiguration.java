package org.choviwu.top.qg.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
//@ConditionalOnProperty(prefix = "spring.redis",name = "host",matchIfMissing = true)
public class RedisConfiguration {



    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setStringSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return redisTemplate;
    }

    @Bean("cacheRedisRepository")
    public Cached redisRepository(){
        return new RedisRepository();
    }

    @Bean
    public HashOperations hashOperations (RedisTemplate redisTemplate){
        return redisTemplate.opsForHash();
    }
    @Bean
    public ValueOperations valueOperations (RedisTemplate redisTemplate){
        return redisTemplate.opsForValue();
    }
    @Bean
    public ListOperations listOperations (RedisTemplate redisTemplate){
        return redisTemplate.opsForList();
    }
    @Bean
    public SetOperations setOperations (RedisTemplate redisTemplate){
        return redisTemplate.opsForSet();
    }
    @Bean
    public ZSetOperations zSetOperations (RedisTemplate redisTemplate){
        return redisTemplate.opsForZSet();
    }
}
