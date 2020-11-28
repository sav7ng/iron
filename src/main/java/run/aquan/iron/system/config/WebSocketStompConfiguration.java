package run.aquan.iron.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Class WebSocketStompConfiguration
 * @Description
 * @Author Saving
 * @Date 2020/3/24 12:07
 * @Version 1.0
 **/
@Configuration
@EnableWebSocketMessageBroker
// @EnableWebSocketMessageBroker注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，这时候控制器（controller）  开始支持@MessageMapping,就像是使用@requestMapping一样。
public class WebSocketStompConfiguration implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册stomp端点
     * @param stompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //注册一个 Stomp 的节点(endpoint),并指定使用 SockJS 协议。
        // 允许使用socketJs方式访问 即可通过http://IP:PORT/webSocket来和服务端websocket连接
        stompEndpointRegistry
                .addEndpoint("/webSocket")  //端点名称
                //.setHandshakeHandler() 握手处理，主要是连接的时候认证获取其他数据验证等
                //.addInterceptors() 拦截处理，和http拦截类似
                .setAllowedOrigins("*") //跨域
                .withSockJS() //使用sockJS
                .setClientLibraryUrl("https://cdn.bootcss.com/sockjs-client/1.4.0/sockjs.min.js");
    }

    /**
     * 配置信息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 这里使用的是内存模式，生产环境可以使用rabbitmq或者其他mq。
        // 这里注册两个，主要是目的是将广播和队列分开。
        // registry.enableStompBrokerRelay().setRelayHost().setRelayPort() 其他方式
        // 订阅Broker名称 user点对点 topic广播即群发
        // 广播式配置名为 /nasus 消息代理 , 这个消息代理必须和 controller 中的 @SendTo 配置的地址前缀一样或者全匹配
        registry.enableSimpleBroker("/topic");
        // 全局(客户端)使用的消息前缀
        // registry.setApplicationDestinationPrefixes("/app");
        // 点对点使用的前缀 无需配置 默认/user
        // registry.setUserDestinationPrefix("/user");
    }
}

