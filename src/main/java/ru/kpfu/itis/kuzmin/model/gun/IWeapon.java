package ru.kpfu.itis.kuzmin.model.gun;

public interface IWeapon {
    double getDamage();

    void reduceInterval(double elapsedTime);

    void resumeInterval();

    double getInterval();

    int getBulletVelocity();
}
