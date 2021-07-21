package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.entity.BulletEntity;
import com.meacks.miyaworld.entity.RocketEntity;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class RocketLauncher extends Item{

    public static final int COOLING_DOWN_TIME = 60;

    public RocketLauncher(){
        super(new Properties().tab(MiyaWorld.ARTIFACT_ITEM_GROUP));
        this.setRegistryName("rocket_launcher");
        ItemHandler.ITEMS.add(this);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int p_77663_4_, boolean p_77663_5_) {
        if (player instanceof PlayerEntity) {
            if (((PlayerEntity) player).getMainHandItem().getItem() == ItemHandler.rocketLauncher) {
                CompoundNBT nbt = stack.getTag();
                if (nbt == null) {
                    nbt = new CompoundNBT();
                }
                int coolTime = nbt.getInt("time");
                if (coolTime < COOLING_DOWN_TIME) {
                    nbt.putInt("time", coolTime + 1);
                    stack.setTag(nbt);
                }
            }
        }
    }


    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClientSide) {
            ItemStack stack = player.getMainHandItem();
            CompoundNBT nbt = stack.getTag();
            if (nbt == null) {
                nbt = new CompoundNBT();
            }
            int coolTime = nbt.getInt("time");
            if(coolTime>=COOLING_DOWN_TIME) {
                Vector3d focusLine = player.getLookAngle();
                RocketEntity rocketEntity = new RocketEntity(world, player, focusLine.x, focusLine.y, focusLine.z);
                rocketEntity.setOwner(player);
                rocketEntity.setPosRaw(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
                world.addFreshEntity(rocketEntity);
                nbt.putInt("time", 0);
                stack.setTag(nbt);
            }
        }
        return super.use(world,player,hand);
    }


}
