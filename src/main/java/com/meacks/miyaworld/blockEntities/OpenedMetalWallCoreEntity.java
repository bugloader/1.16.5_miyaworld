package com.meacks.miyaworld.blockEntities;

import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.TileEntityHandler;
import net.minecraft.block.Block;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class OpenedMetalWallCoreEntity extends TileEntity implements ITickableTileEntity {
    public int remainingTime;
    public static final int MAX_TIME = 200;

    public OpenedMetalWallCoreEntity() {
        this(TileEntityHandler.OPENED_METAL_WALL_CORE_TILE_ENTITY_TYPE);
    }

    public OpenedMetalWallCoreEntity(TileEntityType<?> tileEntityType){
        super(tileEntityType);
        remainingTime=MAX_TIME;
    }

    private void checkNearby(List<BlockPos> checking, BlockPos blockPos){
        assert this.level != null;
        BlockPos[] blockPosArray =
                {blockPos.above(),blockPos.below(),blockPos.east(),blockPos.west(),blockPos.north(),blockPos.south()};
        for (int i = 0; i < 6; i++) {
            if(BlockHandler.areSameBlockType(this.level.getBlockState(blockPosArray[i]).getBlock(),BlockHandler.metalWall)) {
                checking.add(blockPosArray[i]);
                this.level.setBlockAndUpdate(blockPosArray[i],BlockHandler.openedMetalWall.defaultBlockState());
            }
        }
    }

    public void openMetalWalls(){
        opened=true;
        List<BlockPos> checking = new ArrayList<>();
        checking.add(this.getBlockPos());
        for (int i = 0; i < checking.size()&&i<64; i++) {
            checkNearby(checking,checking.get(i));
        }
    }

    boolean opened = false;
    @Override
    public void tick() {
        if(!opened){
            openMetalWalls();
        }
        remainingTime--;
        if(remainingTime==0){
            assert this.level != null;
            this.level.setBlockAndUpdate(this.worldPosition,BlockHandler.metalWallCore.getBlock().defaultBlockState());
        }
    }
}
