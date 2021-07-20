package com.meacks.miyaworld.entity;

import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class GiantRobot extends IronGolemEntity implements IFlyingAnimal, IRideable, IRangedAttackMob{
    private int eyeUpdateTick;
    public static final int eyeCoolDownTime = 30;

    public GiantRobot(EntityType<? extends GiantRobot> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlyingMovementController(this, 10, false);
        eyeUpdateTick=0;

    }

    @Override
    public boolean canAttack(LivingEntity livingEntity) {
        if(livingEntity instanceof PlayerEntity){
            Item helmet = livingEntity.getItemBySlot(EquipmentSlotType.HEAD).getItem();
            return !(helmet.equals(ItemHandler.pilotStoneNecklace));
        }
        return true;
    }

    @Override
    public boolean canAttack(LivingEntity livingEntity, EntityPredicate entityPredicate) {
        if(livingEntity instanceof PlayerEntity){
            Item helmet = livingEntity.getItemBySlot(EquipmentSlotType.HEAD).getItem();
            if (!(helmet.equals(ItemHandler.pilotStoneNecklace))){
                return true;
            }
        }
        return entityPredicate.test(this, livingEntity);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return true;
    }

    @Override
    public boolean canBeControlledByRider() {
        return true;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }


    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    public void travelWithInput(Vector3d vector3d) {
        super.travel(vector3d);
    }

    @Override
    public float getSteeringSpeed() {
        return 0;
    }

    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, world);
        flyingpathnavigator.setCanOpenDoors(true);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3D)
                .add(Attributes.ATTACK_DAMAGE, 15D)
                .add(Attributes.FOLLOW_RANGE, 40D)
                .add(Attributes.ARMOR, 30D);
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float floatNum) {
        this.performRangedAttack(livingEntity.getX(),
                livingEntity.getY() + (double)livingEntity.getEyeHeight(), livingEntity.getZ());
    }

    private void performRangedAttack(double x, double y, double z) {
        RobotLaser robotLaser = new RobotLaser(this.level, this, x-this.getX(),
                y-this.getY()-this.getEyeHeight(), z-this.getZ());
        robotLaser.setOwner(this);
        robotLaser.setPosRaw(this.getX(),this.getY()+this.getEyeHeight(),this.getZ());
        this.level.addFreshEntity(robotLaser);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 20.0F));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(5, new ShowVillagerFlowerGoal(this));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new DefendVillageTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, false, false, (p_234199_0_) -> {
            return p_234199_0_ instanceof IMob;
        }));
        this.targetSelector.addGoal(4, new ResetAngerGoal<>(this, false));
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        LivingEntity entity = this.getTarget();
        if(null!=entity){
            if(eyeUpdateTick>eyeCoolDownTime) {
                this.performRangedAttack(entity, 0);
                eyeUpdateTick=0;
            }else{
                eyeUpdateTick++;
            }
        }
    }

}
