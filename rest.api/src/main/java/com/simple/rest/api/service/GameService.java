package com.simple.rest.api.service;

import com.simple.rest.api.model.PlayerRequest;
import com.simple.rest.api.model.PlayerResponse;
import org.springframework.stereotype.Service;

public interface GameService {
    PlayerResponse calculateWin(PlayerRequest request);
}
