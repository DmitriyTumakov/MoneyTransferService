package ru.netology.moneytransferservice.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.Card;
import ru.netology.moneytransferservice.Exceptions.CardNotFoundException;
import ru.netology.moneytransferservice.Operation;
import ru.netology.moneytransferservice.Repository.ServiceRepository;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class TransferServiceTest {
    private final ServiceRepository repository = Mockito.mock(ServiceRepository.class);
    private final TransferService service = new TransferService(repository);
    private final Operation operation = Mockito.mock(Operation.class);
    private final Card card = Mockito.mock(Card.class);

    @Test
    void transferSuccessTest() {
        Mockito.when(repository.getCardRepository()).thenReturn(new ConcurrentHashMap<>());
        repository.getCardRepository().put("1234567887654321", card);
        repository.getCardRepository().put("8765432112345678", card);
        Mockito.when(operation.getFromCard()).thenReturn("1234567887654321");
        Mockito.when(operation.getToCard()).thenReturn("8765432112345678");
        Mockito.when(repository.addOperation(operation)).thenReturn("0");

        String result = service.transfer(operation);

        Assertions.assertEquals("0", result);
    }

    @Test
    void transferFailTest_FromCardNotFound() {
        Mockito.when(repository.getCardRepository()).thenReturn(new ConcurrentHashMap<>());
        repository.getCardRepository().put("1111111111111111", card);
        repository.getCardRepository().put("8765432112345678", card);
        Mockito.when(operation.getFromCard()).thenReturn("1234567887654321");
        Mockito.when(operation.getToCard()).thenReturn("8765432112345678");
        Mockito.when(repository.addOperation(operation)).thenReturn("0");

        Assertions.assertThrows(CardNotFoundException.class, () -> { service.transfer(operation); });
    }

    @Test
    void transferFailTest_ToCardNotFound() {
        Mockito.when(repository.getCardRepository()).thenReturn(new ConcurrentHashMap<>());
        repository.getCardRepository().put("1234567887654321", card);
        repository.getCardRepository().put("1111111111111111", card);
        Mockito.when(operation.getFromCard()).thenReturn("1234567887654321");
        Mockito.when(operation.getToCard()).thenReturn("8765432112345678");
        Mockito.when(repository.addOperation(operation)).thenReturn("0");

        Assertions.assertThrows(CardNotFoundException.class, () -> { service.transfer(operation); });
    }
}