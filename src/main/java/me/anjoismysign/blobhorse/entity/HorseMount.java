package me.anjoismysign.blobhorse.entity;

import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HorseMount {
    @Nullable
    AbstractHorse getEntity();

    @NotNull
    HorseAsset getHorseAsset();

    default boolean isValid(){
        @Nullable Entity entity = getEntity();
        return entity == null || !entity.isValid();
    }

    void spawn();
}
