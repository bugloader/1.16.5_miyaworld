package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class EggSandwich extends Item{

    public static final Food EGG_SANDWICH = (new Food.Builder()).nutrition(8).saturationMod(0.8F).build();

    public EggSandwich(){
        super(new Properties().food(EGG_SANDWICH).tab(MiyaWorld.ARTIFACT_ITEM_GROUP).stacksTo(1024).rarity(Rarity.UNCOMMON));
        this.setRegistryName("egg_sandwich");
        ItemHandler.ITEMS.add(this);
    }


}
