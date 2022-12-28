package com.simple.rest.api.servicetranslator;

import com.simple.rest.api.model.PlayerRequest;
import com.simple.rest.api.model.PlayerResponse;
import com.simple.rest.api.service.GameService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GameServiceImpl implements GameService {
    @Override
    public PlayerResponse calculateWin(PlayerRequest request) {
        try {
            int number = (int)(Math.random()*100+1);
            PlayerResponse response = new PlayerResponse();

            if(request.getNumber() > number) {
                BigDecimal win = request.getBet().multiply(BigDecimal.valueOf(((double)99/(100 - request.getNumber()))));
                response.setWin(win.setScale(2));
            }

            return response;
        } catch (ArithmeticException e) {
            throw new ArithmeticException();
        }
    }

}
