package com.meacks.miyaworld.blocks;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.OpenedCloudWallEntity;
import com.meacks.miyaworld.blockEntities.OpenedMetalWallCoreEntity;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class OpenedMetalWallCore extends Block {
    public static final String registryName = "opened_metal_wall_core";
    public OpenedMetalWallCore(){
        super(Properties.of(Material.AIR).noCollission());
        this.setRegistryName(registryName);
        BlockHandler.BLOCKS.add(this);
        ItemHandler.createBlockItem(this,registryName, MiyaWorld.MAGIC_Block_GROUP);
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new OpenedMetalWallCoreEntity();
    }

}