package net.ragnarok.update_nauticus.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ragnarok.update_nauticus.UpdateNauticus;
import net.ragnarok.update_nauticus.common.block.blockentity.CrustedLavaBlockEntity;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UpdateNauticus.MOD_ID);

    public static final RegistryObject<BlockEntityType<CrustedLavaBlockEntity>> CRUSTED_LAVA_ENTITY =
            BLOCK_ENTITIES.register("crusted_lava", () ->
                    BlockEntityType.Builder.of(CrustedLavaBlockEntity::new,
                            ModBlocks.CRUSTED_LAVA.get()).build(null)
            );
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
