package net.ragnarok.update_nauticus.common.item;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.ragnarok.update_nauticus.init.ModBlocks;

public class ModCreativeModeTabs {
    public static final CreativeModeTab UPDATE_NAUTICUS_TAB = new CreativeModeTab("update_nauticus_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.MOLTEN_TUFF.get().asItem());
        }
    };
}
