package net.ragnarok.update_nauticus.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ragnarok.update_nauticus.UpdateNauticus;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, UpdateNauticus.MOD_ID);

    public static final RegistryObject<SimpleParticleType> VENT_ASH = register("vent_ash", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ADAPTIVE_UNDERWATER = register("adaptive_underwater", () -> new SimpleParticleType(false));


    private static RegistryObject<SimpleParticleType> register(String key, Supplier<SimpleParticleType> particleTypeSupplier)
    {
        return PARTICLES.register(key, particleTypeSupplier);
    }
    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
