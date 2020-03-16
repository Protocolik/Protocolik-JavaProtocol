package com.github.protocolik.protocol.java.play.clientbound.world

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayClientboundWorldTimePacket
@JvmOverloads
constructor(
        var worldAge: Long = 0,
        var time: Long = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_WORLD_TIME) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        worldAge = byteBuf.readLong()
        time = byteBuf.readLong()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeLong(worldAge)
        byteBuf.writeLong(time)
    }
}