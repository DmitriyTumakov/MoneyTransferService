package ru.netology.moneytransferservice.Controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.moneytransferservice.Operation;
import ru.netology.moneytransferservice.Service.TransferService;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/")
public class TransferController {
    private TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping("transfer")
    public String transfer(@RequestBody Operation fromAccount) {
        System.out.println(fromAccount.toString());
        return service.transfer(fromAccount);
    }

    @CrossOrigin
    @PostMapping("confirmOperation")
    public void confirmOperation(@RequestBody ConcurrentHashMap<String, String> operationCode) {
        service.confirmOperation(Integer.parseInt(operationCode.get("code")));
    }
}
