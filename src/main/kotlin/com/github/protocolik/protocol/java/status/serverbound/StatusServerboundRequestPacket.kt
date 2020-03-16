package com.github.protocolik.protocol.java.status.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

class StatusServerboundRequestPacket : Packet(PacketType.STATUS_SERVERBOUND_REQUEST) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {}

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {}

    override fun toString(): String = "StatusServerboundRequestPacket()"
}