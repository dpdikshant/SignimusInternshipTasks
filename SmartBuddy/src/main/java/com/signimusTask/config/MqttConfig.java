package com.signimusTask.config;

//MqttConfig.java
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.dsl.IntegrationFlow;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class MqttConfig {

 private static final String BROKER_URL = "tcp://broker.hivemq.com:1883";
 private static final String CLIENT_ID = "smartHomeClient";

 @Bean
 public DefaultMqttPahoClientFactory mqttClientFactory() {
     MqttConnectOptions options = new MqttConnectOptions();
     options.setServerURIs(new String[]{BROKER_URL});
     options.setCleanSession(true);
     DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
     factory.setConnectionOptions(options);
     return factory;
 }

 @Bean
 public MessageChannel mqttOutboundChannel() {
     return new DirectChannel();  // Use DirectChannel without calling .get()
 }

 @Bean
 public MessageHandler mqttOutboundHandler() {
     MqttPahoMessageHandler messageHandler =
             new MqttPahoMessageHandler(CLIENT_ID, mqttClientFactory());
     messageHandler.setDefaultTopic("smarthome/devices");
     messageHandler.setAsync(true);
     return messageHandler;
 }

 @Bean
 public IntegrationFlow mqttOutboundFlow() {
     return IntegrationFlow
             .from(mqttOutboundChannel())
             .handle(mqttOutboundHandler())
             .get();
 }
}

