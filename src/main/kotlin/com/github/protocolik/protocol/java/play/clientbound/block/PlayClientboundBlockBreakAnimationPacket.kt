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

data class PlayClientboundBlockBreakAnimationPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var position: Position = Position(),
        var destroyStage: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_BLOCK_BREAK_ANIMATION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        entityId = byteBuf.readVarInt()
        position = byteBuf.readPosition(protocolVersion)
        destroyStage = byteBuf.readByte().toInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(entityId)
        byteBuf.writePosition(position, protocolVersion)
        byteBuf.writeByte(destroyStage)
    }
}