package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.minecraft.Position
import com.github.protocolik.api.minecraft.readPosition
import com.github.protocolik.api.minecraft.writePosition
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayServerboundBlockNbtRequestPacket
@JvmOverloads
constructor(
        var transactionId: Int = 0,
        var position: Position = Position()
) : Packet(PacketType.PLAY_SERVERBOUND_BLOCK_NBT_REQUEST) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        transactionId = byteBuf.readVarInt()
        position = byteBuf.readPosition(protocolVersion)
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(transactionId)
        byteBuf.writePosition(position, protocolVersion)
    }
}