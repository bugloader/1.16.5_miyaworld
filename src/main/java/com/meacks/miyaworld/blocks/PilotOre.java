package com.meacks.miyaworld.blocks;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.blockEntities.LaputaCoreEntity;
import com.meacks.miyaworld.blockEntities.PilotOreEntity;
import com.meacks.miyaworld.handlers.BlockHandler;
import com.meacks.miyaworld.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.block.TwistingVinesBlock.SHAPE;
import static net.minecraft.tileentity.IHopper.INSIDE;


public class PilotOre extends Block {
    public static final String registryName = "pilot_ore";
    public static final IntegerProperty STATE = IntegerProperty.create("activated", 0, 18);
    public PilotOre(){
        super(Properties.of(Material.STONE).lightLevel((x)->{return Math.max(0,x.getValue(STATE)-3);}));
        registerDefaultState(this.getStateDefinition().any().setValue(STATE,0));
        ItemHandler.createBlockItem(this,registryName,MiyaWorld.MAGIC_Block_GROUP);
    }



    @Override
    public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
        state.add(STATE);
        super.createBlockStateDefinition(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockUse) {
        return this.defaultBlockState().setValue(STATE, 0);
    }

}
