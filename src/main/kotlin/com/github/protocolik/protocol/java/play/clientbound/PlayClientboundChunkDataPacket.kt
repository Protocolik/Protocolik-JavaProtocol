package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.data.UserConnection
import com.github.protocolik.api.minecraft.world.chunk.BaseChunk
import com.github.protocolik.api.minecraft.world.chunk.Chunk
import com.github.protocolik.api.minecraft.world.chunk.readChunk
import com.github.protocolik.api.minecraft.world.chunk.writeChunk
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf
import java.util.*

data class PlayClientboundChunkDataPacket
@JvmOverloads
constructor(
        var chunk: Chunk = BaseChunk(0, 0)
) : Packet(PacketType.PLAY_CLIENTBOUND_CHUNK_DATA) {
    private var chunkVersion: ProtocolVersion = ProtocolVersion.CURRENT
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion, userConnection: UserConnection) {
        chunkVersion = protocolVersion
        chunk = byteBuf.readChunk(userConnection.dimension, protocolVersion)
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion, userConnection: UserConnection) {
        byteBuf.writeChunk(chunk, userConnection.dimension, protocolVersion)
    }

    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {}

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {}

    override fun remap(
            protocolVersion: ProtocolVersion,
            userConnection: UserConnection
    ): List<Packet> {
        val packets = LinkedList<Packet>()

        if (protocolVersion >= ProtocolVersion.RELEASE_1_13) {
            chunk.sections.forEach {
                if (it != null) {
                    for (p in 1 until it.palette.size) {
                        val oldId = it.palette[p]
//                        var blockName = BlockMapping[chunkVersion][oldId]
                        var blockName = "minecraft:stone"
//                        if (blockName == null) {
//                            System.out.println("Unknown block name for id $oldId on $chunkVersion")
//                            blockName = "minecraft:stone"
//                        }
//                        val newId = BlockMapping[protocolVersion][blockName]
                        val newId = 1
                        it.palette[p] =
                                if (newId >= 0) newId else if (protocolVersion >= ProtocolVersion.RELEASE_1_13) 1 else 16
                    }
                }
            }
            if (protocolVersion >= ProtocolVersion.RELEASE_1_14 && ProtocolVersion.CURRENT < ProtocolVersion.RELEASE_1_14) {
                packets.add(PlayClientboundUpdateViewPositionPacket(chunk.x, chunk.z))
            }
        }

        return packets.apply {
            add(this@PlayClientboundChunkDataPacket)
        }
    }
}