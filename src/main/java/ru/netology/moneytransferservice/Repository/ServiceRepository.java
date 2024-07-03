package ru.netology.moneytransferservice.Repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.Card;
import ru.netology.moneytransferservice.Operation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ServiceRepository {
    private final ConcurrentHashMap<String, Card> cardRepository = new ConcurrentHashMap<String, Card>();
    private final ConcurrentHashMap<String, Operation> operationRepository = new ConcurrentHashMap<String, Operation>();
    private final AtomicInteger operationCounter = new AtomicInteger(0);
    private String operationId;

    public ConcurrentHashMap<String, Card> getCardRepository() {
        return cardRepository;
    }

    public ConcurrentHashMap<String, Operation> getOperationRepository() {
        return operationRepository;
    }

    public String addOperation(Operation operation) {
        operationId = operationCounter.toString();
        operationRepository.put(operationId, operation);
        operationCounter.getAndIncrement();
        return operationId;
    }

    public void addCard(Card card) {
        cardRepository.put(card.getCardNumber(), card);
    }

    public String getId() {
        return operationId;
    }
}
