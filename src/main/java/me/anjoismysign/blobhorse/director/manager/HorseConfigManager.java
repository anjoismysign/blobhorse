package me.anjoismysign.blobhorse.director.manager;

import me.anjoismysign.blobhorse.constant.BlobHorseUI;
import org.bukkit.configuration.ConfigurationSection;
import us.mytheria.bloblib.entities.ConfigDecorator;
import me.anjoismysign.blobhorse.director.HorseManager;
import me.anjoismysign.blobhorse.director.HorseManagerDirector;

public class HorseConfigManager extends HorseManager {
    private boolean tinyDebug;

    public HorseConfigManager(HorseManagerDirector managerDirector) {
        super(managerDirector);
        reload();
    }

    @Override
    public void reload() {
        BlobHorseUI.reload();

        ConfigDecorator configDecorator = getPlugin().getConfigDecorator();
        ConfigurationSection settingsSection = configDecorator.reloadAndGetSection("Settings");
        tinyDebug = settingsSection.getBoolean("Tiny-Debug");
    }

    public boolean tinyDebug() {
        return tinyDebug;
    }
}