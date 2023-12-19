package ru.kpfu.itis.kuzmin.model.role;

import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;

public interface IRole {
    double getSpeed();

    int getRoleCode();

    Weapon getDefaultWeapon();

    void setDefaultWeapon(Weapon defaultWeapon);

    ImageView getImage();

    void setImage(ImageView image);
}
