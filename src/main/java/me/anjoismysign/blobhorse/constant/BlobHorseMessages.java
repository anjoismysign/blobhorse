package me.anjoismysign.blobhorse.constant;

import me.anjoismysign.blobhorse.BlobHorse;
import me.anjoismysign.blobhorse.proprietor.BlobMountProprietor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import us.mytheria.bloblib.api.BlobLibMessageAPI;
import us.mytheria.bloblib.entities.inventory.BlobInventoryClickEvent;

public enum BlobHorseMessages {
    NOT_OWNED_YET("BlobHorse.Not-Owned-Yet");

    private final @NotNull String key;
    BlobHorseMessages(@NotNull String key){
        this.key = key;
    }

    public void handle(@NotNull Player player){
        player.closeInventory();
        BlobLibMessageAPI.getInstance().getMessage(key, player).handle(player);
    }
}
