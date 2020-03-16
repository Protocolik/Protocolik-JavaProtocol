package com.github.protocolik.protocol.java.login.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.*
import io.netty.buffer.ByteBuf

data class LoginServerboundPluginResponsePacket
@JvmOverloads
constructor(
        var messageId: Int = 0,
        var channel: String = "",
        var data: ByteArray = byteArrayOf()
) : Packet(PacketType.LOGIN_SERVERBOUND_PLUGIN_RESPONSE) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        messageId = byteBuf.readVarInt()
        channel = byteBuf.readString()
        data = byteBuf.readByteArray()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(messageId)
        byteBuf.writeString(channel)
        byteBuf.writeByteArray(data)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginServerboundPluginResponsePacket

        if (messageId != other.messageId) return false
        if (channel != other.channel) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = messageId
        result = 31 * result + channel.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}