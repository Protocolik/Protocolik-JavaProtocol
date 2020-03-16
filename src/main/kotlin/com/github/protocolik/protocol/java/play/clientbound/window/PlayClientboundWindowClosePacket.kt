package com.github.protocolik.protocol.java.play.clientbound.window

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayClientboundWindowClosePacket
@JvmOverloads
constructor(
        var windowId: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_WINDOW_CLOSE) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        windowId = byteBuf.readUnsignedByte().toInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeByte(windowId)
    }
}