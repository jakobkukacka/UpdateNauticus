package net.ragnarok.update_nauticus.common.worldgen.feature.abyssal_vents;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.ragnarok.update_nauticus.common.block.ThermalVentBlock;
import net.ragnarok.update_nauticus.init.ModBlocks;

public class VentFeature extends Feature<NoneFeatureConfiguration> {
    // TODO: After adding more Vents, (either let me pass the material to here or) check block below
    public VentFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        boolean check = false;

        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos chosen_pos = pContext.origin();
        RandomSource randomsource = pContext.random();
        int j = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR, chosen_pos.getX(), chosen_pos.getZ());
        BlockPos place_pos = new BlockPos(chosen_pos.getX(), j, chosen_pos.getZ());
        
        if (worldgenlevel.getBlockState(place_pos).is(Blocks.WATER) && (!worldgenlevel.getBlockState(place_pos.below()).is(ModBlocks.TUFF_VENT.get()) && !worldgenlevel.getBlockState(place_pos.below()).is(ModBlocks.DEEPSLATE_VENT.get()))) { //TODO Solve this with TAGS
            BlockState tuff_vent_blockstate = worldgenlevel.getBlockState(place_pos.below()).is(Blocks.TUFF) ? ModBlocks.TUFF_VENT.get().defaultBlockState() : ModBlocks.DEEPSLATE_VENT.get().defaultBlockState();
            int vent_height = (int) (randomsource.triangle(0,6)) + 1;

            for(int i = 0; i <= vent_height; i++) {
                if(tuff_vent_blockstate.canSurvive(worldgenlevel, place_pos)) {
                    DripstoneThickness thickness = vent_height == i ? DripstoneThickness.TIP : vent_height-i == 1 ? DripstoneThickness.MIDDLE : DripstoneThickness.BASE;
                    worldgenlevel.setBlock(place_pos, tuff_vent_blockstate.setValue(ThermalVentBlock.WATERLOGGED, true).setValue(ThermalVentBlock.THICKNESS, thickness), 2);
                    check = true;
                } else break;
                place_pos = place_pos.above();
            }
        }

        return check;
    }
}
