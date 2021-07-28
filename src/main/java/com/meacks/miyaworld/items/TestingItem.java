package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.LaputaCoreEntity;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class TestingItem extends Item{


    public TestingItem(){
        super(new Properties().tab(MiyaWorld.MAGIC_ITEM_GROUP).stacksTo(1));
        this.setRegistryName("testing_item");
        ItemHandler.ITEMS.add(this);
    }

    @Override
    public ActionResultType useOn(ItemUseContext itemUseContext){
        Hand usingHand = itemUseContext.getHand();
        PlayerEntity usingPlayer = itemUseContext.getPlayer();
        World level = itemUseContext.getLevel();
        assert usingPlayer != null;
        Vector3d position = usingPlayer.position();
        BlockPos blockPos = itemUseContext.getClickedPos();
        Block block = level.getBlockState(blockPos).getBlock();
        if(BlockHandler.areSameBlockType(block,BlockHandler.laputaCore)){
            LaputaCoreEntity laputaCoreEntity = (LaputaCoreEntity) level.getBlockEntity(blockPos);
            assert laputaCoreEntity != null;
            laputaCoreEntity.reachedLevel=LaputaCoreEntity.MAX_RANGE;
        }

        if(level.isClientSide) {
            level.addParticle(ParticleTypes.SOUL, position.x, position.y, position.z, 0, 0, 0);
            usingPlayer.displayClientMessage(new TranslationTextComponent(itemUseContext.getLevel().getBlockState(
                    itemUseContext.getClickedPos()).getBlock().getName().getString()), true);
            System.out.println(itemUseContext.getLevel().getBlockState(itemUseContext.getClickedPos()).getBlock().getName());
        }
        return ActionResultType.PASS;
    }
}
