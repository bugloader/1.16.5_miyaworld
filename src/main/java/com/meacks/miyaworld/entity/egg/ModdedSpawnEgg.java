package com.meacks.miyaworld.entity.egg;

import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.RegistryObject;

public class ModdedSpawnEgg extends SpawnEggItem {
    private final RegistryObject<?> supplier;

    public ModdedSpawnEgg(EntityType<?> typeIn, RegistryObject<?> supplierIn, int primaryColorIn, int secondaryColorIn, Properties builder, String name)
    {
        super(typeIn, primaryColorIn, secondaryColorIn, builder);
        supplier = supplierIn;
        this.setRegistryName(name);
        ItemHandler.ITEMS.add(this);
    }

    @Override
    public EntityType<?> getType(CompoundNBT p_208076_1_)
    {
        return (EntityType<?>) supplier.get();
    }
}