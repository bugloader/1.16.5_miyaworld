package com.meacks.miyaworld.entity;

import com.meacks.miyaworld.handlers.EntityHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class RobotLaser extends DamagingProjectileEntity {

    public RobotLaser(EntityType<? extends DamagingProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public RobotLaser(World p_i1794_1_, LivingEntity p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_) {
        super(EntityHandler.ROBOT_LASER_REGISTRY_OBJECT.get(), p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_, p_i1794_1_);
    }

    public boolean isOnFire() {
        return false;
    }

    protected float getInertia() {
        return 1.5f;
    }

    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        if (!this.level.isClientSide) {
            Entity entity = p_213868_1_.getEntity();
            Entity entity1 = this.getOwner();
            if (entity instanceof LivingEntity && entity1!=entity) {
                entity.hurt(DamageSource.MAGIC,8f);
                ((LivingEntity)entity).setRemainingFireTicks(40);
            }
            Explosion.Mode explosion$mode = Explosion.Mode.NONE;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 0.5F, false, explosion$mode);
            this.remove();
        }
    }

    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        if (!this.level.isClientSide) {
            Explosion.Mode explosion$mode = Explosion.Mode.NONE;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, explosion$mode);
            this.remove();
        }

    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }

    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}