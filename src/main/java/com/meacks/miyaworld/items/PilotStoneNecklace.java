package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.LaputaCoreEntity;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;


public class PilotStoneNecklace extends ArmorItem {
    public PilotStoneNecklace(){
        super(ArmorMaterial.DIAMOND, EquipmentSlotType.HEAD, new Properties().tab(MiyaWorld.MAGIC_ITEM_GROUP));
        this.setRegistryName("pilot_stone_necklace");
        ItemHandler.ITEMS.add(this);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        CompoundNBT nbt = stack.getTag();
        assert nbt != null;
        int t0 = nbt.getInt("t0");
        int t1 = nbt.getInt("t1");
        int t2 = nbt.getInt("t2");
        boolean shine = nbt.getBoolean("shine");

        if(world.isClientSide){
            if(shine){
                if(t0==80){
                    nbt.putInt("t0",0);
                    world.addParticle(ParticleTypes.FLASH,
                            player.getX(), player.getY()+0.2, player.getZ(), 0, 0, 0);
                }
            }
        }else {
            if(shine){
                nbt.putInt("t0", ++t0);
                world.addParticle(ParticleTypes.TOTEM_OF_UNDYING,
                        player.getX()+random.nextFloat()-0.5, player.getY()+random.nextFloat(),
                        player.getZ()+random.nextFloat()-0.5, 0, 0, 0);
            }
            if (!player.isOnGround()) {
                nbt.putInt("t2", 0);
                if (t1 < 30) {
                    nbt.putInt("t1", t1 + 1);
                } else {
                    nbt.putBoolean("shine", true);
                    nbt.putInt("t1", 0);
                    List<PlayerEntity> playerEntityList = world.getNearbyPlayers(EntityPredicate.DEFAULT, player,
                            new AxisAlignedBB(player.getX() - 3, player.getY() - 3, player.getZ() - 3,
                                    player.getX() + 3, player.getY() + 3, player.getZ() + 3));
                    for (PlayerEntity playerEntity : playerEntityList) {
                        playerEntity.addEffect(new EffectInstance(Effects.SLOW_FALLING, 40));
                    }
                    player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 40));
                }
            } else {
                nbt.putBoolean("shine", false);
                nbt.putInt("t0", 0);
                nbt.putInt("t1", 0);
                if (t2 < 10) {
                    nbt.putInt("t2", t2 + 1);
                } else {
                    nbt.putInt("t2", 0);
                    player.removeEffect(Effects.SLOW_FALLING);
                }
            }
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext itemUseContext) {
        GildedPilotStone.useOnMethod(itemUseContext);
        return ActionResultType.PASS;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if(!playerEntity.getMainHandItem().getItem().equals(ItemHandler.pilotStoneNecklace)){
            return GildedPilotStone.findLaputa(world,playerEntity,playerEntity.getItemInHand(hand));
        }
        return super.use(world,playerEntity,hand);
    }
}
