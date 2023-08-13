package net.ragnarok.update_nauticus.common.worldgen.feature.abyssal_vents;

import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.AABB;
import net.ragnarok.update_nauticus.init.ModBlocks;

import java.util.ArrayList;
import java.util.List;

public class GiantVentFeature extends Feature<NoneFeatureConfiguration> {
    public GiantVentFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }


    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        boolean check = false; //TODO maybe use configuration here, idk
        RandomSource rand = pContext.random();
        int max_height = rand.nextInt(5, 10);
        int steepness = rand.nextInt(4,6);

        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos chosen_pos = pContext.origin();
        RandomSource randomsource = pContext.random();
        int j = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR, chosen_pos.getX(), chosen_pos.getZ());
        BlockPos place_pos = new BlockPos(chosen_pos.getX(), j, chosen_pos.getZ());

        if(worldgenlevel.getBlockState(place_pos).is(Blocks.WATER)) {
            check = true;
            BlockPos top = new BlockPos(chosen_pos.getX(), j+max_height, chosen_pos.getZ());
            worldgenlevel.setBlock(top.below(), ModBlocks.CRUSTED_LAVA.get().defaultBlockState(),2); //Set Crusted Tip
            placeTillGround(top, top, steepness, worldgenlevel, rand); //Set Shell
            fill(place_pos, max_height, worldgenlevel, rand);//Set Filling
        }
        return check;
    }

    private void placeTillGround(BlockPos origin, BlockPos center, int steepness, WorldGenLevel worldgenlevel, RandomSource rand) {

        for (Direction dir:
             getOutgoingDirections(center, origin)) {
                BlockPos.MutableBlockPos current = ((WorldGenRegion)worldgenlevel).ensureCanWrite(origin.mutable().move(dir)) ?  origin.mutable().move(dir) : origin.mutable();
                    boolean skip = false;
                    if(rand.nextInt(3) >= 2) {
                        worldgenlevel.setBlock(current.above(), Blocks.BASALT.defaultBlockState(), 2);
                        if(rand.nextInt(3) == 1)
                            worldgenlevel.setBlock(current.above(2), Blocks.SMOOTH_BASALT.defaultBlockState(), 2);
                    }
                    for (int i = 0; i < steepness; i++) {
                        if(!(worldgenlevel.getBlockState(current).is(Blocks.WATER))) { //TODO: Check with tag
                            skip = true;
                            break;
                        }
                        worldgenlevel.setBlock(current, Blocks.BASALT.defaultBlockState(), 2);
                        current.move(Direction.DOWN);
                    }
                    if(skip)
                        continue;
                    placeTillGround(current, center, steepness, worldgenlevel, rand);
        }

    }

    private List<Direction> getOutgoingDirections(BlockPos origin, BlockPos current) {
        List<Direction> all = new ArrayList<>(Direction.Plane.HORIZONTAL.stream().toList());
        if(origin.equals(current)) return all;
        Vec3i dir = normalizeVec3i(new Vec3i(current.getX()-origin.getX(), 0, current.getZ()-origin.getZ()));
        List<Direction> dirs = new ArrayList<>();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if(( dir.getX() != 0 && direction.getNormal().getX() == dir.getX()) || ( dir.getZ() != 0 && direction.getNormal().getZ() == dir.getZ()))
                dirs.add(direction.getOpposite());
        }
        all.removeAll(dirs);
        return all;
    }

    private Vec3i normalizeVec3i(Vec3i vec) {
        int x = vec.getX();
        int y = vec.getY();
        int z = vec.getZ();
        if (x != 0)
            x = x/Math.abs(x);
        if (y != 0)
            y = y/Math.abs(y);
        if (z != 0)
            z = z/Math.abs(z);
        return new Vec3i(x, y , z);
    }

    private void fill(BlockPos origin, int height, WorldGenLevel worldgenlevel, RandomSource rand) {
        for (int i = 0; i < height-1; i++) {
            worldgenlevel.setBlock(origin.above(i), Blocks.LAVA.defaultBlockState(), 2);
            fillRecursivley(origin, origin.above(i), worldgenlevel, rand);
        }
    }

    private void fillRecursivley(BlockPos origin, BlockPos current, WorldGenLevel worldGenLevel, RandomSource rand) {
        for (Direction dir:
                getOutgoingDirections(origin, current)) {

            BlockPos.MutableBlockPos now = ((WorldGenRegion)worldGenLevel).ensureCanWrite(current.mutable().move(dir)) ?  current.mutable().move(dir) : current.mutable();

            if(!worldGenLevel.getBlockState(now).is(Blocks.WATER)) continue;
            worldGenLevel.setBlock(now, Blocks.LAVA.defaultBlockState(), 2); //TODO: Determin Fill material with tag
            fillRecursivley(origin, now, worldGenLevel, rand);
        }
    }
}
