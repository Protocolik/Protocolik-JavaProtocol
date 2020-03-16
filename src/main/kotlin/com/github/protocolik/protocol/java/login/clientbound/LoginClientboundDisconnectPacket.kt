package com.github.protocolik.protocol.java.login.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.writeString
import io.netty.buffer.ByteBuf

data class LoginClientboundDisconnectPacket
@JvmOverloads
constructor(
        var reason: String = ""
) : Packet(PacketType.LOGIN_CLIENTBOUND_DISCONNECT) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        reason = byteBuf.readString()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeString(reason)
    }
}