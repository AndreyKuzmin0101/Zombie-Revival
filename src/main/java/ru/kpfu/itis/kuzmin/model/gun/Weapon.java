package ru.kpfu.itis.kuzmin.model.gun;

public class Weapon {
    private final double rateOfFire;
    private int bulletVelocity;
    private double damage;
    private double interval;

    public Weapon(double damage, double rateOfFire, int bulletVelocity) {
        this.damage = damage;
        this.rateOfFire = rateOfFire;
        this.bulletVelocity = bulletVelocity;
        interval = 0;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void reduceInterval(double elapsedTime) {
        interval -= elapsedTime;
    }
    public void resumeInterval() {
        interval = rateOfFire;
    }

    public double getInterval() {
        return interval;
    }

    public int getBulletVelocity() {
        return bulletVelocity;
    }

    public void setBulletVelocity(int bulletVelocity) {
        this.bulletVelocity = bulletVelocity;
    }
}
