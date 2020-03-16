package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayClientboundUnloadChunkPacket
@JvmOverloads
constructor(
        var chunkX: Int = 0,
        var chunkZ: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_UNLOAD_CHUNK) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        chunkX = byteBuf.readInt()
        chunkZ = byteBuf.readInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeInt(chunkX)
        byteBuf.writeInt(chunkZ)
    }
}