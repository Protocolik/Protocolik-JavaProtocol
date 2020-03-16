package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.minecraft.Position
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundMultiBlockChangePacket
@JvmOverloads
constructor(
        var chunkX: Int = 0,
        var chunkZ: Int = 0,
        var records: List<Record> = emptyList()
) : Packet(PacketType.PLAY_CLIENTBOUND_MULTI_BLOCK_CHANGE) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        chunkX = byteBuf.readInt()
        chunkZ = byteBuf.readInt()
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                records = List(byteBuf.readVarInt()) {
                    val pos = byteBuf.readShort().toInt()
                    val blockId = byteBuf.readVarInt()
                    val x: Int = (chunkX shl 4) + (pos shr 12 and 15)
                    val y: Int = pos and 255
                    val z: Int = (chunkZ shl 4) + (pos shr 8 and 15)
                    Record(Position(x, y, z), blockId)
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                val recordsSize = byteBuf.readShort().toInt()
                byteBuf.readInt()
                records = List(recordsSize) {
                    val pos = byteBuf.readShort().toInt()
                    val blockId = byteBuf.readShort().toInt()
                    val x: Int = (chunkX shl 4) + (pos shr 12 and 15)
                    val y: Int = pos and 255
                    val z: Int = (chunkZ shl 4) + (pos shr 8 and 15)
                    Record(Position(x, y, z), blockId)
                }
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeInt(chunkX)
        byteBuf.writeInt(chunkZ)
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(records.size)
                for (record in records) {
                    byteBuf.writeShort(
                            record.position.x - (chunkX shl 4) shl 12 or (record.position
                                    .z - (chunkZ shl 4) shl 8) or record.position.y
                    )
                    byteBuf.writeVarInt(record.blockId)
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeShort(records.size)
                byteBuf.writeInt(records.size * 4)
                for (record in records) {
                    byteBuf.writeShort(
                            record.position.x - (chunkX shl 4) shl 12 or (record.position
                                    .z - (chunkZ shl 4) shl 8) or record.position.y
                    )
                    byteBuf.writeShort(record.blockId)
                }
            }
        }
    }

    data class Record(
            var position: Position,
            var blockId: Int
    )
}