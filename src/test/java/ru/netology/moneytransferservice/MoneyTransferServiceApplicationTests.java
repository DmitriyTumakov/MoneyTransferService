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

import java.io.IOException;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    GenericContainer<?> container = new GenericContainer<>("transferservice").withExposedPorts(5500);

    @BeforeEach
    void setUp() {
        container.start();
    }

    @Test
    void contextLoads() throws IOException, InterruptedException {
        var request = Map.ofEntries(
                Map.entry("cardFromNumber", "1234567887654321"),
                Map.entry("cardToNumber", "8765432112345678"),
                Map.entry("cardFromCVV", "120"),
                Map.entry("cardFromValidTill", "10/25"),
                Map.entry("amount", Map.ofEntries(
                        Map.entry("currency", "RUR"),
                        Map.entry("value", 1000)
                ))
        );

        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" +
                container.getMappedPort(5500) + "/transfer",
                request, String.class);

        System.out.println(container.getLogs());
        Assertions.assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
    }

}
