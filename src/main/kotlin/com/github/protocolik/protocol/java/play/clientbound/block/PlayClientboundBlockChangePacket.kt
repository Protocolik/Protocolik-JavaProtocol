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

data class PlayClientboundBlockChangePacket
@JvmOverloads
constructor(
        var position: Position = Position(),
        var type: Int = 0,
        var metadata: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_BLOCK_CHANGE) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                position = byteBuf.readPosition(protocolVersion)
                type = byteBuf.readVarInt()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                position = byteBuf.readPosition(protocolVersion)
                type = byteBuf.readVarInt()
                metadata = byteBuf.readByte().toInt()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writePosition(position, protocolVersion)
                byteBuf.writeVarInt(type)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writePosition(position, protocolVersion)
                byteBuf.writeVarInt(type)
                byteBuf.writeByte(metadata)
            }
        }
    }
}