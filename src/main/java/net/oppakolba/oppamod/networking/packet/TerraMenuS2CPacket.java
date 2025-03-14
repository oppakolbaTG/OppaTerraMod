package net.oppakolba.oppamod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.oppamod.client.ClientManaData;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;

import java.util.function.Supplier;

public class TerraMenuS2CPacket {
    private final int max_mana;

    public TerraMenuS2CPacket(int mana) {
        this.max_mana = mana;
    }

    public TerraMenuS2CPacket(FriendlyByteBuf buf) {
        this.max_mana = buf.readInt();
    }

    public void toByte (FriendlyByteBuf buf) {
        buf.writeInt(max_mana);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isClient()) {
                ClientManaData.setPlayerMaxMana(max_mana);
            }
        });
        return true;
    }
}
