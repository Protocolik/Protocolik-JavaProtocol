package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundCollectItemPacket
@JvmOverloads
constructor(
        var collectedEntityId: Int = 0,
        var collectorEntityId: Int = 0,
        var pickupItemCount: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_COLLECT_ITEM) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_11 -> {
                collectedEntityId = byteBuf.readVarInt()
                collectorEntityId = byteBuf.readVarInt()
                pickupItemCount = byteBuf.readVarInt()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                collectedEntityId = byteBuf.readVarInt()
                collectorEntityId = byteBuf.readVarInt()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                collectedEntityId = byteBuf.readInt()
                collectorEntityId = byteBuf.readInt()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_11 -> {
                byteBuf.writeVarInt(collectedEntityId)
                byteBuf.writeVarInt(collectorEntityId)
                byteBuf.writeVarInt(pickupItemCount)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(collectedEntityId)
                byteBuf.writeVarInt(collectorEntityId)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(collectedEntityId)
                byteBuf.writeInt(collectorEntityId)
            }
        }
    }
}