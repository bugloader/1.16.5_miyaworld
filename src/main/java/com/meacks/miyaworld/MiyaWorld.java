package com.meacks.miyaworld;

import com.meacks.miyaworld.entity.GiantRobot;
import com.meacks.miyaworld.entity.render.GiantRobotRender;
import com.meacks.miyaworld.entity.render.RobotLaserRender;
import com.meacks.miyaworld.handlers.*;
import com.meacks.miyaworld.registry.RegistryGeZi;
import jdk.jfr.Name;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MiyaWorld.MODID)
@Name(MiyaWorld.NAME)
public class MiyaWorld
{
    public static final String MODID = "miyaworld";
    public static final String NAME = "Miya World";
    public static final String VERSION = "0.0.1";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup ARTIFACT_ITEM_GROUP = new ItemGroup("Artifact Item") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Items.STICK);
        }
    };
    public static final ItemGroup MAGIC_ITEM_GROUP = new ItemGroup("Magic Item") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Items.BLAZE_ROD);
        }
    };
    public static final ItemGroup MAGIC_Block_GROUP = new ItemGroup("Magic Block") {
    @OnlyIn(Dist.CLIENT)
    public ItemStack makeIcon() {
        return new ItemStack(Items.GOLD_BLOCK);
    }
    };

    public MiyaWorld() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        bus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        bus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        bus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        bus.addListener(this::doClientStuff);

        RegistryGeZi.register(bus);
        EntityHandler.ENTITY_TYPE_DEFERRED_REGISTER.register(bus);
        TileEntityHandler.TILE_ENTITY_TYPE_DEFERRED_REGISTER.register(bus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreHandler::generateOres);
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        event.enqueueWork(() ->
        {
            GlobalEntityTypeAttributes.put(EntityHandler.GIANT_ROBOT_REGISTRY_OBJECT.get(), GiantRobot.createAttributes().build());

        });
        LOGGER.info("HELLO FROM PREINIT");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo(MODID, "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            blockRegistryEvent.getRegistry().registerAll(BlockHandler.BLOCKS.toArray(new Block[0]));
            LOGGER.info("HELLO from block register");
        }@SubscribeEvent
        public static void onItemRegister(RegistryEvent.Register<Item> itemRegistryEvent) {
            itemRegistryEvent.getRegistry().registerAll(ItemHandler.ITEMS.toArray(new Item[0]));
            LOGGER.info("Hello from item register");
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistry {
        @SubscribeEvent
        public static void onClientSetUpEvent(FMLClientSetupEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(EntityHandler.GIANT_ROBOT_REGISTRY_OBJECT.get(), GiantRobotRender::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityHandler.ROBOT_LASER_REGISTRY_OBJECT.get(), RobotLaserRender::new);
            System.out.println("yes i am here");
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonEventHandler {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {

        }
    }

}
