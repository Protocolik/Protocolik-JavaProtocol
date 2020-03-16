package com.github.protocolik.protocol.java.login.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class LoginClientboundSetCompressionPacket
@JvmOverloads
constructor(
        var threshold: Int = 0
) : Packet(PacketType.LOGIN_CLIENTBOUND_SET_COMPRESSION) {

    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        threshold = byteBuf.readVarInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(threshold)
    }
}