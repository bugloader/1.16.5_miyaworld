package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.LaputaCoreEntity;
import com.meacks.miyaworld.entity.SoundPlayingEntity;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class BassTube extends Item{


    public BassTube(){
        super(new Properties().tab(MiyaWorld.ARTIFACT_ITEM_GROUP).stacksTo(1));
        this.setRegistryName("bass_tube");
        ItemHandler.ITEMS.add(this);
    }
    private static final float[] frequency = {0.41f,0.43f,0.49f,0.55f,0.62f,0.65f,0.73f,0.82f
            ,0.87f,0.98f,1.1f,1.23f,1.31f,1.47f,1.65f,0.693f};//e
    public static final int[] music = {2,5,2,5,6,2,7,9,9,9,8,8,8,7,6,5,7,5,2,5,2,5,6,2,7,9,9,9,8,8,8,7,6,5,7,5,
            5,10,10,10,9,8,9,5,5,5,5,9,9,9,8,6,7,7,7,7,8,7,6,5,7,3,6,6,6,15,6,9};
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClientSide){
            CompoundNBT nbt = player.getItemInHand(hand).getTag();
            if(nbt==null){
                nbt = new CompoundNBT();
            }
            int t = nbt.getInt("note");
            SoundPlayingEntity soundEntity = new SoundPlayingEntity(world,frequency[music[t]],0);
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
