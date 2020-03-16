package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayClientboundLockDifficultyPacket
@JvmOverloads
constructor(
        var locked: Boolean = true
) : Packet(PacketType.PLAY_SERVERBOUND_LOCK_DIFFICULTY) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        locked = byteBuf.readBoolean()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeBoolean(locked)
    }
}