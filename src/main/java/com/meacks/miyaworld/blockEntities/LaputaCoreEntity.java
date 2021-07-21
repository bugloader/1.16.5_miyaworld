package com.meacks.miyaworld.blockEntities;

import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.TileEntityHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LaputaCoreEntity extends TileEntity implements ITickableTileEntity {

    public int rangeLevel;
    public int reachedLevel;
    private boolean changed;
    public boolean refill;
    public static final int MAX_RANGE=13;
    public static final int[] LEVEL_AMOUNT = {0,1,1,2,3,5,8,13,21,34,55,89,144,233};

    public static final List<BlockPos> laputaCoreList = new ArrayList<>();
    public static final List<World> laputaCoreWorldList = new ArrayList<>();
    public LaputaCoreEntity() {
        this(TileEntityHandler.LAPUTA_CORE_TILE_ENTITY_TYPE);
    }

    public LaputaCoreEntity(TileEntityType<?> tileEntityType){
        super(tileEntityType);
        rangeLevel=0;
        reachedLevel=0;
        changed=true;
        refill=false;
    }

    @Override
    public void load(BlockState state, CompoundNBT compoundNBT) {
        this.reachedLevel = compoundNBT.getInt("ReachedLevel");
        this.rangeLevel = compoundNBT.getInt("RangeLevel");
        this.changed = compoundNBT.getBoolean("Changed");
        this.refill = compoundNBT.getBoolean("Refill");
        super.load(state, compoundNBT);
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        CompoundNBT superNBT = super.save(compoundNBT);
        superNBT.putInt("ReachedLevel", this.reachedLevel);
        superNBT.putInt("RangeLevel", this.rangeLevel);
        superNBT.putBoolean("Changed", this.changed);
        superNBT.putBoolean("Refill", this.refill);
        return superNBT;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert this.level != null;
        handleUpdateTag(this.level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundNBT = super.getUpdateTag();
        compoundNBT.putInt("ReachedLevel", this.reachedLevel);
        compoundNBT.putInt("RangeLevel", this.rangeLevel);
        compoundNBT.putBoolean("Changed", this.changed);
        compoundNBT.putBoolean("Refill", this.refill);
        return compoundNBT;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.reachedLevel = tag.getInt("ReachedLevel");
        this.rangeLevel = tag.getInt("RangeLevel");
        this.changed = tag.getBoolean("Changed");
        this.refill = tag.getBoolean("Refill");
    }

    private int checkNearby(List<BlockPos> checking,BlockPos blockPos){
        assert this.level != null;
        int result=0;
        BlockPos[] blockPosArray =
                {blockPos.above(),blockPos.below(),blockPos.east(),blockPos.west(),blockPos.north(),blockPos.south()};
        for (int i = 0; i < 6; i++) {
            if(!checking.contains(blockPosArray[i])){
                Block tempBlock = this.level.getBlockState(blockPosArray[i]).getBlock();
                if(BlockHandler.areSameBlockType(tempBlock,BlockHandler.weakPilotBlock)){
                    result++;
                    checking.add(blockPosArray[i]);
                }else if(BlockHandler.areSameBlockType(tempBlock,BlockHandler.pilotBlock)){
                    result+=3;
                    checking.add(blockPosArray[i]);
                }else if(BlockHandler.areSameBlockType(tempBlock,BlockHandler.pilotCrystalBlock)){
                    result+=9;
                    checking.add(blockPosArray[i]);
                }
            }
        }
        return result;
    }

    private int countPilotPower(){
        int result=0;
        List<BlockPos> checking = new ArrayList<>();
        checking.add(this.getBlockPos());
        for (int i = 0; i < checking.size()&&i<233; i++) {
            result+= checkNearby(checking,checking.get(i));
        }
        return result;
    }

    public static final int SUCCESS = 1;
    public static final int FAILED = 0;
    public static final int ERROR = -1;


    public int increaseRange(){
        if(rangeLevel<MAX_RANGE) {
            if(rangeLevel<reachedLevel){
                rangeLevel++;
                changed=true;
                return SUCCESS;
            }
            else if(countPilotPower()==LEVEL_AMOUNT[rangeLevel+1]) {
                reachedLevel++;
                rangeLevel++;
                changed=true;
                return SUCCESS;
            }else{
                return FAILED;
            }
        }
        return ERROR;
    }

    public int n=0;
    public void decreaseRange(){
        if(rangeLevel>0){
            rangeLevel=0;
            changed=true;
            n=0;
        }
    }

    public void barutsu(){
        if(n==3){
            rangeLevel=-1;
            changed=true;
        }else{
            n++;
        }
    }

    int ti1=1;
    double tj1=0;
    double tk1=0;
    public void clean(){
        assert this.level != null;
        int x = this.worldPosition.getX();
        int y = this.worldPosition.getY();
        int z = this.worldPosition.getZ();
        int count=0;
        for (int i = ti1; i <= MAX_RANGE; i++) {
            for (double j = tj1; j <= 6.28; j+=0.012) {
                for (double k = tk1; k <= 3.14; k+=0.012) {
                    if(i==rangeLevel){
                        continue;
                    }
                    int range = 5+i*5;
                    BlockPos tempPos = new BlockPos((int)(x+range*Math.sin(j)*Math.cos(k)),
                            (int)(y+range*Math.sin(j)*Math.sin(k)),(int)(z+range*Math.cos(j)));
                    if(BlockHandler.areSameBlockType(this.level.getBlockState(tempPos).getBlock(),BlockHandler.cloudWall)){
                        this.level.setBlockAndUpdate(tempPos,Blocks.AIR.defaultBlockState());
                        count++;
                        if(count==250){
                            ti1=i;
                            tj1=j;
                            tk1=k;
                            return;
                        }
                    }
                }
            }
        }
        ti1=1;
        tj1=tk1=0;
        if(count==0){
            changed=false;
        }
    }

    double ti2=0;
    double tj2=0;
    boolean fastMode = false;
    public void build(){
        assert this.level != null;
        int x = this.worldPosition.getX();
        int y = this.worldPosition.getY();
        int z = this.worldPosition.getZ();
        int range = 5+rangeLevel*5;
        int countBuild=0;
        int countSearch=0;
        for (double i = ti2; i <= 6.28; i+=0.012) {
            for (double j = tj1; j <= 3.14; j+=0.012) {
                BlockPos tempPos = new BlockPos((int)(x+range*Math.sin(i)*Math.cos(j)),
                        (int)(y+range*Math.sin(i)*Math.sin(j)),(int)(z+range*Math.cos(i)));
                if(BlockHandler.areSameBlockType(this.level.getBlockState(tempPos).getBlock(),Blocks.AIR)){
                    this.level.setBlockAndUpdate(tempPos,BlockHandler.cloudWall.defaultBlockState());
                    countBuild++;
                    if(countBuild==100){
                        ti2=i;
                        tj2=j;
                        return;
                    }
                }
                if(!fastMode){
                    countSearch++;
                    if(countSearch>10000){
                        ti2=i;
                        tj2=j;
                        return;
                    }
                }
            }
        }
        ti2=tj2=0;
        fastMode=countBuild!=0;
    }

    boolean firstCheck=true;
    public void checkClosest(){
        firstCheck=false;
        for (int i = 0; i < laputaCoreWorldList.size(); i++) {
            if(!BlockHandler.areSameBlockType(laputaCoreWorldList.get(i)
                    .getBlockState(laputaCoreList.get(i)).getBlock(),BlockHandler.laputaCore)) {
                LaputaCoreEntity.laputaCoreList.remove(i);
                LaputaCoreEntity.laputaCoreWorldList.remove(i);
                i--;
                continue;
            }
            if(laputaCoreWorldList.get(i).equals(this.level)){
                if((!laputaCoreList.get(i).equals(this.getBlockPos())) && laputaCoreList.get(i).distSqr(
                        this.getBlockPos().getX(),this.getBlockPos().getY(),this.getBlockPos().getZ(),true)<10000){
                    this.level.setBlockAndUpdate(this.getBlockPos(),BlockHandler.pilotBlock.defaultBlockState());
                    firstCheck=true;
                }
            }
        }
    }

    boolean saved = false;
    public void saveData(){
        for (int i = 0; i < laputaCoreWorldList.size(); i++) {
            if(laputaCoreWorldList.get(i).equals(this.level)){
                if(laputaCoreList.get(i).equals(this.getBlockPos())){
                    saved=true;
                    break;
                }
            }
        }
        if(!saved){
            laputaCoreWorldList.add(level);
            laputaCoreList.add(this.getBlockPos());
        }
    }

    public void selfDestroy(){
        if(changed){
            clean();
        }else {
            assert this.level != null;
            this.level.setBlockAndUpdate(this.getBlockPos(), Blocks.AIR.defaultBlockState());
        }
    }

    int t=0;

    @Override
    public void tick() {
        t++;
        if(t==20&&level!=null&&!level.isClientSide){
            if(!saved) {
                if(firstCheck){
                    checkClosest();
                }
                saveData();
            }
            if (rangeLevel==-1){
                selfDestroy();
            }else if(refill&&rangeLevel==0){
                clean();
            }else{
                if(refill&&changed){
                    clean();
                }
                if(refill&&!firstCheck){
                    build();
                }
            }
            t=0;
        }
    }
}
