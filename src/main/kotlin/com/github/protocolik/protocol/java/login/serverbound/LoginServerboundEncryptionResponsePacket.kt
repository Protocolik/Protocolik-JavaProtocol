package com.github.protocolik.protocol.java.login.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readByteArray
import com.github.protocolik.api.utils.writeByteArray
import io.netty.buffer.ByteBuf

data class LoginServerboundEncryptionResponsePacket
@JvmOverloads
constructor(
        var sharedSecret: ByteArray = byteArrayOf(),
        var verifyToken: ByteArray = byteArrayOf()
) : Packet(PacketType.LOGIN_SERVERBOUND_ENCRYPTION_RESPONSE) {

    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        sharedSecret = byteBuf.readByteArray()
        verifyToken = byteBuf.readByteArray()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeByteArray(sharedSecret)
        byteBuf.writeByteArray(verifyToken)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginServerboundEncryptionResponsePacket

        if (!sharedSecret.contentEquals(other.sharedSecret)) return false
        if (!verifyToken.contentEquals(other.verifyToken)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sharedSecret.contentHashCode()
        result = 31 * result + verifyToken.contentHashCode()
        return result
    }
}