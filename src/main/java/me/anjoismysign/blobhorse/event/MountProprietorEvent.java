package me.anjoismysign.blobhorse.event;

import me.anjoismysign.blobhorse.proprietor.BlobMountProprietor;
import org.jetbrains.annotations.NotNull;
import us.mytheria.bloblib.managers.serializablemanager.BukkitSerializableEvent;

public abstract class MountProprietorEvent extends BukkitSerializableEvent<BlobMountProprietor> {

    public MountProprietorEvent(@NotNull BlobMountProprietor serializable,
                               boolean isAsync) {
        super(serializable, isAsync);
    }

    @NotNull
    public BlobMountProprietor getMountProprietor() {
        return serializable;
    }
}
