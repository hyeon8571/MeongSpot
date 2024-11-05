package com.ottogi.gps.walking.handler;

import com.ottogi.gps.walking.repository.WalkingRedisRepository;

import net.minidev.json.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.math.BigDecimal;


@Component
public class WalkingSessionHandler extends TextWebSocketHandler {

    private final WalkingRedisRepository walkingRedisRepository;

    public WalkingSessionHandler(WalkingRedisRepository walkingRedisRepository) {
        this.walkingRedisRepository = walkingRedisRepository;
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(message.getPayload());
        JSONObject jsonObject = (JSONObject)obj;


        Long userId = jsonObject.getLong("userId");
        double latitude = jsonObject.getDouble("lat");
        double longitude = jsonObject.getDouble("lng");

        BigDecimal latBigDecimal = BigDecimal.valueOf(latitude);
        BigDecimal lngBigDecimal = BigDecimal.valueOf(longitude);

        walkingRedisRepository.saveGpsData(userId, latBigDecimal, lngBigDecimal);
    }
}
