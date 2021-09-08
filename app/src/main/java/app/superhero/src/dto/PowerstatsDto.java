package app.superhero.src.dto;

import java.io.Serializable;

import app.superhero.src.utils.ZeroWhenNull;

public class PowerstatsDto implements Serializable {
    String response;
    @ZeroWhenNull
    int id;

    String name;

    @ZeroWhenNull
    int intelligence;
    @ZeroWhenNull
    int strength;
    @ZeroWhenNull
    int speed;
    @ZeroWhenNull
    int durability;
    @ZeroWhenNull
    int power;
    @ZeroWhenNull
    int combat;
}
