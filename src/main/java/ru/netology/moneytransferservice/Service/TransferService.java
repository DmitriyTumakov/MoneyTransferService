package ru.netology.moneytransferservice.Service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.Exceptions.CardNotFoundException;
import ru.netology.moneytransferservice.Exceptions.IncorrectCodeException;
import ru.netology.moneytransferservice.Exceptions.InvalidCVVException;
import ru.netology.moneytransferservice.Exceptions.InvalidExpiryDateException;
import ru.netology.moneytransferservice.Logger.FileLogger;
import ru.netology.moneytransferservice.Logger.Logger;
import ru.netology.moneytransferservice.Operation;
import ru.netology.moneytransferservice.Repository.ServiceRepository;

import java.time.LocalDateTime;

@Service
public class TransferService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TransferService.class);
    private ServiceRepository repository;
    private Logger logger = new FileLogger();

    public TransferService(ServiceRepository repository) {
        this.repository = repository;
    }

    public String transfer(Operation operation) {
        // Проверка на наличие карт
        // Существует ли карта, с которой выполняется перевод
        if (repository.getCardRepository().containsKey(operation.getFromCard())) {
            // Правильно ли указан CVV
            if (repository.getCardRepository().get(operation.getFromCard()).getCvv().equals(operation.getFromCVV())) {
                // Правильно ли указана дата истечения обслуживания карты
                if (repository.getCardRepository().get(operation.getFromCard()).getExpiryDate().equals(operation.getCardFromValidTill())) {
                    // Существует ли карта, на которую выполняется перевод
                    if (repository.getCardRepository().containsKey(operation.getToCard())) {
                        return repository.addOperation(operation);
                    } else {
                        // Выбросить исключение (карты, на которую производится перевод, нет)
                        logOperationDenied(operation);
                        throw new CardNotFoundException("Card not found");
                    }
                } else {
                    // Выбросить исключение (Неверная дата истечения обслуживания карты)
                    logOperationDenied(operation);
                    throw new InvalidExpiryDateException("Expiry Date invalid");
                }
            } else {
                // Выбросить исключение (Неверный CVV)
                logOperationDenied(operation);
                throw new InvalidCVVException("CVV invalid");
            }
        } else {
            // Выбросить исключение (Карты, с которой производится перевод, нет)
            logOperationDenied(operation);
            throw new CardNotFoundException("Card not found");
        }
    }

    public void confirmOperation(int code) {
        Operation operation = repository.getOperationRepository().get(repository.getId());
        // Подтверждение операции (Ввод кода)
        if (code == 0000) {
            repository.getCardRepository().get(operation.getFromCard()).decrementMoney(operation.getMoneyValue());
            repository.getCardRepository().get(operation.getToCard()).incrementMoney(operation.getMoneyValue());
            logOperationAccepted(operation);
        } else {
            // Выбросить исключение (Код неверный)
            logOperationDenied(operation);
            throw new IncorrectCodeException("Incorrect code");
        }
    }

    private void logOperationAccepted(Operation operation) {
        logger.log("[" + LocalDateTime.now() + "] Operation confirmed: " +
                operation.getFromCard() + " -> " +
                operation.getToCard() + " | Сумма перевода: " +
                operation.getMoneyValue() + "₽ | Сумма комиссии: " +
                operation.getComissionValue() + "₽ | Операция прошла успешно\n", true);
    }

    private void logOperationDenied(Operation operation) {
        logger.log("[" + LocalDateTime.now() + "] Operation confirmed: " +
                operation.getFromCard() + " -> " +
                operation.getToCard() + " | Сумма перевода: " +
                operation.getMoneyValue() + "₽ | Сумма комиссии: " +
                operation.getComissionValue() + "₽ | Операция отклонена\n", true);
    }
}
