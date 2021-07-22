package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.entity.SoundPlayingEntity;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Flute943 extends Item{


    public Flute943(){
        super(new Properties().tab(MiyaWorld.ARTIFACT_ITEM_GROUP));
        this.setRegistryName("flute943");
        ItemHandler.ITEMS.add(this);
    }
    private static final float[] frequency = {0.41f,0.43f,0.49f,0.55f,0.62f,0.65f,0.73f,0.82f
            ,0.87f,0.98f,1.1f,1.23f,1.31f,1.47f,1.65f,1.55f};//e
    public static final int[] music = {7,9,7,9,10,9,7,9,7,6,5,3,5,3,2,3,4,5,4,5,6,7,6,7,6,5,4,3,2,
            7,9,7,9,10,9,7,9,7,6,5,3,5,3,2,3,4,5,7,6,5,4,5,
            7,9,7,6,7,9,10,10,9,7,6,7,9,7,5,4,5,9,10,9,7,6,7,6,5,4,3,2,
            7,9,7,6,7,9,10,10,9,10,11,12,12,10,9,15,13,12};
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClientSide){
            CompoundNBT nbt = player.getItemInHand(hand).getTag();
            if(nbt==null){
                nbt = new CompoundNBT();
            }
            int t = nbt.getInt("note");
            SoundPlayingEntity soundEntity = new SoundPlayingEntity(world,frequency[music[t]],1);
            soundEntity.setPosRaw(player.getX(),player.getY(),player.getZ());
            world.addFreshEntity(soundEntity);
            if(player.isShiftKeyDown()){
                if(t>0){
                    t--;
                }
            }else if(t<music.length-1){
                t++;
            }else {
                t=0;
            }
            nbt.putInt("note",t);
            player.getItemInHand(hand).setTag(nbt);
        }
        return super.use(world,player,hand);
    }


}
