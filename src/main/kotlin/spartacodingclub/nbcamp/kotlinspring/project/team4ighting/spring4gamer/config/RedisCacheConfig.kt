package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class RedisCacheConfig {

    @Bean
    fun redisCacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager =

        RedisCacheManager
            .builder(redisConnectionFactory)
            .cacheDefaults(
                RedisCacheConfiguration
                    .defaultCacheConfig()
//                    .prefixCacheNameWith(this.javaClass.simpleName + ".")
                    .entryTtl(Duration.ofMinutes(1))
                    .disableCachingNullValues()
                    .serializeKeysWith(
                        RedisSerializationContext
                            .SerializationPair
                            .fromSerializer(StringRedisSerializer())
                    )
                    .serializeValuesWith(
                        RedisSerializationContext
                            .SerializationPair
                            .fromSerializer(
                                GenericJackson2JsonRedisSerializer(
                                    jacksonObjectMapper()
                                        .registerModules(JavaTimeModule())
                                        .activateDefaultTyping(
                                            BasicPolymorphicTypeValidator
                                                .builder()
                                                .allowIfSubType(Object::class.java)
                                                .build(),
                                            ObjectMapper.DefaultTyping.NON_FINAL
                                        )
                                )
                            )
                    )
            )
            .build()

}