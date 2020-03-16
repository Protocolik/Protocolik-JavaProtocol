package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.minecraft.ItemStack
import com.github.protocolik.api.minecraft.entity.player.Hand
import com.github.protocolik.api.minecraft.readItemStack
import com.github.protocolik.api.minecraft.writeItemStack
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayServerboundEditBookPacket
@JvmOverloads
constructor(
        var newBook: ItemStack = ItemStack(),
        var signing: Boolean = false,
        var hand: Hand = Hand.MAIN_HAND
) : Packet(PacketType.PLAY_SERVERBOUND_EDIT_BOOK) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        newBook = byteBuf.readItemStack()!!
        signing = byteBuf.readBoolean()
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_13_1 -> {
                hand = Hand[byteBuf.readVarInt()]
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeItemStack(newBook)
        byteBuf.writeBoolean(signing)
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_13_1 -> {
                byteBuf.writeVarInt(hand.id)
            }
        }
    }
}