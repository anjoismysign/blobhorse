package me.anjoismysign.blobhorse;

import me.anjoismysign.blobhorse.command.BlobHorseCommand;
import me.anjoismysign.blobhorse.director.manager.MountManager;
import me.anjoismysign.blobhorse.entity.HorseAsset;
import me.anjoismysign.blobhorse.proprietor.BlobMountProprietor;
import me.anjoismysign.blobhorse.event.MountProprietorJoinEvent;
import me.anjoismysign.blobhorse.event.MountProprietorQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import us.mytheria.bloblib.BlobLib;
import us.mytheria.bloblib.entities.BlobScheduler;
import us.mytheria.bloblib.managers.BlobPlugin;
import us.mytheria.bloblib.managers.IManagerDirector;
import me.anjoismysign.blobhorse.director.HorseManagerDirector;
import us.mytheria.bloblib.managers.asset.BukkitIdentityManager;
import us.mytheria.bloblib.managers.serializablemanager.BukkitSerializableManager;

public final class BlobHorse extends BlobPlugin {
    private IManagerDirector proxy;
    private BukkitIdentityManager<HorseAsset> horseAssetManager;
    private BukkitSerializableManager<BlobMountProprietor> mountProprietorManager;

    public static BlobHorse getInstance() {
        return JavaPlugin.getPlugin(BlobHorse.class);
    }

    @Override
    public void onEnable() {
        HorseManagerDirector director = new HorseManagerDirector(this);
        proxy = director.proxy();

        BlobScheduler scheduler = getScheduler();
        scheduler.sync(() -> {
            mountProprietorManager = registerSerializableManager(BlobMountProprietor.class, BlobMountProprietor::deserialize, MountProprietorJoinEvent::new, MountProprietorQuitEvent::new, null);
        });

        horseAssetManager = BlobLib.getInstance().getPluginManager().addIdentityManager(HorseAsset.Info.class, this, "HorseData");

        BlobHorseCommand.INSTANCE.initialize();
    }

    @Override
    public void onDisable(){
        super.onDisable();
        MountManager.removeAll();
    }

    @NotNull
    public IManagerDirector getManagerDirector() {
        return proxy;
    }

    public BukkitIdentityManager<HorseAsset> getHorseAssetManager() {
        return horseAssetManager;
    }

    public BukkitSerializableManager<BlobMountProprietor> getMountProprietorManager() {
        return mountProprietorManager;
    }
}
