package com.meacks.miyaworld.blocks;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.OpenedCloudWallEntity;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CloudWall extends AbstractGlassBlock {
    public static final String registryName = "cloud_wall";
    public CloudWall(){
        super(AbstractBlock.Properties.of(Material.GLASS).noOcclusion().isValidSpawn(BlockHandler::never)
                .isRedstoneConductor(BlockHandler::never).isSuffocating(BlockHandler::never)
                .isViewBlocking(BlockHandler::never));

        this.setRegistryName(registryName);
        BlockHandler.BLOCKS.add(this);
        ItemHandler.createBlockItem(this,registryName, MiyaWorld.MAGIC_Block_GROUP);
    }
    
}