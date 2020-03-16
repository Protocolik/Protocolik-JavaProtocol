package com.github.protocolik.protocol.java.login.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.writeString
import io.netty.buffer.ByteBuf
import java.util.*

data class LoginClientboundSuccessPacket
@JvmOverloads
constructor(
        var uuid: UUID = UUID(0, 0),
        var username: String = ""
) : Packet(PacketType.LOGIN_CLIENTBOUND_SUCCESS) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        uuid = UUID.fromString(byteBuf.readString())
        username = byteBuf.readString()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeString(uuid.toString())
        byteBuf.writeString(username)
    }
}