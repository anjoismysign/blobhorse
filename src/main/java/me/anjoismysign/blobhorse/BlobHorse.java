package me.anjoismysign.blobhorse;

import org.jetbrains.annotations.NotNull;
import us.mytheria.bloblib.managers.BlobPlugin;
import us.mytheria.bloblib.managers.IManagerDirector;
import me.anjoismysign.blobhorse.director.HorseManagerDirector;

public final class BlobHorse extends BlobPlugin {
    private IManagerDirector proxy;

    @Override
    public void onEnable() {
        HorseManagerDirector director = new HorseManagerDirector(this);
        proxy = director.proxy();
    }

    @NotNull
    public IManagerDirector getManagerDirector() {
        return proxy;
    }
}
