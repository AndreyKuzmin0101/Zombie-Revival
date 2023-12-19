package ru.kpfu.itis.kuzmin.model.gun;

public class Weapon implements IWeapon{
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

    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public void reduceInterval(double elapsedTime) {
        interval -= elapsedTime;
    }

    @Override
    public void resumeInterval() {
        interval = rateOfFire;
    }

    @Override
    public double getInterval() {
        return interval;
    }

    @Override
    public int getBulletVelocity() {
        return bulletVelocity;
    }
}
