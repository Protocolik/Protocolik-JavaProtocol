package com.github.protocolik.protocol.java.status.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class StatusClientboundPongPacket
@JvmOverloads
constructor(
        var time: Long = 0
) : Packet(PacketType.STATUS_CLIENTBOUND_PONG) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        time = byteBuf.readLong()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeLong(time)
    }
}