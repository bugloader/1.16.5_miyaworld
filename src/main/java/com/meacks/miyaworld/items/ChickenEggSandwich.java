package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class ChickenEggSandwich extends Item{
    private static final Food food = (new Food.Builder().build());

    public ChickenEggSandwich(){
        super(new Properties().food(food).tab(MiyaWorld.ARTIFACT_ITEM_GROUP));
        this.setRegistryName("chicken_egg_sw");
        ItemHandler.ITEMS.add(this);
    }


}
