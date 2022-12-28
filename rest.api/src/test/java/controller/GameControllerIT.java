package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.rest.api.Application;
import com.simple.rest.api.model.PlayerRequest;
import com.simple.rest.api.model.PlayerResponse;
import com.simple.rest.api.service.GameService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class GameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private GameService gameService;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void play() throws Exception {
        PlayerResponse response = new PlayerResponse();

        response.setWin(BigDecimal.valueOf(80.19));
        Mockito.when(gameService.calculateWin(Mockito.any(PlayerRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/play")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"bet\": 40.5, \"number\": 50}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.win").value(80.19));
    }

    @Test
    public void playRounds() throws ExecutionException, InterruptedException, UnsupportedEncodingException, JsonProcessingException {
        playRounds(24);
    }

    private void playRounds(final int threadCount) throws InterruptedException, ExecutionException, UnsupportedEncodingException, JsonProcessingException {
        final PlayerResponse response = new PlayerResponse();
        final int numProcesses = 1000000;

        response.setWin(BigDecimal.valueOf(0.99));
        Mockito.when(gameService.calculateWin(Mockito.any(PlayerRequest.class))).thenReturn(response);

        Callable<MockHttpServletResponse> task = () -> mockMvc.perform(post("/api/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bet\": 0.99, \"number\": 1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"win\": 0.99}"))
                .andReturn().getResponse();

        List<Callable<MockHttpServletResponse>> tasks = Collections.nCopies(numProcesses, task);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<MockHttpServletResponse>> futures = executorService.invokeAll(tasks);
        List<MockHttpServletResponse> resultList = new ArrayList<>(futures.size());
        BigDecimal playerWin = new BigDecimal(0);
        // Check for exceptions
        for (Future<MockHttpServletResponse> future : futures) {
            // Throws an exception if an exception was thrown by the task.
            PlayerResponse futureResponse = new ObjectMapper().readValue(future.get().getContentAsString(), PlayerResponse.class);
            playerWin = playerWin.add(futureResponse.getWin());
            resultList.add(future.get());
        }

        Assertions.assertEquals(BigDecimal.valueOf(numProcesses*0.99), playerWin.setScale(1));
    }

}