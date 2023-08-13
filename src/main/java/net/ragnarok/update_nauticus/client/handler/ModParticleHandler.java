package net.ragnarok.update_nauticus.client.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ragnarok.update_nauticus.UpdateNauticus;
import net.ragnarok.update_nauticus.client.particle.AdaptiveUnderwaterParticle;
import net.ragnarok.update_nauticus.client.particle.VentAshParticle;
import net.ragnarok.update_nauticus.init.ModParticles;

@Mod.EventBusSubscriber(modid = UpdateNauticus.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticleHandler
{
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event)
    {
        event.register(ModParticles.VENT_ASH.get(), VentAshParticle.Provider::new);
        event.register(ModParticles.ADAPTIVE_UNDERWATER.get(), AdaptiveUnderwaterParticle.AdaptiveUnderwaterProvider::new);
    }

}