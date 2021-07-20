package com.meacks.miyaworld.handlers;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.entity.GiantRobot;
import com.meacks.miyaworld.entity.RobotLaser;
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
                    EntityClassification.MISC).sized(1f, 3f).build("giant_robot"));

    public static final RegistryObject<EntityType<RobotLaser>> ROBOT_LASER_REGISTRY_OBJECT =
            ENTITY_TYPE_DEFERRED_REGISTER.register("robot_laser", () -> EntityType.Builder.<RobotLaser>of(RobotLaser::new,
                    EntityClassification.MISC).sized(0.5f, 0.5f).build("robot_laser"));

}
