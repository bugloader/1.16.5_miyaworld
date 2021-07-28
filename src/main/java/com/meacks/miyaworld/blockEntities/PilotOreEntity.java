package com.meacks.miyaworld.blockEntities;

import com.meacks.miyaworld.blocks.PilotOre;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import com.meacks.miyaworld.handlers.TileEntityHandler;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.Objects;

public class PilotOreEntity extends TileEntity implements ITickableTileEntity {

    public PilotOreEntity() {
        this(TileEntityHandler.PILOT_ORE_TILE_ENTITY_TYPE);
    }

    public PilotOreEntity(TileEntityType<?> tileEntityType){
        super(tileEntityType);
    }

    private void increaseEnergy(){
        int n=this.getBlockState().getValue(PilotOre.STATE);
        if(n<18) {
            assert level != null;
            level.setBlock(worldPosition,this.getBlockState().setValue(PilotOre.STATE,n+1),3);
        }
    }

    private void decreaseEnergy(){
        int n=this.getBlockState().getValue(PilotOre.STATE);
        if(n>0) {
            assert level != null;
            level.setBlock(worldPosition,this.getBlockState().setValue(PilotOre.STATE,n-1),3);
        }
    }

    int i=0;
    @Override
    public void tick() {
        if(i==3) {
            assert level != null;
            PlayerEntity player = level.getNearestPlayer(getBlockPos().getX(), getBlockPos().getY(),
                    getBlockPos().getZ(), 30, null);
            if(player!=null) {
                List<PlayerEntity> playerEntityList = level.getNearbyPlayers(EntityPredicate.DEFAULT, player,
                        new AxisAlignedBB(getBlockPos().getX() - 30, getBlockPos().getY() - 30,
                                getBlockPos().getZ() - 30, getBlockPos().getX() + 30,
                                getBlockPos().getY() + 30, getBlockPos().getZ() + 30));
                playerEntityList.add(player);
                boolean hasPlayer = false;
                for (PlayerEntity playerEntity : playerEntityList) {
                    if (playerEntity.distanceToSqr(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()) < 900 &&
                            (playerEntity.inventory.contains(ItemHandler.pilotStoneNecklace.getDefaultInstance()) ||
                            playerEntity.inventory.contains(ItemHandler.gildedPilotStone.getDefaultInstance()))) {
                        hasPlayer = true;
                        break;
                    }
                    playerEntity.addEffect(new EffectInstance(Effects.SLOW_FALLING, 40));
                }
                if (hasPlayer) {
                    increaseEnergy();
                } else {
                    decreaseEnergy();
                }
            }
            i=0;
        }else{
            i++;
        }
    }
}
