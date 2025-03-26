package me.anjoismysign.blobhorse.director.manager;

import us.mytheria.bloblib.entities.ListenerManager;
import me.anjoismysign.blobhorse.director.HorseManagerDirector;

public class HorseListenerManager extends ListenerManager {
    private final HorseManagerDirector managerDirector;

    public HorseListenerManager(HorseManagerDirector managerDirector) {
        super(managerDirector);
        this.managerDirector = managerDirector;
        //add(new HorseListener(this));

        reload();
    }

    @Override
    public HorseManagerDirector getManagerDirector() {
        return managerDirector;
    }
}