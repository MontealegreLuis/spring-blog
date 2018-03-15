/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.messaging;

import com.codeup.security.ConfirmAccountEmail;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import javax.jms.ConnectionFactory;

@Configuration
public class MessagingConfiguration {
    @Value("${blog.messaging.topic}")
    private String topic;

    private ConfirmAccountEmail notification;

    public MessagingConfiguration(ConfirmAccountEmail notification) {
        this.notification = notification;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new RMQConnectionFactory();
    }

    @Bean
    public DefaultMessageListenerContainer jmsListener(ConnectionFactory connectionFactory) {

        DefaultMessageListenerContainer jmsListener = new DefaultMessageListenerContainer();
        jmsListener.setConnectionFactory(connectionFactory);
        jmsListener.setDestinationName(topic);
        jmsListener.setPubSubDomain(false);

        MessageListenerAdapter adapter = new MessageListenerAdapter(notification);
        adapter.setDefaultListenerMethod("receive");

        jmsListener.setMessageListener(adapter);
        return jmsListener;
    }
}
