package com.github.protocolik.protocol.java.login.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readByteArray
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.writeByteArray
import com.github.protocolik.api.utils.writeString
import io.netty.buffer.ByteBuf

data class LoginClientboundEncryptionRequestPacket
@JvmOverloads
constructor(
        var serverId: String = "",
        var publicKey: ByteArray = byteArrayOf(),
        var verifyToken: ByteArray = byteArrayOf()
) : Packet(PacketType.LOGIN_CLIENTBOUND_ENCRYPTION_REQUEST) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        serverId = byteBuf.readString()
        publicKey = byteBuf.readByteArray()
        verifyToken = byteBuf.readByteArray()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeString(serverId)
        byteBuf.writeByteArray(publicKey)
        byteBuf.writeByteArray(verifyToken)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginClientboundEncryptionRequestPacket

        if (serverId != other.serverId) return false
        if (!publicKey.contentEquals(other.publicKey)) return false
        if (!verifyToken.contentEquals(other.verifyToken)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = serverId.hashCode()
        result = 31 * result + publicKey.contentHashCode()
        result = 31 * result + verifyToken.contentHashCode()
        return result
    }
}