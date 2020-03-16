package com.github.protocolik.protocol.java.play.clientbound.entity

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.getBitValue
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.setBitValue
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundEntityEffectPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var effectId: Int = 0,
        var amplifier: Int = 0,
        var duration: Int = 0,
        var ambient: Boolean = false,
        var showParticles: Boolean = true,
        var showIcon: Boolean = true
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_EFFECT) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                entityId = byteBuf.readVarInt()
                effectId = byteBuf.readByte().toInt()
                amplifier = byteBuf.readByte().toInt()
                duration = byteBuf.readVarInt()
                val flags = byteBuf.readByte().toInt()
                ambient = flags.getBitValue(0x01)
                showParticles = flags.getBitValue(0x02)
                showIcon = flags.getBitValue(0x04)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                entityId = byteBuf.readVarInt()
                effectId = byteBuf.readByte().toInt()
                amplifier = byteBuf.readByte().toInt()
                duration = byteBuf.readVarInt()
                showParticles = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                entityId = byteBuf.readInt()
                effectId = byteBuf.readByte().toInt()
                amplifier = byteBuf.readByte().toInt()
                duration = byteBuf.readShort().toInt()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeByte(effectId)
                byteBuf.writeByte(amplifier)
                byteBuf.writeVarInt(duration)
                var flags = 0
                flags = flags.setBitValue(ambient, 0x01)
                flags = flags.setBitValue(showParticles, 0x02)
                flags = flags.setBitValue(showIcon, 0x04)
                byteBuf.writeByte(flags)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeByte(effectId)
                byteBuf.writeByte(amplifier)
                byteBuf.writeVarInt(duration)
                byteBuf.writeBoolean(showParticles)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(entityId)
                byteBuf.writeByte(effectId)
                byteBuf.writeByte(amplifier)
                byteBuf.writeShort(duration)
            }
        }
    }
}