package com.meacks.miyaworld.items;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.item.Item;

public class RocketLauncher extends Item{


    public RocketLauncher(){
        super(new Properties().tab(MiyaWorld.MAGIC_ITEM_GROUP));
        this.setRegistryName("rocket_launcher");
        ItemHandler.ITEMS.add(this);
    }


}
