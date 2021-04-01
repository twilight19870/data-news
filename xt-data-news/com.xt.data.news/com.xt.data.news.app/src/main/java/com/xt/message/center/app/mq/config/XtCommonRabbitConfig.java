package com.xt.message.center.app.mq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息通用域配置
 * @author vivi207
 *
 */
@ConditionalOnProperty(prefix = "xt.message.center.mq", name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration
public class XtCommonRabbitConfig {
	private final static Logger LOGGER = LoggerFactory.getLogger(XtCommonRabbitConfig.class);
	
	@Bean("xtCommonRabbitConnectionFactory")
    public ConnectionFactory createMpsRabbitConnectionFactory(@Value("${xt.common.rabbitmq.host}") String host,
                                                      @Value("${xt.common.rabbitmq.port:5672}") int port,
                                                      @Value("${xt.common.rabbitmq.username}") String username,
                                                      @Value("${xt.common.rabbitmq.password}") String password,
                                                      @Value("${xt.common.rabbitmq.publisher-confirms:false}") boolean publisherConfirms,
                                                      @Value("${xt.common.rabbitmq.virtual-host:/common}") String virtualHost) {
		LOGGER.info("xtCommonRabbitConnectionFactory, host:{}, username:{}, virtualHost:{}", host, username, virtualHost);
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
	}

	@Bean("xtCommonRabbitTemplate")
	public RabbitTemplate rabbitTemplate(@Qualifier("xtCommonRabbitConnectionFactory") ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		return template;
	}

	@Bean("xtCommonRabbitListenerContainerFactory")
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(@Qualifier("xtCommonRabbitConnectionFactory") ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		return factory;
	}
}
