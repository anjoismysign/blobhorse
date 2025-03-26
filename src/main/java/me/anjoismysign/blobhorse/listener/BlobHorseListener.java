package me.anjoismysign.blobhorse.listener;

import us.mytheria.bloblib.entities.BlobListener;
import me.anjoismysign.blobhorse.director.manager.HorseConfigManager;
import me.anjoismysign.blobhorse.director.manager.HorseListenerManager;

public abstract class BlobHorseListener implements BlobListener {
    private final HorseListenerManager listenerManager;

    public BlobHorseListener(HorseListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    public HorseListenerManager getListenerManager() {
        return listenerManager;
    }

    public HorseConfigManager getConfigManager() {
        return getListenerManager().getManagerDirector().getConfigManager();
    }
}
