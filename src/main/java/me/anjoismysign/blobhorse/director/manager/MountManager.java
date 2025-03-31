package me.anjoismysign.blobhorse.director.manager;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.anjoismysign.blobhorse.director.HorseManager;
import me.anjoismysign.blobhorse.director.HorseManagerDirector;
import me.anjoismysign.blobhorse.entity.BlobHorseMount;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MountManager extends HorseManager implements Listener {

    private static final Map<UUID, BlobHorseMount> HORSE_MOUNTS = new HashMap<>();

    public MountManager(HorseManagerDirector managerDirector) {
        super(managerDirector);
        Bukkit.getPluginManager().registerEvents(this,getPlugin());
    }

    @Nullable
    public static BlobHorseMount horseMount(@NotNull UUID uuid){
        return HORSE_MOUNTS.get(uuid);
    }

    @Nullable
    public static BlobHorseMount ofOwner(@NotNull Player player){
        return HORSE_MOUNTS.values().stream()
                .filter(mount -> mount.proprietor().player().getUniqueId().equals(player.getUniqueId()))
                .findFirst().orElse(null);
    }

    public static void addMount(@NotNull BlobHorseMount mount){
        UUID uuid = mount.entityAddress().look().getUniqueId();
        HORSE_MOUNTS.put(uuid, mount);
    }

    public static void removeAll(){
        HORSE_MOUNTS.values().forEach(mount->mount.getEntity().remove());
    }

    @EventHandler
    public void remove(@NotNull EntityRemoveEvent event){
        UUID uuid = event.getEntity().getUniqueId();
        HORSE_MOUNTS.remove(uuid);
    }

}
