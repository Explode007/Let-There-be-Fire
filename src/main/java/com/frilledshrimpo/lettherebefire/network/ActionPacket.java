package com.frilledshrimpo.lettherebefire.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ActionPacket {
    private final PlayerAction actionType;

    public ActionPacket(PlayerAction actionType) {
        this.actionType = actionType;
    }

    public static void encode(ActionPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.actionType.getId()); // Write the action ID
    }

    public static ActionPacket decode(FriendlyByteBuf buf) {
        return new ActionPacket(PlayerAction.fromId(buf.readInt()));
    }

    public static void handle(ActionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && msg.actionType != null) {
                msg.actionType.execute(player); // Execute the corresponding action
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
