package me.anjoismysign.blobhorse.director.manager;

import me.anjoismysign.blobhorse.listener.HorseMountListener;
import me.anjoismysign.blobhorse.listener.HorseSpawnListener;
import us.mytheria.bloblib.entities.ListenerManager;
import me.anjoismysign.blobhorse.director.HorseManagerDirector;

public class HorseListenerManager extends ListenerManager {
    private final HorseManagerDirector managerDirector;

    public HorseListenerManager(HorseManagerDirector managerDirector) {
        super(managerDirector);
        this.managerDirector = managerDirector;
        add(new HorseMountListener(this));
        add(new HorseSpawnListener(this));

        reload();
    }

    @Override
    public HorseManagerDirector getManagerDirector() {
        return managerDirector;
    }
}