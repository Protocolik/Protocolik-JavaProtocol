package com.github.protocolik.protocol.java.play.clientbound.player

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.getBitValue
import com.github.protocolik.api.utils.setBitValue
import io.netty.buffer.ByteBuf

data class PlayClientboundPlayerAbilitiesPacket
@JvmOverloads
constructor(
        var invincible: Boolean = false,
        var canFly: Boolean = false,
        var flying: Boolean = false,
        var creative: Boolean = false,
        var flyingSpeed: Float = 0.0f,
        var walkingSpeed: Float = 0.0f
) : Packet(PacketType.PLAY_CLIENTBOUND_PLAYER_ABILITIES) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        val flags = byteBuf.readByte().toInt()
        invincible = flags.getBitValue(0x01)
        canFly = flags.getBitValue(0x02)
        flying = flags.getBitValue(0x04)
        creative = flags.getBitValue(0x08)
        flyingSpeed = byteBuf.readFloat()
        walkingSpeed = byteBuf.readFloat()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        var flags = 0
        flags = flags.setBitValue(invincible, 0x01)
        flags = flags.setBitValue(canFly, 0x02)
        flags = flags.setBitValue(flying, 0x04)
        flags = flags.setBitValue(creative, 0x08)
        byteBuf.writeByte(flags)
        byteBuf.writeFloat(flyingSpeed)
        byteBuf.writeFloat(walkingSpeed)
    }
}