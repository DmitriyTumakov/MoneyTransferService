package ru.netology.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.moneytransferservice.Repository.ServiceRepository;

import java.io.IOException;

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
        Operation operation = new Operation("1234567887654321",
                "8765432112345678",
                "120",
                "10/25",
                new Amount("RUR", 120));

        container.execInContainer(String.valueOf(serviceRepository.getCardRepository().put("1234567887654321", card)));
        container.execInContainer(String.valueOf(serviceRepository.getCardRepository().put("8765432112345678", card)));

        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" +
                container.getMappedPort(5500) + "/transfer",
                operation, String.class);

        System.out.println(container.getLogs());
        Assertions.assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
    }

}
