package emc.brousegame;

import emc.brousegame.domain.ChatMessage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.concurrent.TimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@RunWith(SpringJUnit4ClassRunner.class)
public class BrousegameApplicationTests {
    
    private CompletableFuture<ChatMessage> completableFuture;
    private String URL;

	@Test
	public void contextLoads() throws InterruptedException, ExecutionException, TimeoutException {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity res =  restTemplate.postForEntity("http://localhost:9000/api/v1/chat/channel?userIdOne=1&userIdTwo=2", null, Map.class);
            Map<String,Object> channel = (Map<String,Object>) res.getBody();
            completableFuture = new CompletableFuture<>();
            WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
            stompClient.setMessageConverter(new MappingJackson2MessageConverter());
            StompSession stompSession = stompClient.connect("ws://localhost:9000/ws", new StompSessionHandlerAdapter() {}).get(1, SECONDS);
            stompSession.subscribe("/topic/chat."+channel.get("channelUuid"), new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders sh) {
                    System.out.println(sh.toString());
                    return ChatMessage.class;
                }

                @Override
                public void handleFrame(StompHeaders sh, Object o) {
                     System.out.println((ChatMessage) o);
                    completableFuture.complete((ChatMessage) o);

                }
            });
            ChatMessage message = new ChatMessage();
            message.setChannelId((String) channel.get("channelUuid"));
            message.setMessage("tester nyoba");
            
            stompSession.send("/app/chat."+channel.get("channelUuid"), message);
//            ChatMessage gameState = completableFuture.get(20, SECONDS);
//            System.out.println(gameState);

	}
        
        private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }


}
