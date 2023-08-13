package net.ragnarok.update_nauticus.common.worldgen.feature.configuration;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.ragnarok.update_nauticus.UpdateNauticus;
import net.ragnarok.update_nauticus.common.worldgen.feature.NauticusFeatures;

import java.util.function.Supplier;

public class NauticusConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, UpdateNauticus.MOD_ID);

    public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> VENT = register("vent", NauticusFeatures.VENT, () -> NoneFeatureConfiguration.INSTANCE);
    public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> GIANT_VENT = register("giant_vent", NauticusFeatures.GIANT_VENT, () -> NoneFeatureConfiguration.INSTANCE);


    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String key, F feature, Supplier<FC> configurationSupplier)
    {
        return CONFIGURED_FEATURES.register(key, () -> new ConfiguredFeature<>(feature, configurationSupplier.get()));
    }

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}
