package ru.kpfu.itis.kuzmin.model.zombie;

public abstract class Zombie {
    public static final byte SHAMBLING_CITIZEN = 1;
    private int id;
    private int type;
    private int hp;
    private int damage;
    private float speed;

    public Zombie(int id, int type, int hp, int damage, float speed) {
        this.id = id;
        this.type = type;
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
