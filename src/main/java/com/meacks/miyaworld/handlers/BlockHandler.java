package com.meacks.miyaworld.handlers;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BlockHandler {
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final Block laputaCore = new LaputaCore();
    public static final Block laputaController = new LaputaController();
    public static final Block cloudWall = createBlockWithItem(Material.STONE,"cloud_wall", MiyaWorld.MAGIC_Block_GROUP);
    public static final Block openedCloudWall = new OpenedCloudWall();
    public static final Block metalWall = createBlockWithItem(Material.STONE,"metal_wall", MiyaWorld.MAGIC_Block_GROUP);
    public static final Block solidMetalWall = createBlockWithItem(Material.STONE,"solid_metal_wall", MiyaWorld.MAGIC_Block_GROUP);
    public static final Block openedMetalWall = new OpenedMetalWall();
    public static final Block metalWallCore = createBlockWithItem(Material.STONE,"metal_wall_core", MiyaWorld.MAGIC_Block_GROUP);
    public static final Block openedMetalWallCore = new OpenedMetalWallCore();
    public static final Block LaputaRune = createBlockWithItem(Material.STONE,"laputa_rune", MiyaWorld.MAGIC_Block_GROUP);
    public static final Block pilotOre = createOreWithItem(Material.STONE,"pilot_ore", MiyaWorld.MAGIC_Block_GROUP);
    public static final Block weakPilotBlock = createBlockWithItem(Material.STONE,"weak_pilot_block", MiyaWorld.MAGIC_Block_GROUP);
    public static final Block pilotBlock = createBlockWithItem(Material.STONE,"pilot_block", MiyaWorld.MAGIC_Block_GROUP);
    public static final Block pilotCrystalBlock = createBlockWithItem(Material.STONE,"pilot_crystal_block", MiyaWorld.MAGIC_Block_GROUP);

    //public static final Block castleWall = createBlockWithItem(Material.STONE,"castle_wall", MiyaWorld.MAGIC_Block_GROUP);

    public static Block createBlockWithItem(Material properties, String registryName, ItemGroup tab){
        Block block = new Block(AbstractBlock.Properties.of(properties));
        block.setRegistryName(registryName);
        BlockHandler.BLOCKS.add(block);
        ItemHandler.ITEMS.add(new BlockItem(block,new Item.Properties().tab(tab)).setRegistryName(registryName));
        return block;
    }

    public static Block createOreWithItem(Material properties, String registryName, ItemGroup tab){
        Block block = new OreBlock(Block.Properties.of(properties));
        block.setRegistryName(registryName);
        BlockHandler.BLOCKS.add(block);
        ItemHandler.ITEMS.add(new BlockItem(block,new Item.Properties().tab(tab)).setRegistryName(registryName));
        return block;
    }

    public static boolean areSameBlockType(Block block1,Block block2){
        return block1==block2;
    }
}
