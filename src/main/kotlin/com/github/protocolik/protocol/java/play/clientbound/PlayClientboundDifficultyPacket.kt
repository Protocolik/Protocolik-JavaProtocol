package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.minecraft.Difficulty
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayClientboundDifficultyPacket
@JvmOverloads
constructor(
        var difficulty: Difficulty = Difficulty.NORMAL,
        var locked: Boolean = true
) : Packet(PacketType.PLAY_CLIENTBOUND_DIFFICULTY) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                difficulty = Difficulty[byteBuf.readUnsignedByte()]
                locked = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                difficulty = Difficulty[byteBuf.readUnsignedByte().toInt()]
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                byteBuf.writeByte(difficulty.id)
                byteBuf.writeBoolean(locked)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeByte(difficulty.id)
            }
        }
    }
}