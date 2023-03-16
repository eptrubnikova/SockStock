package me.trubnikova.sockstock.controllers;

import me.trubnikova.sockstock.exception.InSufficientSockQuantity;
import me.trubnikova.sockstock.exception.InvalidSockRequestException;
import me.trubnikova.sockstock.model.Color;
import me.trubnikova.sockstock.model.Size;
import me.trubnikova.sockstock.model.Socks;
import me.trubnikova.sockstock.service.SockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
public class SocksController {

    private final SockService sockService;

    public SocksController(SockService sockService) {
        this.sockService = sockService;
    }

    @ExceptionHandler(InvalidSockRequestException.class)
    public ResponseEntity<String> handleInvalidException(InvalidSockRequestException invalidSockRequestException) {
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }

    @ExceptionHandler(InSufficientSockQuantity.class)
    public ResponseEntity<String> handleInSufficientException(InSufficientSockQuantity inSufficientSockQuantity) {
        return ResponseEntity.badRequest().body(inSufficientSockQuantity.getMessage());
    }

    @PostMapping
    public void addSocks(@RequestBody Socks socks) {
        sockService.addSock(socks);
    }

    @PutMapping
    public void issueSock(Socks socks) {
        sockService.issueSock(socks);
    }

    @GetMapping
    public int getSocksCount(@RequestParam(required = false, name = "color") Color color,
                             @RequestParam(required = false, name = "size") Size size,
                             @RequestParam(required = false, name = "cottonMin") Integer cottonMin,
                             @RequestParam(required = false, name = "cottonMax") Integer cottonMax) {
        return sockService.getSocksQuantity(color, size, cottonMin, cottonMax);
    }

    @DeleteMapping
    public void removeDefectiveSocks(@RequestBody Socks socks) {
        sockService.removeDefectiveSocks(socks);
    }
}
