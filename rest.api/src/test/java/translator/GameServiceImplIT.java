package translator;

import com.simple.rest.api.Application;
import com.simple.rest.api.model.PlayerRequest;
import com.simple.rest.api.model.PlayerResponse;
import com.simple.rest.api.servicetranslator.GameServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class GameServiceImplIT {

    @Autowired
    private GameServiceImpl gameServiceImpl;

    @Test
    public void calculate() {
        PlayerResponse response = new PlayerResponse();
        response.setWin(BigDecimal.valueOf(4009.5));
        PlayerRequest request = new PlayerRequest();
        request.setBet(BigDecimal.valueOf(40.5));
        request.setNumber(99);

        PlayerResponse result = gameServiceImpl.calculateWin(request);
        Assertions.assertNotNull(result);
    }
}
