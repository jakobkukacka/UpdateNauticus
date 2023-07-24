package net.ragnarok.update_nauticus.common.worldgen;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.ragnarok.update_nauticus.common.biome.NauticusBiomes;
import net.ragnarok.update_nauticus.init.ModBlocks;

public class NauticusSurfaceRuleData {
    private static final SurfaceRules.RuleSource MAGMA = makeStateRule(ModBlocks.MOLTEN_TUFF.get());
    private static final SurfaceRules.RuleSource TUFF = makeStateRule(Blocks.TUFF);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);

    public static SurfaceRules.RuleSource makeRules()
    {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        SurfaceRules.ConditionSource surfaceNoise = SurfaceRules.noiseCondition(Noises.SWAMP, 0.45D); // The lower the threshold the more often true it is
        SurfaceRules.ConditionSource surfaceNoiseOuter = SurfaceRules.noiseCondition(Noises.SWAMP, 0.1D);

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(NauticusBiomes.ABYSSAL_VENTS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
                                        SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(2,false, 2, CaveSurface.FLOOR),
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(surfaceNoise, MAGMA),
                                                        TUFF))
                                                )
                        )),


                // Default to a grass and dirt surface
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
