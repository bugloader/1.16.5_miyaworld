package com.meacks.miyaworld.handlers;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityHandler {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, MiyaWorld.MODID);
    public static final RegistryObject<EntityType<GiantRobot>> GIANT_ROBOT_REGISTRY_OBJECT =
            ENTITY_TYPE_DEFERRED_REGISTER.register("giant_robot", () -> EntityType.Builder.of(GiantRobot::new,
                    EntityClassification.MISC).sized(1f, 2.5f).build("giant_robot"));

    public static final RegistryObject<EntityType<SoundPlayingEntity>> SOUND_ENTITY_REGISTRY_OBJECT =
            ENTITY_TYPE_DEFERRED_REGISTER.register("sound_entity",
                    () -> EntityType.Builder.<SoundPlayingEntity>of(SoundPlayingEntity::new,
                    EntityClassification.MISC).sized(0.5f, 0.5f).build("sound_entity"));

    public static final RegistryObject<EntityType<RobotLaser>> ROBOT_LASER_REGISTRY_OBJECT =
            ENTITY_TYPE_DEFERRED_REGISTER.register("robot_laser", () -> EntityType.Builder.<RobotLaser>of(RobotLaser::new,
                    EntityClassification.MISC).sized(0.3f, 0.3f).build("robot_laser"));

    public static final RegistryObject<EntityType<BulletEntity>> BULLET_ENTITY_REGISTRY_OBJECT =
            ENTITY_TYPE_DEFERRED_REGISTER.register("bullet_entity", () -> EntityType.Builder.<BulletEntity>of(BulletEntity::new,
                    EntityClassification.MISC).sized(0.2f, 0.2f).build("bullet_entity"));

    public static final RegistryObject<EntityType<RocketEntity>> ROCKET_ENTITY_REGISTRY_OBJECT =
            ENTITY_TYPE_DEFERRED_REGISTER.register("rocket_entity", () -> EntityType.Builder.<RocketEntity>of(RocketEntity::new,
                    EntityClassification.MISC).sized(0.5f, 0.5f).build("rocket_entity"));

}
