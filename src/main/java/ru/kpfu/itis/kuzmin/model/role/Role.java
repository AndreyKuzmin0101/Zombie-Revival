package ru.kpfu.itis.kuzmin.model.role;

import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;


public abstract class Role implements IRole {
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

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public int getRoleCode() {
        return roleCode;
    }

    @Override
    public Weapon getDefaultWeapon() {
        return defaultWeapon;
    }

    @Override
    public void setDefaultWeapon(Weapon defaultWeapon) {
        this.defaultWeapon = defaultWeapon;
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Override
    public void setImage(ImageView image) {
        this.image = image;
    }
}
