package net.ragnarok.update_nauticus.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ragnarok.update_nauticus.UpdateNauticus;
import net.ragnarok.update_nauticus.common.block.ThermalVentBlock;
import net.ragnarok.update_nauticus.common.item.ModCreativeModeTabs;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UpdateNauticus.MOD_ID);

    public static final RegistryObject<Block> MOLTEN_TUFF = registerBlock("molten_tuff",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f)
                    .requiresCorrectToolForDrops()
                    .lightLevel((blockState) -> 5)
            ), ModCreativeModeTabs.UPDATE_NAUTICUS_TAB);
    public static final RegistryObject<Block> TUFF_VENT = registerBlock("tuff_vent",
            () -> new ThermalVentBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .lightLevel((blockState) -> blockState.getValue(ThermalVentBlock.THICKNESS) == DripstoneThickness.TIP ? 7 : 0)
            ), ModCreativeModeTabs.UPDATE_NAUTICUS_TAB);
    public static final RegistryObject<Block> DEEPSLATE_VENT = registerBlock("deepslate_vent",
            () -> new ThermalVentBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .lightLevel((blockState) -> blockState.getValue(ThermalVentBlock.THICKNESS) == DripstoneThickness.TIP ? 7 : 0)
            ), ModCreativeModeTabs.UPDATE_NAUTICUS_TAB);

    //UTILS
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registryBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registryBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
