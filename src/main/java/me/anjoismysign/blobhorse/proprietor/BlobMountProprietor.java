package me.anjoismysign.blobhorse.proprietor;

import me.anjoismysign.blobhorse.BlobHorse;
import me.anjoismysign.blobhorse.constant.BlobHorseMessages;
import me.anjoismysign.blobhorse.director.manager.MountManager;
import me.anjoismysign.blobhorse.entity.BlobHorseMount;
import me.anjoismysign.blobhorse.entity.HorseAsset;
import me.anjoismysign.blobhorse.entity.HorseMount;
import me.anjoismysign.psa.lehmapp.Lehmapp;
import me.anjoismysign.psa.lehmapp.LehmappCrudable;
import me.anjoismysign.psa.lehmapp.LehmappSerializable;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.bloblib.entities.BlobScheduler;
import us.mytheria.bloblib.entities.PlayerDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class BlobMountProprietor implements LehmappSerializable, MountProprietor {
    private static final BlobHorse PLUGIN = BlobHorse.getInstance();
    private static final BlobScheduler SCHEDULER = PLUGIN.getScheduler();

    private final PlayerDecorator playerDecorator;
    private final List<String> horseAssets;

    private int currentHorseAssetIndex;

    private BlobMountProprietor(
            @NotNull PlayerDecorator playerDecorator,
            @NotNull List<String> horseAssets,
            @Nullable Integer currentHorseMount) {
        this.playerDecorator = playerDecorator;
        this.horseAssets = horseAssets;
        this.currentHorseAssetIndex = currentHorseMount == null ? 0 : currentHorseMount;
    }

    public List<String> getHorseAssets() {
        return horseAssets;
    }

    @Override
    public BlobHorseMount getCurrentHorseMount() {
        return MountManager.ofOwner(player());
    }

    public void setCurrentHorse(int index) {
        if (horseAssets.isEmpty()){
            Player player = player();
            BlobHorseMessages.NOT_OWNED_YET.handle(player);
            return;
        }
        String mountIdentifier = horseAssets.get(index);
        @Nullable HorseMount oldMount = getCurrentHorseMount();
        if (oldMount != null && oldMount.getEntity() != null)
            oldMount.getEntity().remove();
        this.currentHorseAssetIndex = index;
        @Nullable HorseAsset asset = PLUGIN.getHorseAssetManager().getGeneration(mountIdentifier).asset();
        HorseMount mount = BlobHorseMount.of(asset, this);
        mount.spawn();
        AbstractHorse abstractHorse = Objects.requireNonNull(mount.getEntity(), "'mount' has no entity");
        abstractHorse.addPassenger(player());
    }

    @Override
    public void spawnMount() {
        setCurrentHorse(currentHorseAssetIndex);
    }

    public static BlobMountProprietor deserialize(@NotNull LehmappCrudable crudable) {
        String identification = crudable.getIdentification();
        Lehmapp lehmapp = crudable.getLehmapp();
        PlayerDecorator playerDecorator = PlayerDecorator.address(identification);
        @NotNull List<String> horseAssets = (List<String>) lehmapp.getOrDefault("horseAssets", new ArrayList<>());
        horseAssets = new ArrayList<>(horseAssets);
        @Nullable Integer currentHorseMount = lehmapp.getInteger("currentHorseAsset");
        return new BlobMountProprietor(playerDecorator,horseAssets,currentHorseMount);
    }

    @NotNull
    public Player player() {
        return Objects.requireNonNull(playerDecorator.address().look(), "why is 'player' null?");
    }

    public @NotNull LehmappCrudable serialize() {
        Player lookup = player();
        UUID uuid = lookup.getUniqueId();
        LehmappCrudable crudable = new LehmappCrudable(uuid.toString(), new Lehmapp());
        Lehmapp lehmapp = crudable.getLehmapp();
        lehmapp.put("horseAssets", horseAssets);
        lehmapp.put("currentHorseAsset", currentHorseAssetIndex);
        return crudable;
    }
}