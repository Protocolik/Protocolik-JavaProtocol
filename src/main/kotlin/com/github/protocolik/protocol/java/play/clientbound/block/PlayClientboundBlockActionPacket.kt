package com.github.protocolik.protocol.java.play.clientbound.block

import com.github.protocolik.api.minecraft.Position
import com.github.protocolik.api.minecraft.readPosition
import com.github.protocolik.api.minecraft.writePosition
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundBlockActionPacket
@JvmOverloads
constructor(
        var position: Position = Position(),
        var actionId: Int = 0,
        var actionParam: Int = 0,
        var blockId: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_BLOCK_ACTION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        position = byteBuf.readPosition(protocolVersion)
        actionId = byteBuf.readByte().toInt()
        actionParam = byteBuf.readByte().toInt()
        blockId = byteBuf.readVarInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writePosition(position, protocolVersion)
        byteBuf.writeByte(actionId)
        byteBuf.writeByte(actionParam)
        byteBuf.writeVarInt(blockId)
    }
}