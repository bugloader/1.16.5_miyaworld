package com.meacks.miyaworld.blockEntities;

import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.TileEntityHandler;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OpenedMetalWallEntity extends TileEntity implements ITickableTileEntity {
    public int remainingTime;
    public static final int MAX_TIME = 200;

    public OpenedMetalWallEntity() {
        this(TileEntityHandler.OPENED_METAL_WALL_TILE_ENTITY_TYPE);
    }

    public OpenedMetalWallEntity(TileEntityType<?> tileEntityType){
        super(tileEntityType);
        remainingTime=MAX_TIME;
    }

    @Override
    public void tick() {
        remainingTime--;
        if(remainingTime==0){
            assert this.level != null;
            this.level.setBlockAndUpdate(this.worldPosition,BlockHandler.metalWall.getBlock().defaultBlockState());
        }
    }
}
