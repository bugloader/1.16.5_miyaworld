package com.meacks.miyaworld.entity;

import com.meacks.miyaworld.handlers.EntityHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SoundPlayingEntity extends Entity {
    private static final DataParameter<Float> frequencyData =
            EntityDataManager.defineId(SoundPlayingEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> instrumentIdData =
            EntityDataManager.defineId(SoundPlayingEntity.class, DataSerializers.INT);
    public static final SoundEvent[] instrumentList = {SoundEvents.NOTE_BLOCK_BASS,SoundEvents.NOTE_BLOCK_FLUTE};
    public SoundPlayingEntity(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
        this.entityData.set(frequencyData,0f);
    }

    public SoundPlayingEntity(World world , float frequency,int instumentId){
        this(EntityHandler.SOUND_ENTITY_REGISTRY_OBJECT.get(),world);
        this.entityData.set(frequencyData,frequency);
        this.entityData.set(instrumentIdData,instumentId);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(frequencyData,0f);
        this.entityData.define(instrumentIdData,0);

    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        this.entityData.set(frequencyData, nbt.getFloat("frequency"));
        this.entityData.set(instrumentIdData, nbt.getInt("instrumentId"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        nbt.putFloat("frequency",this.entityData.get(frequencyData));
        nbt.putFloat("instrumentId",this.entityData.get(instrumentIdData));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        float frequency = this.entityData.get(frequencyData);
        this.playSound(instrumentList[this.entityData.get(instrumentIdData)], 1,frequency);
        this.remove();
    }
}
