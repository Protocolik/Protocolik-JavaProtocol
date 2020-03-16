package com.github.protocolik.protocol.java.login.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.writeString
import io.netty.buffer.ByteBuf

data class LoginServerboundLoginStartPacket
@JvmOverloads
constructor(
        var username: String = ""
) : Packet(PacketType.LOGIN_SERVERBOUND_LOGIN_START) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        username = byteBuf.readString()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeString(username)
    }
}