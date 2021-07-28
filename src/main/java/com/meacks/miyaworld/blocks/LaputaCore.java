package com.meacks.miyaworld.blocks;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.LaputaCoreEntity;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;


public class LaputaCore extends Block {
    public static final String registryName = "laputa_core";
    public LaputaCore(){
        super(AbstractBlock.Properties.of(Material.GLASS).noOcclusion().isValidSpawn(BlockHandler::never)
                .isRedstoneConductor(BlockHandler::never).isSuffocating(BlockHandler::never)
                .isViewBlocking(BlockHandler::never).lightLevel((x) -> {
            return 15;
        }));
        this.setRegistryName(registryName);
        BlockHandler.BLOCKS.add(this);
        ItemHandler.createBlockItem(this,registryName,MiyaWorld.MAGIC_Block_GROUP);
    }



    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LaputaCoreEntity();
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if(!world.isClientSide && hand==Hand.MAIN_HAND &&
                player.getItemInHand(hand).equals(Items.AIR.getDefaultInstance(),false)){
            LaputaCoreEntity laputaCoreEntity = (LaputaCoreEntity) world.getBlockEntity(blockPos);
            assert laputaCoreEntity != null;
            if (Objects.requireNonNull(player).isShiftKeyDown()){
                laputaCoreEntity.decreaseRange();
                player.sendMessage(new TranslationTextComponent("Shield level decreased to 0."),player.getUUID());
            }else{
                    int result = laputaCoreEntity.increaseRange();
                    switch (result){
                        case 1:
                            player.sendMessage(new TranslationTextComponent(
                                    "Upgrade Succeed! New shield level: "+laputaCoreEntity.rangeLevel),player.getUUID());
                            player.playSound(SoundEvents.NOTE_BLOCK_BELL,1.0f,1.0f);
                            break;
                        case 0:
                            player.sendMessage(new TranslationTextComponent("Seems like your amount of pilot " +
                                    "stone is not right. You recalled your grandma taught you the fibonacci."),player.getUUID());
                            break;
                        case -1:
                            player.sendMessage(new TranslationTextComponent(
                                    "The shield's power is the strongest in the world!"),player.getUUID());
                            break;
                    }
                player.displayClientMessage(new TranslationTextComponent(
                        "shield level: " + laputaCoreEntity.rangeLevel), true);

            }
        }
        return ActionResultType.PASS;
    }

}
