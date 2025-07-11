package net.oppakolba.oppamod.init;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.block.custom.AlterioTableBlock;
import net.oppakolba.oppamod.block.custom.WaterLeafBlock;
import net.oppakolba.oppamod.item.CreativeModeTab;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Oppamod.MOD_ID);


public static final RegistryObject<Block> PLATINUM_ORE = registerBlock("platinum_ore",
        () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                .strength(5f)
                .requiresCorrectToolForDrops(),
                UniformInt.of(3,5)), CreativeModeTab.OPPA_TAB);

    public static final RegistryObject<Block> AlTERIO_TABLE = registerBlock("alterio_table",
            () -> new AlterioTableBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .noOcclusion()
                    .strength( 30f)
                    .requiresCorrectToolForDrops()), CreativeModeTab.OPPA_TAB);

    public static final RegistryObject<Block> PLATINUM_BLOCK = registerBlock("platinum_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f)
                    .requiresCorrectToolForDrops()), CreativeModeTab.OPPA_TAB);


        public static final RegistryObject<Block> WATER_LEAF_BLOCK = BLOCKS.register("water_leaf_block",
                () -> new WaterLeafBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH)));



    private static <I extends Block> RegistryObject<I> registerBlock(String name, Supplier<I> block, net.minecraft.world.item.CreativeModeTab tab) {
        RegistryObject<I> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);

        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            net.minecraft.world.item.CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
