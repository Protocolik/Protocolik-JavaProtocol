package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundSwitchCameraPacket
@JvmOverloads
constructor(
        var cameraId: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_SWITCH_CAMERA) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        cameraId = byteBuf.readVarInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(cameraId)
    }
}