package net.ragnarok.update_nauticus.common.block.blockentity;

import com.ibm.icu.text.PluralRules;
import com.mojang.math.Vector3f;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.ragnarok.update_nauticus.common.block.CrustedLavaBlock;
import net.ragnarok.update_nauticus.init.ModBlockEntities;
import net.ragnarok.update_nauticus.init.ModParticles;

public class CrustedLavaBlockEntity extends BlockEntity {
    public CrustedLavaBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRUSTED_LAVA_ENTITY.get(), pPos, pBlockState);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, CrustedLavaBlockEntity te) {
        //boolean found = level.getBlockStates(hurtArea).allMatch(blockState -> blockState.is(Blocks.WATER));
        if(level.isClientSide())
            te.particles(level, pos, te);
        else {
            int activeDistance = CrustedLavaBlock.getActiveDistance(level, pos);
            level.setBlock(pos, state.setValue(CrustedLavaBlock.DISTANCE, activeDistance), 1 | 2 | 4);
            AABB hurtArea = new AABB(pos).setMaxY(pos.getY()+activeDistance+1);
            //TODO: Add own damage type
            ((ServerLevel)level).getEntities(null, hurtArea).forEach(entity -> {
                if(entity instanceof LivingEntity)
                    entity.hurt(DamageSource.HOT_FLOOR, 1);
            });
        }




    }

    public void particles(Level level, BlockPos pos, CrustedLavaBlockEntity te) {
        RandomSource ran = level.getRandom();
        if(ran.nextBoolean()) {
            level.addAlwaysVisibleParticle(ModParticles.VENT_ASH.get(), true, (double)pos.getX() + 0.5D + ran.nextDouble() / 3.0D * (double)(ran.nextBoolean() ? 1 : -1), (double)pos.getY() + ran.nextDouble() + ran.nextDouble(), (double)pos.getZ() + 0.5D + ran.nextDouble() / 3.0D * (double)(ran.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        }
    }
}
