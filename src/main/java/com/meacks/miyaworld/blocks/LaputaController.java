package com.meacks.miyaworld.blocks;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.LaputaControllerEntity;
import com.meacks.miyaworld.blockEntities.OpenedCloudWallEntity;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LaputaController extends Block {
    public static final String registryName = "laputa_controller";
    public LaputaController(){
        super(Properties.of(Material.GLASS).noCollission());
        this.setRegistryName(registryName);
        BlockHandler.BLOCKS.add(this);
        ItemHandler.createBlockItem(this,registryName, MiyaWorld.MAGIC_Block_GROUP);
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if(!world.isClientSide && hand==Hand.MAIN_HAND &&
                playerEntity.getItemInHand(hand).equals(Items.AIR.getDefaultInstance(),false)){
            LaputaControllerEntity laputaControllerEntity = (LaputaControllerEntity) world.getBlockEntity(blockPos);
            assert laputaControllerEntity != null;
            if(playerEntity.isShiftKeyDown()){
                    laputaControllerEntity.changeCommand();
                    playerEntity.displayClientMessage(new TranslationTextComponent("current command: " +
                            laputaControllerEntity.getCommandName()), true);
            }else{
                playerEntity.displayClientMessage(new TranslationTextComponent("current level: " +
                        laputaControllerEntity.rangeLevel), true);

            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LaputaControllerEntity();
    }

}