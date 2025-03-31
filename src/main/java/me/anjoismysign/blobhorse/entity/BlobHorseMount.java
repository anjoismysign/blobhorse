package me.anjoismysign.blobhorse.entity;

import me.anjoismysign.blobhorse.director.manager.MountManager;
import me.anjoismysign.blobhorse.proprietor.BlobMountProprietor;
import org.bukkit.Location;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.bloblib.entities.MutableAddress;

public record BlobHorseMount(
        @NotNull HorseAsset getHorseAsset,
        @NotNull MutableAddress<AbstractHorse> entityAddress,
        @NotNull BlobMountProprietor proprietor)
implements HorseMount {

    @NotNull
    public static BlobHorseMount of(
            @NotNull HorseAsset horseAsset,
            @NotNull BlobMountProprietor proprietor){
        return new BlobHorseMount(horseAsset, MutableAddress.of(null), proprietor);
    }

    @Override
    public @Nullable AbstractHorse getEntity() {
        return entityAddress.look();
    }

    @Override
    public void spawn() {
        @Nullable Player owner = proprietor().player();
        HorseAsset asset = getHorseAsset;
        EntityType type = asset.type();
        Location location = owner.getLocation();
        Entity entity = location.getWorld().spawnEntity(location, type);
        AbstractHorse abstractHorse = (AbstractHorse) entity;
        entityAddress.set(abstractHorse);
        MountManager.addMount(this);
        asset.apply(this);
    }
}
