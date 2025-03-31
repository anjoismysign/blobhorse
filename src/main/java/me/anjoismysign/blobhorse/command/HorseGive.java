package me.anjoismysign.blobhorse.command;

import me.anjoismysign.blobhorse.BlobHorse;
import me.anjoismysign.blobhorse.entity.HorseAsset;
import me.anjoismysign.skeramidcommands.command.Command;
import me.anjoismysign.skeramidcommands.commandtarget.BukkitCommandTarget;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HorseGive {

    private static final BlobHorse PLUGIN = BlobHorse.getInstance();

    public static void command(@NotNull Command pyramid){
        Command give = pyramid.child("give");
        var onlinePlayers = BukkitCommandTarget.ONLINE_PLAYERS();
        var horseAssetManager = PLUGIN.getHorseAssetManager();
        give.setParameters(onlinePlayers, horseAssetManager);
        give.onExecute(((permissionMessenger, strings) -> {
            Player player = onlinePlayers.parse(strings[0]);
            if (player == null) {
                return;
            }
            HorseAsset asset = horseAssetManager.parse(strings[1]);
            if (asset == null) {
                return;
            }
            PLUGIN.getMountProprietorManager().get(player).getHorseAssets().add(asset.identifier());
            permissionMessenger.sendMessage("gave "+asset.identifier()+" to "+player.getName());
        }));
    }
}
