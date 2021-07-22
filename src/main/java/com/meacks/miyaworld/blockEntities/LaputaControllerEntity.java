package com.meacks.miyaworld.blockEntities;

import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.TileEntityHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class LaputaControllerEntity extends TileEntity implements ITickableTileEntity{
    public static final int COMMAND_NUM = 6;
    public static final String[] COMMAND_NAMES = {"None","Single Lightning","Multiple Lightning","Rain","Thunder","Sun"};
    public static final int[] COOL_DOWN_TIME = {20,5,20,20,20,20};
    public int coolTime = COOL_DOWN_TIME[0];
    public int time = 0;
    public int currentCommand = 0;
    public int rangeLevel;

    public LaputaControllerEntity() {
        this(TileEntityHandler.LAPUTA_CONTROLLER_TILE_ENTITY_TYPE);
    }

    public LaputaControllerEntity(TileEntityType<?> tileEntityType){
        super(tileEntityType);
        rangeLevel=0;
    }

    boolean keep=false;
    @Override
    public void tick() {
        time=Math.min(time+1,coolTime);
        if(keep&&time>=coolTime){
            executeCommand();
            time=0;
        }
    }

    public void changeCommand(){
        coolTime = COOL_DOWN_TIME[currentCommand];
        currentCommand=(currentCommand+1)%COMMAND_NUM;
    }

    public String getCommandName(){
        return COMMAND_NAMES[currentCommand];
    }

    public void changeKeep(){
        keep=!keep;
    }

    public boolean getKeep(){
        return keep;
    }

    public void executeCommand(){
        rangeLevel=findLaputaCoreLevel();
        if(time>=coolTime){
            switch (currentCommand){
                case 0:
                    break;
                case 1:
                    lightningClosestMob();
                    break;
                case 2:
                    lightningMultipleMobs();
                    break;
                case 3:
                    setRainWeather();
                    break;
                case 4:
                    setThunderWeather();
                    break;
                case 5:
                    clearWeather();
                    break;
            }
        }
    }

    public void lightningClosestMob(){
        int radius = rangeLevel*10;
        assert this.level != null;
        MonsterEntity monsterEntity = this.level.getNearestEntity(MonsterEntity.class, EntityPredicate.DEFAULT, null,
                this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), new AxisAlignedBB(
                        this.getBlockPos().getX() - radius, this.getBlockPos().getY() - radius,
                        this.getBlockPos().getZ() - radius, this.getBlockPos().getX() + radius,
                        this.getBlockPos().getY() + radius, this.getBlockPos().getZ() + radius));
        if(monsterEntity!=null){
            Vector3d position = monsterEntity.position();
            LightningBoltEntity lightningBoltEntity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,level);
            lightningBoltEntity.setPos(position.x,position.y,position.z);
            this.level.addFreshEntity(lightningBoltEntity);
        }
    }

    public void lightningMultipleMobs(){
        int radius = rangeLevel*10;
        assert this.level != null;
        List<LivingEntity> monsterEntitys = this.level.getNearbyEntities(
                MonsterEntity.class,EntityPredicate.DEFAULT,null,new AxisAlignedBB(
                        this.getBlockPos().getX() - radius, this.getBlockPos().getY() - radius,
                        this.getBlockPos().getZ() - radius, this.getBlockPos().getX() + radius,
                        this.getBlockPos().getY() + radius, this.getBlockPos().getZ() + radius));
        for (LivingEntity monsterEntity : monsterEntitys) {
            Vector3d position = monsterEntity.position();
            LightningBoltEntity lightningBoltEntity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, level);
            lightningBoltEntity.setPos(position.x, position.y, position.z);
            this.level.addFreshEntity(lightningBoltEntity);
        }
    }

    public void setRainWeather(){
        assert this.level != null;
        if(level.isClientSide) {
            this.level.setRainLevel(100);
        }
    }

    public void setThunderWeather(){
        assert this.level != null;
        if(level.isClientSide) {
            this.level.setThunderLevel(100);
        }
    }

    public void clearWeather() {
        assert this.level != null;
        if (level.isClientSide) {
            this.level.setRainLevel(0);
            this.level.setThunderLevel(0);
        }
    }
    private int findLaputaCoreLevel(){
        int result = 0;
        assert this.level != null;
        if(!this.level.isClientSide()){
            int index = -1;
            double smallestDistant = 70*70;
            double tempDistant;
            BlockPos blockPos = null;
            for (int i = 0; i < LaputaCoreEntity.laputaCoreList.size(); i++) {
                World tempWorld = LaputaCoreEntity.laputaCoreWorldList.get(i);
                BlockPos tempPos = LaputaCoreEntity.laputaCoreList.get(i);
                if (!BlockHandler.areSameBlockType(tempWorld.getBlockState(tempPos).getBlock(), BlockHandler.laputaCore)) {
                    LaputaCoreEntity.laputaCoreList.remove(i);
                    LaputaCoreEntity.laputaCoreWorldList.remove(i);
                    i--;
                }
                else if (this.level.equals(tempWorld)) {
                    tempDistant = this.getBlockPos().distSqr(tempPos);
                    if (tempDistant < smallestDistant) {
                        index = i;
                        smallestDistant = tempDistant;
                        blockPos = tempPos;
                    }
                }
            }
            if(index!=-1){
                result = ((LaputaCoreEntity) Objects.requireNonNull(this.level.getBlockEntity(blockPos))).reachedLevel;
            }
        }
        return result;
    }

    @Override
    public void load(BlockState state, CompoundNBT compoundNBT) {
        this.rangeLevel = compoundNBT.getInt("RangeLevel");
        this.time = compoundNBT.getInt("TimePower");
        this.currentCommand = compoundNBT.getInt("CommandId");
        this.keep = compoundNBT.getBoolean("Keep");
        super.load(state, compoundNBT);
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        CompoundNBT superNBT = super.save(compoundNBT);
        superNBT.putInt("TimePower", this.time);
        superNBT.putInt("RangeLevel", this.rangeLevel);
        superNBT.putInt("CommandId", this.currentCommand);
        superNBT.putBoolean("Keep", this.keep);
        return superNBT;
    }
}
