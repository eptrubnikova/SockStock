package me.trubnikova.sockstock.service;

import me.trubnikova.sockstock.model.Color;
import me.trubnikova.sockstock.model.Size;
import me.trubnikova.sockstock.model.Socks;

public interface SockService {
    void addSock(Socks socks);

    void issueSock(Socks socks);

    void removeDefectiveSocks(Socks socks);

    int getSocksQuantity(Color color, Size size, Integer cottonMin, Integer cottonMax);
}
