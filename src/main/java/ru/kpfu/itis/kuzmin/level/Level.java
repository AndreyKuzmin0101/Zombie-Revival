package ru.kpfu.itis.kuzmin.level;

import ru.kpfu.itis.kuzmin.protocol.Message;
import ru.kpfu.itis.kuzmin.server.Lobby;
import ru.kpfu.itis.kuzmin.protocol.ZombieModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level {
    private Lobby lobby;
    private Spawner spawner;
    private List<ZombieModel> zombies;

    public Level(Lobby lobby) {
        this.lobby = lobby;
        spawner = Spawner.getInstance(1);
        zombies = new ArrayList<>();
    }

    public void startGame() {
        ZombieModel zombie;
        while ((zombie = spawner.getZombie()) != null) {
            zombies.add(zombie);

            lobby.sendMessage(Message.createMessage(Message.SPAWN_ZOMBIE, zombie.getData()));
        }
    }

    public void stopGame() {
        spawner.setInActive();
    }

    public boolean deleteZombie(int id) {
        Iterator<ZombieModel> iterator = zombies.iterator();
        while (iterator.hasNext()) {
            ZombieModel zombie = iterator.next();
            if (zombie.getId() == id) {
                iterator.remove();
                if (zombies.size() == 0 && !spawner.isActive()) {

                    lobby.stopGame(Message.VICTORY);
                }
                return true;
            }
        }
        return false;
    }


}
