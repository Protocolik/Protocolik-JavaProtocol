package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeString
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundDeclareTagsPacket
@JvmOverloads
constructor(
        var blockTags: Map<String, IntArray> = emptyMap(),
        var itemTags: Map<String, IntArray> = emptyMap(),
        var fluidTags: Map<String, IntArray> = emptyMap(),
        var entityTags: Map<String, IntArray> = emptyMap()
) : Packet(PacketType.PLAY_CLIENTBOUND_DECLARE_TAGS) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        blockTags = byteBuf.readTags()
        itemTags = byteBuf.readTags()
        fluidTags = byteBuf.readTags()
        entityTags = byteBuf.readTags()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeTags(blockTags)
        byteBuf.writeTags(itemTags)
        byteBuf.writeTags(fluidTags)
        byteBuf.writeTags(entityTags)
    }

    fun ByteBuf.readTags(): HashMap<String, IntArray> {
        val tags = HashMap<String, IntArray>()
        val tagsCount = readVarInt()
        for (i in 0..tagsCount) {
            val name = readString()
            val entriesCount = readVarInt()
            val entries = IntArray(entriesCount) { readVarInt() }
            tags[name] = entries
        }
        return tags
    }

    fun ByteBuf.writeTags(tags: Map<String, IntArray>) {
        writeVarInt(tags.size)
        tags.forEach { name, entries ->
            writeString(name)
            writeVarInt(entries.size)
            entries.forEach { writeVarInt(it) }
        }
    }
}