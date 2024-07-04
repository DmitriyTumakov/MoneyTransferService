package ru.netology.moneytransferservice.Repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.moneytransferservice.Operation;

class ServiceRepositoryTest {
    private final ServiceRepository repository = new ServiceRepository();
    private final Operation operation = Mockito.mock(Operation.class);

    @Test
    void addOperationTest() {
        String result = repository.addOperation(operation);

        Assertions.assertEquals("0", result);
    }
}