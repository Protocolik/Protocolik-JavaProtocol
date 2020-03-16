package com.github.protocolik.protocol.java.play.serverbound.window

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayServerboundWindowButtonClickPacket
@JvmOverloads
constructor(
        var containerId: Int = 0,
        var buttonId: Int = 0
) : Packet(PacketType.PLAY_SERVERBOUND_WINDOW_BUTTON_CLICK) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        containerId = byteBuf.readByte().toInt()
        buttonId = byteBuf.readByte().toInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeByte(containerId)
        byteBuf.writeByte(buttonId)
    }
}