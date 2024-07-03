package ru.netology.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.netology.moneytransferservice.Repository.ServiceRepository;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    GenericContainer<?> container = new GenericContainer<>("transferservice").withExposedPorts(5500);
    private final Card card = Mockito.mock(Card.class);
    @Autowired
    private ServiceRepository serviceRepository;

    @BeforeEach
    void setUp() {
        container.start();
    }

    @Test
    void contextLoads() throws IOException, InterruptedException {
        Amount amount = new Amount("RUR", 120);
        Operation operation = new Operation("1234567887654321",
                "8765432112345678",
                "120",
                "10/25",
                amount);

        container.execInContainer(String.valueOf(serviceRepository.getCardRepository().put("1234567887654321", card)));
        container.execInContainer(String.valueOf(serviceRepository.getCardRepository().put("8765432112345678", card)));

        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" +
                container.getMappedPort(5500) + "/transfer",
                operation, String.class);

        Assertions.assertEquals(200, result.getStatusCode());
    }

}
