package me.anjoismysign.blobhorse.event;

import me.anjoismysign.blobhorse.proprietor.BlobMountProprietor;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MountProprietorQuitEvent extends MountProprietorEvent {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public MountProprietorQuitEvent(@NotNull BlobMountProprietor serializable) {
        super(serializable, false);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
