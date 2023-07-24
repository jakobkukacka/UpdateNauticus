package net.ragnarok.update_nauticus.common.biome;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.ragnarok.update_nauticus.UpdateNauticus;

public class NauticusBiomes {
    public static final ResourceKey<Biome> ABYSSAL_VENTS = register("abyssal_vents");

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(UpdateNauticus.MOD_ID, name));
    }
}
