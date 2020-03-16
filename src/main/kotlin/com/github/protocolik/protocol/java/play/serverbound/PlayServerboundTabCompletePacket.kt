package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.minecraft.Position
import com.github.protocolik.api.minecraft.readPosition
import com.github.protocolik.api.minecraft.writePosition
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeString
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayServerboundTabCompletePacket
@JvmOverloads
constructor(
        var text: String = "",
        var transactionId: Int = 0,
        var assumeCommand: Boolean = true,
        var lookedAtBlock: Position? = null
) : Packet(PacketType.PLAY_SERVERBOUND_TAB_COMPLETE) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                transactionId = byteBuf.readVarInt()
                text = byteBuf.readString()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                text = byteBuf.readString()
                assumeCommand = byteBuf.readBoolean()
                lookedAtBlock = if (byteBuf.readBoolean()) {
                    byteBuf.readPosition(protocolVersion)
                } else {
                    null
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                text = byteBuf.readString()
                lookedAtBlock = if (byteBuf.readBoolean()) {
                    byteBuf.readPosition(protocolVersion)
                } else {
                    null
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                text = byteBuf.readString()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                byteBuf.writeVarInt(transactionId)
                byteBuf.writeString(text)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeString(text)
                byteBuf.writeBoolean(assumeCommand)
                byteBuf.writeBoolean(lookedAtBlock != null)
                if (lookedAtBlock != null) {
                    byteBuf.writePosition(lookedAtBlock!!, protocolVersion)
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeString(text)
                byteBuf.writeBoolean(lookedAtBlock != null)
                if (lookedAtBlock != null) {
                    byteBuf.writePosition(lookedAtBlock!!, protocolVersion)
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeString(text)
            }
        }
    }
}