package net.ragnarok.update_nauticus.common.block;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ThermalVentBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape BASE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final VoxelShape MIDDLE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape TIP = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 15.0D, 13.0D);

    private static final DustColorTransitionOptions PARTICLE_OPTIONS = new DustColorTransitionOptions(new Vector3f(0.5f, 0.3f,0.0f ), new Vector3f(0.0f, 0.0f,0.0f ), 2.8f);
    public ThermalVentBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(THICKNESS, DripstoneThickness.TIP).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(THICKNESS, WATERLOGGED);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelReader world = pContext.getLevel();
        BlockState block = world.getBlockState(pContext.getClickedPos());
        return this.defaultBlockState().setValue(THICKNESS, calculateVentThickness(world, pContext.getClickedPos())).setValue(WATERLOGGED, block.is(Blocks.WATER));
    }
    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(THICKNESS)) {

            case TIP_MERGE, FRUSTUM, BASE -> BASE;

            case TIP -> TIP;
            case MIDDLE -> MIDDLE;
        };
    }
    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        if (pDirection != Direction.UP && pDirection != Direction.DOWN) {
            return pState;
        } else {
            return pState.setValue(THICKNESS, calculateVentThickness(pLevel, pCurrentPos));
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);

        if(pState.getValue(ThermalVentBlock.THICKNESS) != DripstoneThickness.TIP) return;

        //TODO: Make particles actually good
        //pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX()+0.5+pRandom.nextDouble()*0.5, pos.getY()+0.6+pRandom.nextDouble()*0.5, pos.getZ()+0.5+pRandom.nextDouble()*0.5, 0.0d, 0.0d, 0.0d);
        pLevel.addParticle(PARTICLE_OPTIONS, pPos.getX()+0.5, pPos.getY()+1.1, pPos.getZ()+0.5, 0.0d, 100d, 0.0d);
    }
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isValidThermalVentPlacement(pLevel, pPos);
    }
    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!this.canSurvive(pState, pLevel, pPos)) { //Todo: Figure out how to make this destroy the block when not used
            pLevel.destroyBlock(pPos, true);
        }
    }

    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.DESTROY;
    }

    public static DripstoneThickness calculateVentThickness(LevelReader pLevel, BlockPos pPos) {
        Direction direction = Direction.UP;
        BlockState blockstate = pLevel.getBlockState(pPos.relative(direction));
        if(blockstate.getBlock() instanceof ThermalVentBlock) {
            return switch (blockstate.getValue(THICKNESS)) {

                case TIP_MERGE, FRUSTUM -> DripstoneThickness.TIP;
                case TIP -> DripstoneThickness.MIDDLE;
                case MIDDLE, BASE -> DripstoneThickness.BASE;
            };
        }
        return DripstoneThickness.TIP;
    }

    private static boolean isValidThermalVentPlacement(LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(Direction.UP.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) || blockstate.getBlock() instanceof ThermalVentBlock;
    }


}
