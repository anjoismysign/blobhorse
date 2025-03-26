package me.anjoismysign.blobhorse.director;

import us.mytheria.bloblib.entities.GenericManager;
import me.anjoismysign.blobhorse.BlobHorse;

public class HorseManager extends GenericManager<BlobHorse, HorseManagerDirector> {

    public HorseManager(HorseManagerDirector managerDirector) {
        super(managerDirector);
    }
}