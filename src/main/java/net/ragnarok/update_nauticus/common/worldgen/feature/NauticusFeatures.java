package net.ragnarok.update_nauticus.common.worldgen.feature;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.ragnarok.update_nauticus.UpdateNauticus;
import net.ragnarok.update_nauticus.common.worldgen.feature.abyssal_vents.GiantVentFeature;
import net.ragnarok.update_nauticus.common.worldgen.feature.abyssal_vents.VentFeature;

public class NauticusFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registry.FEATURE_REGISTRY, UpdateNauticus.MOD_ID);

    public static final Feature<NoneFeatureConfiguration> VENT = register("vent", new VentFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> GIANT_VENT = register("giant_vent", new GiantVentFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, F value)
    {
        FEATURES.register(key, () -> value);
        return value;
    }
}
