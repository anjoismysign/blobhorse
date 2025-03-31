package me.anjoismysign.blobhorse.constant;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.anjoismysign.blobhorse.BlobHorse;
import me.anjoismysign.blobhorse.entity.BlobHorseMount;
import me.anjoismysign.blobhorse.entity.HorseAsset;
import me.anjoismysign.blobhorse.proprietor.BlobMountProprietor;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.bloblib.api.BlobLibInventoryAPI;
import us.mytheria.bloblib.api.BlobLibTranslatableAPI;
import us.mytheria.bloblib.entities.inventory.BlobInventoryClickEvent;
import us.mytheria.bloblib.entities.message.BlobSound;
import us.mytheria.bloblib.entities.translatable.TranslatableItem;
import us.mytheria.bloblib.entities.translatable.TranslatableSnippet;
import us.mytheria.bloblib.itemstack.ItemStackModder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public enum BlobHorseUI {
    STASHED("BlobHorse-Stashed"),
    UNSTASHED("BlobHorse-Unstashed");

    private final @NotNull String key;
    BlobHorseUI(@NotNull String key){
        this.key = key;
    }

    public void open(@NotNull Player player){
        BlobLibInventoryAPI.getInstance().trackInventory(player, key).getInventory().open(player);
    }

    public static void reload(){
        var unstashed = BlobLibInventoryAPI.getInstance().getInventoryDataRegistry(UNSTASHED.key);
        unstashed.onClick("Select", clickEvent->{
            var proprietor = proprietor(clickEvent);
            Player player = proprietor.player();
            BlobLibInventoryAPI.getInstance().selector(
                    player,
                    "STASH",
                    ()->{
                        return proprietor.mapHorseAssets().entrySet().stream().toList();
                    },
                    entry->{
                        int index = entry.getKey();
                        proprietor.setCurrentHorse(index);
                        BlobSound.by("BlobHorse.Call").handle(player);
                        proprietor.player().closeInventory();
                    },
                    entry->{
                        HorseAsset horseAsset = BlobHorse.getInstance().getHorseAssetManager().getGeneration(entry.getValue()).asset();
                        EntityType entityType = horseAsset.type();
                        Material material = Material.valueOf(entityType+"_SPAWN_EGG");
                        TranslatableItem item = TranslatableItem.by("BlobHorse.Select");
                        Objects.requireNonNull(item, "'item' cannot be null");
                        ItemStack real = item.localize(player).getClone();
                        ItemStack itemStack = new ItemStack(material);
                        itemStack.setItemMeta(real.getItemMeta());
                        ItemStackModder.mod(itemStack).replace("%key%", horseAsset.display(player));
                        return itemStack;
                    });
        });
        unstashed.onClick("PutAway", clickEvent->{
            var proprietor = proprietor(clickEvent);
            @Nullable BlobHorseMount horseMount = proprietor.getCurrentHorseMount();
            if (horseMount == null)
                return;
            horseMount.getEntity().remove();
            proprietor.player().closeInventory();
        });
        var stashed = BlobLibInventoryAPI.getInstance().getInventoryDataRegistry(STASHED.key);
        stashed.onClick("Call", clickEvent->{
            var proprietor = proprietor(clickEvent);
            if (proprietor.getCurrentHorseMount() != null) {
                return;
            }
            proprietor.spawnMount();
            Player player = proprietor.player();
            BlobSound.by("BlobHorse.Call").handle(player);
            proprietor.player().closeInventory();
        });
        stashed.onClick("Select", clickEvent->{
            var proprietor = proprietor(clickEvent);
            Player player = proprietor.player();
            BlobLibInventoryAPI.getInstance().selector(
                    player,
                    "STASH",
                    ()->{
                        return proprietor.mapHorseAssets().entrySet().stream().toList();
                    },
                    entry->{
                        int index = entry.getKey();
                        proprietor.setCurrentHorse(index);
                        BlobSound.by("BlobHorse.Call").handle(player);
                        proprietor.player().closeInventory();
                    },
                    entry->{
                        HorseAsset horseAsset = BlobHorse.getInstance().getHorseAssetManager().getGeneration(entry.getValue()).asset();
                        EntityType entityType = horseAsset.type();
                        Material material = Material.valueOf(entityType+"_SPAWN_EGG");
                        TranslatableItem item = TranslatableItem.by("BlobHorse.Select");
                        Objects.requireNonNull(item, "'item' cannot be null");
                        ItemStack real = item.localize(player).getClone();
                        ItemStack itemStack = new ItemStack(material);
                        itemStack.setItemMeta(real.getItemMeta());
                        ItemStackModder.mod(itemStack).replace("%key%", horseAsset.display(player));
                        return itemStack;
                    });
        });
    }

    private static BlobMountProprietor proprietor(@NotNull BlobInventoryClickEvent clickEvent){
        Player player = (Player) clickEvent.getWhoClicked();
        return BlobHorse.getInstance().getMountProprietorManager().get(player);
    }
}
