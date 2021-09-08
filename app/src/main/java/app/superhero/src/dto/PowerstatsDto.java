package app.superhero.src.dto;

import java.io.Serializable;

import app.superhero.src.api.ZeroWhenNull;

public class PowerstatsDto implements Serializable {
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
