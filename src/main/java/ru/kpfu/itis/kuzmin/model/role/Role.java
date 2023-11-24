package ru.kpfu.itis.kuzmin.model.role;

import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;


public abstract class Role {
    public static final int SHOOTER = 0;
    public static final int ENGINEER = 1;
    private double speed;
    private int roleCode;
    private Weapon defaultWeapon;
    private ImageView image;


    public Role(double speed, int roleCode) {
        this.speed = speed;
        this.roleCode = roleCode;
    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }

    public Weapon getDefaultWeapon() {
        return defaultWeapon;
    }

    public void setDefaultWeapon(Weapon defaultWeapon) {
        this.defaultWeapon = defaultWeapon;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
