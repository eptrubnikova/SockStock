package me.trubnikova.sockstock.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Socks {

    private final Color color;
    private final Size size;
    private final int cottonPart;
    public int quantity;

    public Socks(Color color, Size size, int cottonPart) {
        this.color = color;
        this.size = size;
        this.cottonPart = cottonPart;
    }
}
