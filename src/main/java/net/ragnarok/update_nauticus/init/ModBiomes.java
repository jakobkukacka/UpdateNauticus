package net.ragnarok.update_nauticus.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.ragnarok.update_nauticus.UpdateNauticus;
import net.ragnarok.update_nauticus.common.biome.NauticusBiomes;
import net.ragnarok.update_nauticus.common.biome.NauticusOverworldBiomes;

import java.util.function.Supplier;

public class ModBiomes {
    public static DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(Registry.BIOME_REGISTRY, UpdateNauticus.MOD_ID);

    public static void registerBiomes()
    {
        register(NauticusBiomes.ABYSSAL_VENTS, NauticusOverworldBiomes::abyssal_vents);
    }

    public static RegistryObject<Biome> register(ResourceKey<Biome> key, Supplier<Biome> biomeSupplier)
    {
        return BIOME_REGISTER.register(key.location().getPath(), biomeSupplier);
    }

    public static void register(IEventBus eventBus) {
        BIOME_REGISTER.register(eventBus);
    }
}
