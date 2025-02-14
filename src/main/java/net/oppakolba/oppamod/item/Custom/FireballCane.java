package net.oppakolba.oppamod.item.Custom;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.oppakolba.oppamod.client.ClientManaData;
import net.oppakolba.oppamod.entity.ModEntities;
import net.oppakolba.oppamod.entity.custom.CustomFireball;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.ManaDataSyncS2CPacket;

public class  FireballCane extends Item {
    public FireballCane(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(!level.isClientSide()){
            LazyOptional<PlayerMana> manaOptional = player.getCapability(PlayerManaProvider.PLAYER_MANA);
            if(manaOptional.isPresent()){
                PlayerMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                if(mana.getMana() >= 11){
                    mana.subMana(11);
                    CustomFireball customFireball = new CustomFireball(ModEntities.CUSTOM_FIREBALL.get(), level, player, player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 2.0f);
                    level.addFreshEntity(customFireball);
                    if(player instanceof ServerPlayer serverplayer) {
                        ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), serverplayer);
                    }

                }
            }
            else{
                    player.sendSystemMessage(Component.literal("Малло маны"));
                    return InteractionResultHolder.fail(itemStack);
                }
            }
        return InteractionResultHolder.success(itemStack);
        }
    }

