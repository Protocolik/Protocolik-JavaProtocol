package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundKeepAlivePacket
@JvmOverloads
constructor(
        var keepAliveId: Long = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_KEEP_ALIVE) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_12_2 -> {
                keepAliveId = byteBuf.readLong()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                keepAliveId = byteBuf.readVarInt().toLong()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                keepAliveId = byteBuf.readInt().toLong()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_12_2 -> {
                byteBuf.writeLong(keepAliveId)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(keepAliveId.toInt())
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(keepAliveId.toInt())
            }
        }
    }
}