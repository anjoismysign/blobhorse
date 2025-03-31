package me.anjoismysign.blobhorse.listener;

import me.anjoismysign.blobhorse.BlobHorse;
import me.anjoismysign.blobhorse.director.manager.HorseListenerManager;
import me.anjoismysign.blobhorse.proprietor.BlobMountProprietor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.bloblib.entities.translatable.TranslatableItem;

public class HorseSpawnListener extends BlobHorseListener{
    public HorseSpawnListener(HorseListenerManager listenerManager) {
        super(listenerManager);
    }

    @EventHandler
    public void spawn(PlayerInteractEvent event){
        if (event.getHand() != EquipmentSlot.HAND)
            return;
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
            return;
        @Nullable ItemStack itemStack = event.getItem();
        if (itemStack == null)
            return;
        @Nullable TranslatableItem translatableItem = TranslatableItem.byItemStack(itemStack);
        if (translatableItem == null)
            return;
        if (!translatableItem.identifier().equals("BlobHorse.Saddle"))
            return;
        @NotNull BlobMountProprietor mountProprietor = BlobHorse.getInstance().getMountProprietorManager().get(event.getPlayer());
        mountProprietor.setCurrentHorse(0);
    }

    @Override
    public boolean checkIfShouldRegister() {
        return true;
    }
}
