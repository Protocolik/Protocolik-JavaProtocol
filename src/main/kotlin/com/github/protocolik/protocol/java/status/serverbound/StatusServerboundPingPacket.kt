package com.github.protocolik.protocol.java.status.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class StatusServerboundPingPacket
@JvmOverloads
constructor(
        var time: Long = 0
) : Packet(PacketType.STATUS_SERVERBOUND_PING) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        time = byteBuf.readLong()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeLong(time)
    }
}