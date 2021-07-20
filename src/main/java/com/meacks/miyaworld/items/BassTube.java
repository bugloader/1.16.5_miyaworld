package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.LaputaCoreEntity;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BassTube extends Item{


    public BassTube(){
        super(new Properties().tab(MiyaWorld.ARTIFACT_ITEM_GROUP));
        this.setRegistryName("bass_tube");
        ItemHandler.ITEMS.add(this);
    }
    int t=0;
    private static final float[] frequency = {0.41f,0.43f,0.49f,0.55f,0.62f,0.65f,0.73f,0.82f
            ,0.87f,0.98f,1.1f,1.23f,1.31f,1.47f,1.65f,0.693f};//e
    public static final int[] music = {2,5,2,5,6,2,7,9,9,9,8,8,8,7,6,5,7,5,2,5,2,5,6,2,7,9,9,9,8,8,8,7,6,5,7,5,
            5,10,10,10,9,8,9,5,5,5,5,9,9,9,8,6,7,7,7,7,8,7,6,5,7,3,6,6,6,15,6,9};
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        if(world.isClientSide) {
            Vector3d position = playerEntity.position();
            playerEntity.playSound(SoundEvents.NOTE_BLOCK_FLUTE, 1, frequency[music[t]]);
            if(playerEntity.isShiftKeyDown()){
                if(t>0){
                    t--;
                }
            }else if(t<music.length-1){
                t++;
            }else {
                t=0;
            }

            world.addParticle(ParticleTypes.NOTE, position.x, position.y, position.z, 0, 0, 0);

        }
        return super.use(world,playerEntity,hand);
    }


}
