package ru.kpfu.itis.kuzmin.level;

import ru.kpfu.itis.kuzmin.model.zombie.Zombie;
import ru.kpfu.itis.kuzmin.protocol.Message;
import ru.kpfu.itis.kuzmin.server.Lobby;

import java.util.Iterator;
import java.util.List;

public class Level {
    private Lobby lobby;
    private Spawner spawner;
    private List<Zombie> zombies;
    public Level(Lobby lobby) {
        this.lobby = lobby;
        spawner = Spawner.getInstance(1);
    }

    public void startGame() {
        Zombie zombie;
        while ( (zombie = spawner.getZombie()) != null) {
            zombies.add(zombie);
//            lobby.sendMessage(Message.createMessage(Message.SPAWN_ZOMBIE));
        }
    }

    public void deleteZombie(int id) {
        Iterator<Zombie>  iterator = zombies.iterator();

        while (iterator.hasNext()) {
            Zombie zombie = iterator.next();
            if (zombie.getId() == id) {
                iterator.remove();
                if (zombies.size() == 0 && !spawner.isActive()) {
                    finishGame();
                    break;
                }
            }
        }
    }

    public void finishGame() {
        System.out.println("Живые победили!");
    }

    public void loseGame() {

    }

}
