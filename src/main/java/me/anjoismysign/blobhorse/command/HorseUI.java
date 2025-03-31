package me.anjoismysign.blobhorse.command;

import me.anjoismysign.blobhorse.BlobHorse;
import me.anjoismysign.blobhorse.constant.BlobHorseUI;
import me.anjoismysign.blobhorse.proprietor.MountProprietor;
import me.anjoismysign.skeramidcommands.command.Command;
import me.anjoismysign.skeramidcommands.commandtarget.BukkitCommandTarget;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HorseUI {

    private static final BlobHorse PLUGIN = BlobHorse.getInstance();

    public static void command(@NotNull Command pyramid){
        Command ui = pyramid.child("ui");
        var onlinePlayers = BukkitCommandTarget.ONLINE_PLAYERS();
        ui.setParameters(onlinePlayers);
        ui.onExecute(((permissionMessenger, strings) -> {
            boolean noParameter = strings[0].trim().isEmpty();
            Player player = noParameter
                    ?
                    permissionMessenger.adapt().bukkit().getPlayer()
                    :
                    onlinePlayers.parse(strings[0]);
            if (player == null) {
                System.out.println("player is null");
                return;
            }
            MountProprietor mountProprietor = BlobHorse.getInstance().getMountProprietorManager().get(player);
            boolean isStashed = mountProprietor.getCurrentHorseMount() == null;
            if (isStashed){
                BlobHorseUI.STASHED.open(player);
            } else {
                BlobHorseUI.UNSTASHED.open(player);
            }
        }));
    }
}
