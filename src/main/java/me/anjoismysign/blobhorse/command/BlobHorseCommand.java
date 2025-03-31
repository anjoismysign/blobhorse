package me.anjoismysign.blobhorse.command;

import me.anjoismysign.skeramidcommands.command.Command;
import me.anjoismysign.skeramidcommands.server.bukkit.BukkitAdapter;

public enum BlobHorseCommand {
    INSTANCE;

    public void initialize(){
        Command command = BukkitAdapter.getInstance().ofBukkitCommand("blobhorse");
        HorseGive.command(command);
        HorseUI.command(command);
    }
}
