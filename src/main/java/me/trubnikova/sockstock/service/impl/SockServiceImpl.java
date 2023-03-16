package me.trubnikova.sockstock.service.impl;

import me.trubnikova.sockstock.exception.InSufficientSockQuantity;
import me.trubnikova.sockstock.exception.InvalidSockRequestException;
import me.trubnikova.sockstock.model.Color;
import me.trubnikova.sockstock.model.Size;
import me.trubnikova.sockstock.model.Socks;
import me.trubnikova.sockstock.service.SockService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SockServiceImpl implements SockService {

    private final Map<Socks, Integer> socksAll = new HashMap<>();

    @Override
    public void addSock(Socks socks) {
        validateRequest(socks);
        Socks sock = mapToSock(socks);
        if (socksAll.containsKey(sock)) {
            socksAll.put(sock, socksAll.get(sock) + socks.getQuantity());
        } else {
            socksAll.put(sock, socks.getQuantity());
        }
    }

    @Override
    public void issueSock(Socks socks) {
        decreaseSockQuantity(socks);
    }

    @Override
    public void removeDefectiveSocks(Socks socks) {
        decreaseSockQuantity(socks);
    }

    @Override
    public int getSocksQuantity(Color color, Size size, Integer cottonMin, Integer cottonMax) {
        int total = 0;
        for (Map.Entry<Socks, Integer> entry : socksAll.entrySet()) {
            if (color != null && !entry.getKey().getColor().equals(color)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonMin != null && entry.getKey().getCottonPart() < cottonMin) {
                continue;
            }
            if (cottonMax != null && entry.getKey().getCottonPart() > cottonMax) {
                continue;
            }
            total += entry.getValue();
        }
        return total;
    }

    private void validateRequest(Socks socks) {
        if (socks.getColor() == null || socks.getSize() == null) {
            throw new InvalidSockRequestException("Все поля должны быть заполнены");
        }
        if (socks.getCottonPart() < 0 || socks.getCottonPart() > 100) {
            throw new InvalidSockRequestException("Процент хлопка должен быть между 0 и 100");
        }
        if (socks.getQuantity() <= 0) {
            throw new InvalidSockRequestException("Количество должно быть больше 0");
        }
    }

    private Socks mapToSock(Socks socks) {
        Socks sock = new Socks(socks.getColor(), socks.getSize(), socks.getCottonPart());
        return sock;
    }

    private void decreaseSockQuantity(Socks socks) {
        validateRequest(socks);
        Socks sock = mapToSock(socks);
        int sockQuantity = socksAll.getOrDefault(sock, 0);
        if (sockQuantity >= socks.getQuantity()) {
            socksAll.put(sock, sockQuantity - socks.getQuantity());
        } else {
            throw new InSufficientSockQuantity("Носков нет");
        }
    }
}
