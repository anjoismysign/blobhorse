package me.anjoismysign.blobhorse.listener;

import me.anjoismysign.blobhorse.director.manager.HorseListenerManager;
import me.anjoismysign.blobhorse.director.manager.MountManager;
import me.anjoismysign.blobhorse.entity.BlobHorseMount;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityMountEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HorseMountListener extends BlobHorseListener{
    public HorseMountListener(HorseListenerManager listenerManager) {
        super(listenerManager);
    }

    @EventHandler
    public void mount(EntityMountEvent event){
        Entity mount = event.getMount();
        UUID uuid = mount.getUniqueId();
        @Nullable BlobHorseMount horseMount = MountManager.horseMount(uuid);
        if (horseMount == null)
            return;
        AbstractHorse horse = horseMount.getEntity();
        if (horse.getOwnerUniqueId() == event.getEntity().getUniqueId())
            return;
        event.setCancelled(true);
    }

    @Override
    public boolean checkIfShouldRegister() {
        return true;
    }
}
