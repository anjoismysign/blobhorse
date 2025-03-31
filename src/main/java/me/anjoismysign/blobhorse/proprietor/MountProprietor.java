package me.anjoismysign.blobhorse.proprietor;

import me.anjoismysign.blobhorse.entity.BlobHorseMount;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MountProprietor {
    @NotNull
    Player player();

    List<String> getHorseAssets();

    default Map<Integer, String> mapHorseAssets(){
        List<String> horseAssets = getHorseAssets();
        Map<Integer, String> assetMap = new HashMap<>();
        for (int i = 0; i < horseAssets.size(); i++) {
            assetMap.put(i, horseAssets.get(i));
        }
        return assetMap;
    }

    @Nullable
    BlobHorseMount getCurrentHorseMount();

    /**
     * Sets the current horse asset and spawns it
     * @param index the horse asset index
     */
    void setCurrentHorse(int index);

    void spawnMount();
}
