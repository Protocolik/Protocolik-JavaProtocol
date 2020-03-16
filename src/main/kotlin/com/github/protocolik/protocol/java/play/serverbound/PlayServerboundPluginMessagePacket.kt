package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.data.UserConnection
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readByteArray
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.writeByteArray
import com.github.protocolik.api.utils.writeString
import io.netty.buffer.ByteBuf

data class PlayServerboundPluginMessagePacket
@JvmOverloads
constructor(
        var channel: String = "",
        var data: ByteArray = byteArrayOf()
) : Packet(PacketType.PLAY_SERVERBOUND_PLUGIN_MESSAGE) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        channel = byteBuf.readString()
        data = byteBuf.readByteArray()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeString(channel)
        byteBuf.writeByteArray(data)
    }

    override fun remap(
            protocolVersion: ProtocolVersion,
            userConnection: UserConnection
    ): List<Packet> {
        val newChannel = if (protocolVersion >= ProtocolVersion.RELEASE_1_13) {
            when (channel) {
                "MC|Brand" -> "minecraft:brand"
                else -> channel
            }
        } else {
            channel
        }
        val newData = if (newChannel == "minecraft:brand") {
            "${data.toString(Charsets.UTF_8)} [Protocolik]".toByteArray()
        } else data
        return listOf(PlayServerboundPluginMessagePacket(newChannel, newData))
    }
}