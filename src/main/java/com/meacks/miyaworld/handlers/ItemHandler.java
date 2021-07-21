package com.meacks.miyaworld.handlers;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.entity.GiantRobot;
import com.meacks.miyaworld.entity.egg.ModdedSpawnEgg;
import com.meacks.miyaworld.items.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;
import java.util.List;

public class ItemHandler {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final Item testingItem = new TestingItem();
    public static final Item gildedPilotStone = new GildedPilotStone();
    public static final Item pilotStoneNecklace = new PilotStoneNecklace();
    public static final Item bassTube = new BassTube();
    public static final Item GiantRobotBall = new ModdedSpawnEgg(EntityType.DOLPHIN,
            EntityHandler.GIANT_ROBOT_REGISTRY_OBJECT,0xffffff,0xcccccc,
            new Item.Properties().tab(MiyaWorld.MAGIC_ITEM_GROUP),"giant_robot_ball");
    public static final Item pistol = new Pistol();
    public static final Item rocketLauncher = new RocketLauncher();

    public static void createBlockItem(Block block, String registryName, ItemGroup tab){
        ItemHandler.ITEMS.add(new BlockItem(block,new Item.Properties().tab(tab)).setRegistryName(registryName));
    }
}
