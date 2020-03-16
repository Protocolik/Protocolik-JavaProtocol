package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.minecraft.Difficulty
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayServerboundSetDifficultyPacket
@JvmOverloads
constructor(
        var difficulty: Difficulty = Difficulty.NORMAL
) : Packet(PacketType.PLAY_SERVERBOUND_SET_DIFFICULTY) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        difficulty = Difficulty[byteBuf.readByte().toInt()]
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeByte(difficulty.id)
    }
}