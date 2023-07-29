package net.ragnarok.update_nauticus;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.ragnarok.update_nauticus.common.worldgen.feature.NauticusFeatures;
import net.ragnarok.update_nauticus.common.worldgen.feature.configuration.NauticusConfiguredFeatures;
import net.ragnarok.update_nauticus.common.worldgen.placement.NauticusPlacements;
import net.ragnarok.update_nauticus.init.ModBiomes;
import net.ragnarok.update_nauticus.common.worldgen.NauticusRegionOcean;
import net.ragnarok.update_nauticus.common.worldgen.NauticusSurfaceRuleData;
import net.ragnarok.update_nauticus.init.ModBlocks;
import net.ragnarok.update_nauticus.init.ModItems;
import org.slf4j.Logger;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UpdateNauticus.MOD_ID)
public class UpdateNauticus
{
    public static final String MOD_ID = "update_nauticus";
    private static final Logger LOGGER = LogUtils.getLogger();

    public UpdateNauticus()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModBlocks.register(modEventBus);

        ModItems.register(modEventBus);
        NauticusFeatures.register(modEventBus);
        NauticusConfiguredFeatures.register(modEventBus);
        NauticusPlacements.register(modEventBus);

        ModBiomes.register(modEventBus);
        ModBiomes.registerBiomes();



        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            // Given we only add two biomes, we should keep our weight relatively low.
            Regions.register(new NauticusRegionOcean(6));

            // Register our surface rules
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, NauticusSurfaceRuleData.makeRules());
        });
    }



    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
