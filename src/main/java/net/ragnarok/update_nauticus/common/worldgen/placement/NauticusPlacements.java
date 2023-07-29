package net.ragnarok.update_nauticus.common.worldgen.placement;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.ragnarok.update_nauticus.UpdateNauticus;
import net.ragnarok.update_nauticus.common.worldgen.feature.NauticusFeatures;
import net.ragnarok.update_nauticus.common.worldgen.feature.configuration.NauticusConfiguredFeatures;

import java.util.List;
import java.util.function.Supplier;

public class NauticusPlacements {

    public static DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, UpdateNauticus.MOD_ID);

    public static final RegistryObject<PlacedFeature> VENT = register("vent", NauticusConfiguredFeatures.VENT, () -> List.of(NoiseBasedCountPlacement.of(120, 80.0D, 0.0D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()));

    public static RegistryObject<PlacedFeature> register(String key, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers)
    {
        return PLACED_FEATURES.register(key, () -> new PlacedFeature(Holder.hackyErase(feature), List.copyOf(modifiers)));
    }

    public static RegistryObject<PlacedFeature> register(String key, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers)
    {
        return register(key, feature, List.of(modifiers));
    }

    // NOTE: We use a supplier for modifiers as they may reference blocks which haven't been registered by Forge yet
    public static RegistryObject<PlacedFeature> register(String key, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, Supplier<List<PlacementModifier>> modifiers)
    {
        return PLACED_FEATURES.register(key, () -> new PlacedFeature(Holder.hackyErase(feature.getHolder().orElseThrow()), List.copyOf(modifiers.get())));
    }

    public static RegistryObject<PlacedFeature> register(String key, RegistryObject<? extends ConfiguredFeature<?, ?>> feature)
    {
        return register(key, feature, () -> List.of());
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
