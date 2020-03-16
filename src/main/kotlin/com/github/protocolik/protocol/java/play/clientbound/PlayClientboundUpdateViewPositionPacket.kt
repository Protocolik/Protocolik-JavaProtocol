package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.data.UserConnection
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundUpdateViewPositionPacket
@JvmOverloads
constructor(
        var chunkX: Int = 0,
        var chunkZ: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_UPDATE_VIEW_POSITION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        chunkX = byteBuf.readVarInt()
        chunkZ = byteBuf.readVarInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(chunkX)
        byteBuf.writeVarInt(chunkZ)
    }

    override fun remap(
            protocolVersion: ProtocolVersion,
            userConnection: UserConnection
    ): List<Packet> {
        return if (protocolVersion >= ProtocolVersion.RELEASE_1_14) {
            super.remap(protocolVersion, userConnection)
        } else {
            emptyList()
        }
    }
}