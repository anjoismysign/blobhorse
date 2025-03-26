package me.anjoismysign.blobhorse.director;

import org.jetbrains.annotations.NotNull;
import us.mytheria.bloblib.entities.GenericManagerDirector;
import me.anjoismysign.blobhorse.BlobHorse;
import me.anjoismysign.blobhorse.director.manager.HorseConfigManager;
import me.anjoismysign.blobhorse.director.manager.HorseListenerManager;

public class HorseManagerDirector extends GenericManagerDirector<BlobHorse> {

    public HorseManagerDirector(BlobHorse plugin) {
        super(plugin);
        addManager("ConfigManager",
                new HorseConfigManager(this));
        addManager("ListenerManager",
                new HorseListenerManager(this));
    }

    /**
     * From top to bottom, follow the order.
     */
    @Override
    public void reload() {
        getConfigManager().reload();
    }

    @Override
    public void unload() {
    }

    @NotNull
    public final HorseConfigManager getConfigManager() {
        return getManager("ConfigManager", HorseConfigManager.class);
    }


    @NotNull
    public final HorseListenerManager getListenerManager(){
        return getManager("ListenerManager", HorseListenerManager.class);
    }
}