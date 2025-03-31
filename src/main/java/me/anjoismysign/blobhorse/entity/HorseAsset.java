package me.anjoismysign.blobhorse.entity;

import me.anjoismysign.holoworld.asset.DataAsset;
import me.anjoismysign.holoworld.asset.IdentityGenerator;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.bloblib.api.BlobLibTranslatableAPI;
import us.mytheria.bloblib.entities.translatable.TranslatableItem;
import us.mytheria.bloblib.entities.translatable.TranslatableSnippet;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record HorseAsset(
        @NotNull EntityType type,
        @Nullable Horse.Color color,
        @Nullable Horse.Style style,
        double health,
        double speed,
        double jumpStrength,
        double stepHeight,
        double scale,
        double safeFallDistance,
        double gravity,
        @NotNull String identifier)
        implements DataAsset {

    @NotNull
    public String display(@NotNull Player player){
        String identifier = identifier();
        @Nullable TranslatableSnippet snippet = BlobLibTranslatableAPI.getInstance().getTranslatableSnippet(identifier, player);
        return snippet == null ? identifier : snippet.get();
    }

    private Map<Attribute, Double> ATTRIBUTES(){
        return Map.of(
                Attribute.MAX_HEALTH, health,
                Attribute.MOVEMENT_SPEED, speed,
                Attribute.JUMP_STRENGTH, jumpStrength,
                Attribute.STEP_HEIGHT, stepHeight,
                Attribute.SCALE, scale,
                Attribute.SAFE_FALL_DISTANCE, safeFallDistance,
                Attribute.GRAVITY, gravity);
    }

    public void apply(@NotNull BlobHorseMount mount){
        Entity entity = Objects.requireNonNull(mount.getEntity(), "'mount' has no entity attached");
        Player owner = mount.proprietor().player();
        if ((color != null || style != null) && entity instanceof Horse horse){
            if (color != null)
                horse.setColor(color);
            if (style != null)
                horse.setStyle(style);
        }
        AbstractHorse abstractHorse = (AbstractHorse) entity;
        abstractHorse.setAdult();
        abstractHorse.setTamed(true);
        abstractHorse.setOwner(owner);
        abstractHorse.getInventory().setSaddle(TranslatableItem.by("BlobHorse.Saddle").getClone());
        ATTRIBUTES().forEach((attribute,baseValue) -> {
            abstractHorse.registerAttribute((attribute));
            abstractHorse.getAttribute(attribute).setBaseValue(baseValue);
        });
        abstractHorse.setHealth(health);

    }

    public record Info(@NotNull EntityType type,
                       @Nullable Horse.Color color,
                       @Nullable Horse.Style style,
                       String health,
                       String speed,
                       String jumpStrength,
                       String stepHeight,
                       String scale,
                       String safeFallDistance,
                       String gravity)
            implements IdentityGenerator<HorseAsset> {

        private static final Set<EntityType> ALLOWED_TYPE = Set.of(
                EntityType.HORSE,
                EntityType.SKELETON_HORSE,
                EntityType.ZOMBIE_HORSE,
                EntityType.DONKEY,
                EntityType.MULE);

        private static final String TYPE_FAIL_MESSAGE = "Invalid horse type. Allowed: "+ Arrays.toString(ALLOWED_TYPE.toArray());

        @Override
        public @NotNull HorseAsset generate(@NotNull String identifier) {
            if (!ALLOWED_TYPE.contains(type))
                throw new IllegalArgumentException(TYPE_FAIL_MESSAGE);
            double parsedHealth = Double.parseDouble(health);
            double parsedSpeed = Double.parseDouble(speed);
            double parsedJumpStrength = Double.parseDouble(jumpStrength);
            double parsedStepHeight = Double.parseDouble(stepHeight);
            double parsedScale = Double.parseDouble(scale);
            double parsedSafeFallDistance = Double.parseDouble(safeFallDistance);
            double parsedGravity = Double.parseDouble(gravity);
            return new HorseAsset(
                    type,
                    color,
                    style,
                    parsedHealth,
                    parsedSpeed,
                    parsedJumpStrength,
                    parsedStepHeight,
                    parsedScale,
                    parsedSafeFallDistance,
                    parsedGravity,
                    identifier);
        }
    }
}