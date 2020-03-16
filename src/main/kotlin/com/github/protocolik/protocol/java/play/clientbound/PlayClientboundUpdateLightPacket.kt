package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readByteArray
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeByteArray
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundUpdateLightPacket
@JvmOverloads
constructor(
        var chunkX: Int = 0,
        var chunkZ: Int = 0,
        var skyLightMask: Int = 0,
        var blockLightMask: Int = 0,
        var emptySkyLightMask: Int = 0,
        var emptyBlockLightMask: Int = 0,
        var skyLight: ByteArray = byteArrayOf(),
        var blockLight: ByteArray = byteArrayOf()
) : Packet(PacketType.PLAY_CLIENTBOUND_UPDATE_LIGHT) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        chunkX = byteBuf.readVarInt()
        chunkZ = byteBuf.readVarInt()
        skyLightMask = byteBuf.readVarInt()
        blockLightMask = byteBuf.readVarInt()
        emptySkyLightMask = byteBuf.readVarInt()
        emptyBlockLightMask = byteBuf.readVarInt()
        skyLight = byteBuf.readByteArray()
        blockLight = byteBuf.readByteArray()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(chunkX)
        byteBuf.writeVarInt(chunkZ)
        byteBuf.writeVarInt(skyLightMask)
        byteBuf.writeVarInt(blockLightMask)
        byteBuf.writeVarInt(emptySkyLightMask)
        byteBuf.writeVarInt(emptyBlockLightMask)
        byteBuf.writeByteArray(skyLight)
        byteBuf.writeByteArray(blockLight)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayClientboundUpdateLightPacket

        if (chunkX != other.chunkX) return false
        if (chunkZ != other.chunkZ) return false
        if (skyLightMask != other.skyLightMask) return false
        if (blockLightMask != other.blockLightMask) return false
        if (emptySkyLightMask != other.emptySkyLightMask) return false
        if (emptyBlockLightMask != other.emptyBlockLightMask) return false
        if (!skyLight.contentEquals(other.skyLight)) return false
        if (!blockLight.contentEquals(other.blockLight)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chunkX
        result = 31 * result + chunkZ
        result = 31 * result + skyLightMask
        result = 31 * result + blockLightMask
        result = 31 * result + emptySkyLightMask
        result = 31 * result + emptyBlockLightMask
        result = 31 * result + skyLight.contentHashCode()
        result = 31 * result + blockLight.contentHashCode()
        return result
    }
}