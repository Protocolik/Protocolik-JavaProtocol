package com.github.protocolik.protocol.java.handshaking

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolState
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeString
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class HandshakingServerboundSetProtocolPacket
@JvmOverloads
constructor(
        var version: ProtocolVersion = ProtocolVersion.CURRENT,
        var serverHost: String = "",
        var serverPort: Int = 0,
        var nextState: ProtocolState = ProtocolState.LOGIN
) : Packet(PacketType.HANDSHAKING_SERVERBOUND_SET_PROTOCOL) {

    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        version = ProtocolVersion[byteBuf.readVarInt()]
        serverHost = byteBuf.readString()
        serverPort = byteBuf.readUnsignedShort()
        nextState = ProtocolState[byteBuf.readVarInt()]
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(ProtocolVersion.CURRENT.id)
        byteBuf.writeString(serverHost)
        byteBuf.writeShort(serverPort)
        byteBuf.writeVarInt(nextState.id)
    }
}