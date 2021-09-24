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

    public PowerstatsDto(String response, int id, String name, int intelligence, int strength, int speed, int durability, int power, int combat) {
        this.response = response;
        this.id = id;
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.speed = speed;
        this.durability = durability;
        this.power = power;
        this.combat = combat;
    }

    public String getResponse() {
        return response;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getStrength() {
        return strength;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDurability() {
        return durability;
    }

    public int getPower() {
        return power;
    }

    public int getCombat() {
        return combat;
    }
}
