/*××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
 × The MIT License (MIT)
 × Copyright © 2020. 南织( 1448848683@qq.com )
 ×
 × Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 ×
 × The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 ×
 × THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××*/

package com.meacks.miyaworld.registry;

import com.meacks.miyaworld.MiyaWorld;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class RegistryGeZi {

    public static final DeferredRegister<Block> blockReg = DeferredRegister.create( ForgeRegistries.BLOCKS, MiyaWorld.MODID ) ;
    public static final DeferredRegister<Item> itemReg = DeferredRegister.create( ForgeRegistries.ITEMS, MiyaWorld.MODID ) ;
    public static final DeferredRegister<Effect> effectReg = DeferredRegister.create( ForgeRegistries.POTIONS, MiyaWorld.MODID ) ;
    public static final DeferredRegister<TileEntityType<?>> tileEntityRegDef = DeferredRegister.create( ForgeRegistries.TILE_ENTITIES, MiyaWorld.MODID ) ;

    private static String _dn( String domain, String name ) {
        return domain + "/" + name ;
    }
    public static Block register(String name, Block block, Item.Properties properties) {
        blockReg.register( name, ( ) -> block ) ;
        itemReg.register( name, ( ) -> new BlockItem( block, properties ) );
        return block ;
    }
    public static Block register( String domain, String name, Block block, Item.Properties properties ) {
        return register( _dn( domain, name ), block, properties ) ;
    }
    public static Item register(String name, Item item) {
        itemReg.register( name, ( ) -> item );
        return item ;
    }
    public static Item register( String domain, String name, Item item ) {
        return register( _dn( domain, name ), item ) ;
    }
    public static Block register( String name, BlockItem BlockItemIn ) {
        blockReg.register( name, BlockItemIn::getBlock ) ;
        itemReg.register( name, ( ) -> BlockItemIn );
        return BlockItemIn.getBlock( ) ;
    }
    public static Block register( String domain, String name, BlockItem BlockItemIn ) {
        return register( _dn( domain, name ), BlockItemIn ) ;
    }
    public static Effect register( String name, Effect effectIn ) {
        effectReg.register( name, ( ) -> effectIn ) ;
        return effectIn.getEffect( ) ;
    }
    public static Effect register( String domain, String name, Effect effectIn ) {
        return register( _dn( domain, name ), effectIn ) ;
    }
    public static <T extends TileEntity> TileEntityType<T> register (String key, Supplier<T> ts, Block blockIn ) {
        TileEntityType<T> te = TileEntityType.Builder.of( ts, blockIn ).build(null) ;
        tileEntityRegDef.register( key, ( ) -> te ) ;
        return te ;
    } ;
    public static <T extends TileEntity> TileEntityType<T> register ( String domain, String name, Supplier<T> ts, Block blockIn ) {
        return register( _dn( domain, name ), ts, blockIn ) ;
    } ;
    public static void register ( IEventBus bus ) {
        blockReg.register( bus ) ;
        itemReg.register( bus ) ;
        effectReg.register( bus ) ;
        tileEntityRegDef.register( bus ) ;
    }  ;

}
