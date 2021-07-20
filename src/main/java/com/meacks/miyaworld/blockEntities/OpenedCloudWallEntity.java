package com.meacks.miyaworld.blockEntities;

import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.TileEntityHandler;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OpenedCloudWallEntity extends TileEntity implements ITickableTileEntity {
    public int remainingTime;
    public static final int MAX_TIME = 200;

    public OpenedCloudWallEntity() {
        this(TileEntityHandler.OPENED_CLOUD_WALL_TILE_ENTITY_TYPE);
    }

    public OpenedCloudWallEntity(TileEntityType<?> tileEntityType){
        super(tileEntityType);
        remainingTime=MAX_TIME;
    }

    @Override
    public void tick() {
        remainingTime--;
        if(remainingTime==0){
            assert this.level != null;
            this.level.setBlockAndUpdate(this.worldPosition,BlockHandler.cloudWall.getBlock().defaultBlockState());
        }
    }
}
