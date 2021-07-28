package com.meacks.miyaworld.handlers;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreHandler{

    public static void generateOres(final BiomeLoadingEvent event){
            oreGenerate(event.getGeneration(),OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockHandler.PILOT_ORE_REGISTRY_OBJECT.get().defaultBlockState(),
                    20,5,20,1);
    }

    private static void oreGenerate(BiomeGenerationSettingsBuilder settingsBuilder, RuleTest fillerType,
                                    BlockState blockState, int veinSize, int lowestY, int highestY,int veinPerChunk){
        settingsBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(
                new OreFeatureConfig(fillerType,blockState,veinSize)).decorated(Placement.RANGE.configured(
                        new TopSolidRangeConfig(lowestY,0,highestY))).squared().count(veinPerChunk));
    }
}