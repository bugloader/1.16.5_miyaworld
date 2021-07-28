package com.meacks.miyaworld.handlers;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.*;
import com.meacks.miyaworld.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TileEntityHandler {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MiyaWorld.MODID);

    public static final TileEntityType<LaputaCoreEntity> LAPUTA_CORE_TILE_ENTITY_TYPE =
            sampleRegistry(LaputaCore.registryName,LaputaCoreEntity::new,BlockHandler.laputaCore);

    public static final TileEntityType<PilotOreEntity> PILOT_ORE_TILE_ENTITY_TYPE =
            sampleRegistry(PilotOre.registryName,PilotOreEntity::new,BlockHandler.pilotOre);

    public static final TileEntityType<LaputaControllerEntity> LAPUTA_CONTROLLER_TILE_ENTITY_TYPE =
            sampleRegistry(LaputaController.registryName, LaputaControllerEntity::new,BlockHandler.laputaController);

    public static final TileEntityType<OpenedCloudWallEntity> OPENED_CLOUD_WALL_TILE_ENTITY_TYPE =
            sampleRegistry(OpenedCloudWall.registryName,OpenedCloudWallEntity::new,BlockHandler.openedCloudWall);

    public static final TileEntityType<OpenedMetalWallEntity> OPENED_METAL_WALL_TILE_ENTITY_TYPE =
            sampleRegistry(OpenedMetalWall.registryName, OpenedMetalWallEntity::new,BlockHandler.openedMetalWall);

    public static final TileEntityType<OpenedMetalWallCoreEntity> OPENED_METAL_WALL_CORE_TILE_ENTITY_TYPE =
            sampleRegistry(OpenedMetalWallCore.registryName, OpenedMetalWallCoreEntity::new,BlockHandler.openedMetalWallCore);

    public static <T extends TileEntity>TileEntityType<T> sampleRegistry(
            String blockName, Supplier<T> supplier, Block block){
        TileEntityType<T> tileEntityType = TileEntityType.Builder.of(supplier,block).build(null);
        TILE_ENTITY_TYPE_DEFERRED_REGISTER.register(blockName+"_tileentity", () -> tileEntityType);
        return tileEntityType;
    }

}
