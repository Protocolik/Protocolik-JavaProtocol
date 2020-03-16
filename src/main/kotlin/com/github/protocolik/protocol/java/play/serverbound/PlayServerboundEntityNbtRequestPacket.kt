package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayServerboundEntityNbtRequestPacket
@JvmOverloads
constructor(
        var transactionId: Int = 0,
        var entityId: Int = 0
) : Packet(PacketType.PLAY_SERVERBOUND_ENTITY_NBT_REQUEST) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        transactionId = byteBuf.readVarInt()
        entityId = byteBuf.readVarInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(transactionId)
        byteBuf.writeVarInt(entityId)
    }
}