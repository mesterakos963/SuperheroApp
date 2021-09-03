package app.superhero.src.models;

import java.io.Serializable;

import app.superhero.src.api.ZeroWhenNull;

public class Powerstats implements Serializable {
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
