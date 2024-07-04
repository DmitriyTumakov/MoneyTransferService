package ru.netology.moneytransferservice.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.moneytransferservice.Card;
import ru.netology.moneytransferservice.Exceptions.CardNotFoundException;
import ru.netology.moneytransferservice.Exceptions.InvalidCVVException;
import ru.netology.moneytransferservice.Exceptions.InvalidExpiryDateException;
import ru.netology.moneytransferservice.Operation;
import ru.netology.moneytransferservice.Repository.ServiceRepository;

import java.util.concurrent.ConcurrentHashMap;

class TransferServiceTest {
    private final ServiceRepository repository = Mockito.mock(ServiceRepository.class);
    private final TransferService service = new TransferService(repository);
    private final Operation operation = Mockito.mock(Operation.class);
    private final Card card = Mockito.mock(Card.class);
    private final ConcurrentHashMap<String, Card> map = new ConcurrentHashMap();

    @BeforeEach
    void setUp() {
        String fromCard = "1234567887654321";
        String toCard = "8765432112345678";

        map.put(fromCard, new Card(fromCard, "10/25", "120"));
        map.put(toCard, new Card(toCard, "11/27", "150"));

        Mockito.when(repository.getCardRepository()).thenReturn(map);
        Mockito.when(operation.getFromCard()).thenReturn("1234567887654321");
        Mockito.when(operation.getToCard()).thenReturn("8765432112345678");
        Mockito.when(operation.getFromCVV()).thenReturn("120");
        Mockito.when(operation.getCardFromValidTill()).thenReturn("10/25");
        Mockito.when(repository.addOperation(operation)).thenReturn("0");
    }

    @Test
    void transferSuccessTest() {
        String result = service.transfer(operation);

        Assertions.assertEquals("0", result);
    }

    @Test
    void transferFailTest_FromCardNotFound() {
        Mockito.when(operation.getFromCard()).thenReturn("2222222222222222");
        Mockito.when(operation.getToCard()).thenReturn("8765432112345678");

        Assertions.assertThrows(CardNotFoundException.class, () -> { service.transfer(operation); });
    }

    @Test
    void transferFailTest_ToCardNotFound() {
        Mockito.when(operation.getFromCard()).thenReturn("1234567887654321");
        Mockito.when(operation.getToCard()).thenReturn("2222222222222222");

        Assertions.assertThrows(CardNotFoundException.class, () -> { service.transfer(operation); });
    }

    @Test
    void transferFailTest_InvalidCVV() {
        Mockito.when(operation.getFromCVV()).thenReturn("160");

        Assertions.assertThrows(InvalidCVVException.class, () -> { service.transfer(operation); });
    }

    @Test
    void transferFailTest_InvalidExpiryDate() {
        Mockito.when(operation.getCardFromValidTill()).thenReturn("06/24");

        Assertions.assertThrows(InvalidExpiryDateException.class, () -> { service.transfer(operation); });
    }
}