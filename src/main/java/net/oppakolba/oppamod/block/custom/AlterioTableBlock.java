package net.oppakolba.oppamod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.oppakolba.oppamod.block.entity.AlterioTableEntity;
import net.oppakolba.oppamod.init.ModBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlterioTableBlock extends BaseEntityBlock {

    public AlterioTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos blockPos, BlockState blockState, boolean b) {
        if(state.getBlock() != blockState.getBlock()){
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if(blockEntity instanceof AlterioTableEntity){
                ((AlterioTableEntity) blockEntity).drops();
            }
        }
        super.onRemove(state, level, blockPos, blockState, b);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide()){
            BlockEntity entity = level.getBlockEntity(blockPos);
            if(entity instanceof AlterioTableEntity){
                NetworkHooks.openScreen(((ServerPlayer) player), (AlterioTableEntity) entity, blockPos);
            }
            else{
                throw new IllegalStateException("Чет-то не работает");
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlterioTableEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.ALTERIO_TABLE.get(), AlterioTableEntity::tick);
    }
}
