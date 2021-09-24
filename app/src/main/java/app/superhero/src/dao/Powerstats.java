package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Powerstats {
    @PrimaryKey
    int powerstatsId;

    int intelligence;
    int strength;
    int speed;
    int durability;
    int power;
    int combat;

    public Powerstats(int powerstatsId, int intelligence, int strength, int speed, int durability, int power, int combat) {
        this.powerstatsId = powerstatsId;
        this.intelligence = intelligence;
        this.strength = strength;
        this.speed = speed;
        this.durability = durability;
        this.power = power;
        this.combat = combat;
    }

    public int getPowerstatsId() {
        return powerstatsId;
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
