package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.LaputaControllerEntity;
import com.meacks.miyaworld.blockEntities.LaputaCoreEntity;
import com.meacks.miyaworld.blockEntities.PilotOreEntity;
import com.meacks.miyaworld.blocks.PilotOre;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GildedPilotStone extends Item {
    public GildedPilotStone(){
        super(new Properties().tab(MiyaWorld.MAGIC_ITEM_GROUP).stacksTo(1));
        this.setRegistryName("gilded_pilot_stone");
        ItemHandler.ITEMS.add(this);
    }

    public static void checkOre(PlayerEntity entity,BlockPos playerBlockPos, World level){
            if (level.isClientSide) {
                Random random = new Random();
                if(random.nextFloat()<0.05) {
                    entity.level.addParticle(ParticleTypes.TOTEM_OF_UNDYING,
                            entity.getX() + random.nextFloat() - 0.5,
                            entity.getY() + random.nextFloat(),
                            entity.getZ() + random.nextFloat() - 0.5,
                            0, 0, 0);
                }
            } else {
                for (int i = -9; i < 9; i++) {
                    for (int j = -9; j < 9; j++) {
                        for (int k = -9; k < 9; k++) {
                            BlockPos tempPos = new BlockPos(playerBlockPos.getX() + i,
                                    playerBlockPos.getY() + j, playerBlockPos.getZ() + k);
                            Block tempBlock = entity.level.getBlockState(tempPos).getBlock();
                            if (tempBlock instanceof PilotOre) {
                                int n = entity.level.getBlockState(tempPos).getValue(PilotOre.STATE);
                                if (entity.position().distanceToSqr(tempPos.getX(), tempPos.getY(), tempPos.getZ()) < 36) {
                                    if (n < 18) {
                                        assert entity.level != null;
                                        entity.level.setBlock(tempPos, entity.level.getBlockState(tempPos)
                                                .setValue(PilotOre.STATE, n + 1), 3);
                                    }
                                } else if (n > 0) {
                                    assert entity.level != null;
                                    entity.level.setBlock(tempPos, entity.level.getBlockState(tempPos)
                                            .setValue(PilotOre.STATE, n - 1), 3);
                                }
                            }
                        }
                    }
                }
            }
    }

    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if(p_77663_3_ instanceof PlayerEntity) {
            checkOre((PlayerEntity) p_77663_3_, p_77663_3_.blockPosition(), p_77663_2_);
        }
    }

    public static void configLaputaCore(World world,BlockPos blockPos,PlayerEntity player){
        LaputaCoreEntity laputaCoreEntity = (LaputaCoreEntity) world.getBlockEntity(blockPos);
        assert laputaCoreEntity != null;
        if(!world.isClientSide){
            if (Objects.requireNonNull(player).isShiftKeyDown()){
                laputaCoreEntity.barutsu();
                player.sendMessage(new TranslationTextComponent("Do you know what バルス " +
                        "means?"), player.getUUID());
                player.displayClientMessage(new TranslationTextComponent(
                        "バルス".charAt(laputaCoreEntity.n-1)+""),true);
            }else{
            laputaCoreEntity.refill=!laputaCoreEntity.refill;
                player.displayClientMessage(new TranslationTextComponent(
                        "shield refill : "+laputaCoreEntity.refill),true);
            }
        }
    }

    public static void openCloudWall(World world,BlockPos blockPos,PlayerEntity player){
        int x=blockPos.getX();
        int y=blockPos.getY();
        int z=blockPos.getZ();
        for (int i = x-2; i <= x+2; i++) {
            for (int j = y-2; j <= y+2; j++) {
                for (int k = z-2; k <= z+2; k++) {
                    BlockPos tempPos = new BlockPos(i,j,k);
                    if(BlockHandler.areSameBlockType(world.getBlockState(tempPos).getBlock(),BlockHandler.cloudWall)){
                        world.setBlockAndUpdate(tempPos, BlockHandler.openedCloudWall.defaultBlockState());
                        player.displayClientMessage(new TranslationTextComponent("Welcome"),true);
                    }
                }
            }
        }
    }

    public static void openMetalWallCore(World world,BlockPos blockPos,PlayerEntity player){
        world.setBlockAndUpdate(blockPos, BlockHandler.openedMetalWallCore.defaultBlockState());
        player.displayClientMessage(new TranslationTextComponent("Welcome"),true);
    }

    public static ActionResult<ItemStack> findLaputa(World world, PlayerEntity playerEntity, ItemStack stack){
        Vector3d playerPos = playerEntity.position();
        if(!world.isClientSide()){
            int index = -1;
            double smallestDistant = 1e8;
            double tempDistant;
            for (int i = 0; i < LaputaCoreEntity.laputaCoreList.size(); i++) {
                World tempWorld = LaputaCoreEntity.laputaCoreWorldList.get(i);
                BlockPos tempPos = LaputaCoreEntity.laputaCoreList.get(i);
                if (!BlockHandler.areSameBlockType(tempWorld.getBlockState(tempPos).getBlock(), BlockHandler.laputaCore)) {
                    LaputaCoreEntity.laputaCoreList.remove(i);
                    LaputaCoreEntity.laputaCoreWorldList.remove(i);
                    i--;
                }else if (world.equals(tempWorld)) {
                    tempDistant = playerPos.distanceToSqr(tempPos.getX(), tempPos.getY(), tempPos.getZ());
                    if (tempDistant < smallestDistant) {
                        index = i;
                        smallestDistant = tempDistant;
                    }
                }
            }
            CompoundNBT nbt = stack.getTag();
            if(nbt==null){
                nbt = new CompoundNBT();
            }
            if (index == -1) {
                playerEntity.displayClientMessage(new TranslationTextComponent(
                        "The stone seems to have no reaction"), true);
                nbt.putInt("x",(int)playerPos.x);
                nbt.putInt("y",(int)playerPos.y);
                nbt.putInt("z",(int)playerPos.z);
            } else {
                BlockPos laputaPos = LaputaCoreEntity.laputaCoreList.get(index);
                playerEntity.displayClientMessage(new TranslationTextComponent(
                        laputaPos.getX()+", "+laputaPos.getY()+", "+laputaPos.getZ()), true);
                nbt.putInt("x",laputaPos.getX());
                nbt.putInt("y",laputaPos.getY());
                nbt.putInt("z",laputaPos.getZ());
            }
            stack.setTag(nbt);
        }else{
            CompoundNBT nbt = stack.getTag();
            if(nbt==null){
                nbt = new CompoundNBT();
            }
            int times = 100;
            double dx = (nbt.getInt("x") - playerPos.x) / times;
            double dy = (nbt.getInt("y")  - playerPos.y) / times;
            double dz = (nbt.getInt("z")  - playerPos.z) / times;
            for (int i = 0; i < times; i++) {
                world.addParticle(ParticleTypes.TOTEM_OF_UNDYING,
                        playerPos.x + i * dx, playerPos.y + i * dy, playerPos.z + i * dz, 0, 0, 0);
            }
        }
        return ActionResult.sidedSuccess(stack, true);
    }

    public static void configLaputaController(World world,BlockPos blockPos,PlayerEntity playerEntity){
        LaputaControllerEntity laputaControllerEntity = (LaputaControllerEntity) world.getBlockEntity(blockPos);
        assert laputaControllerEntity != null;
            if (playerEntity.isShiftKeyDown()) {
                laputaControllerEntity.changeKeep();
                playerEntity.displayClientMessage(new TranslationTextComponent("automatic: " +
                        laputaControllerEntity.getKeep()), true);
            } else {
                laputaControllerEntity.executeCommand();
                playerEntity.displayClientMessage(new TranslationTextComponent("executing: " +
                        laputaControllerEntity.getCommandName()), true);
            }
    }

    public static void useOnMethod(ItemUseContext itemUseContext){
        World world = itemUseContext.getLevel();
        BlockPos blockPos = itemUseContext.getClickedPos();
        PlayerEntity player = itemUseContext.getPlayer();
        Block block = world.getBlockState(blockPos).getBlock();
        assert player != null;
        if(BlockHandler.areSameBlockType(block,BlockHandler.laputaCore)){
            configLaputaCore(world,blockPos,player);
        }else if (BlockHandler.areSameBlockType(block,BlockHandler.cloudWall)){
            openCloudWall(world,blockPos,player);
        }else if(BlockHandler.areSameBlockType(block,BlockHandler.metalWallCore)){
            openMetalWallCore(world,blockPos,player);
        }else if(BlockHandler.areSameBlockType(block,BlockHandler.laputaController)){
            configLaputaController(world,blockPos,player);
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext itemUseContext) {
        useOnMethod(itemUseContext);
        return ActionResultType.PASS;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if(!playerEntity.getMainHandItem().getItem().equals(ItemHandler.gildedPilotStone)){
            return findLaputa(world,playerEntity,playerEntity.getItemInHand(hand));
        }
        return super.use(world,playerEntity,hand);
    }
}
