package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayClientboundSetSlotPacket
@JvmOverloads
constructor(
        var slot: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_SET_SLOT) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        slot = byteBuf.readByte().toInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeByte(slot)
    }
}