package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.minecraft.Difficulty
import com.github.protocolik.api.minecraft.entity.player.GameMode
import com.github.protocolik.api.minecraft.world.Dimension
import com.github.protocolik.api.minecraft.world.WorldType
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.writeString
import io.netty.buffer.ByteBuf

data class PlayClientboundRespawnPacket
@JvmOverloads
constructor(
        var dimension: Int = Dimension.OVERWORLD.id,
        var difficulty: Int = Difficulty.NORMAL.id,
        var hashedSeed: Long = 0L,
        var gamemode: Int = GameMode.SURVIVAL.id,
        var worldType: String = WorldType.DEFAULT.namespace
) : Packet(PacketType.PLAY_CLIENTBOUND_RESPAWN) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_15 -> {
                dimension = byteBuf.readInt()
                hashedSeed = byteBuf.readLong()
                gamemode = byteBuf.readUnsignedByte().toInt()
                worldType = byteBuf.readString()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                dimension = byteBuf.readInt()
                gamemode = byteBuf.readUnsignedByte().toInt()
                worldType = byteBuf.readString()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                dimension = byteBuf.readInt()
                difficulty = byteBuf.readUnsignedByte().toInt()
                gamemode = byteBuf.readUnsignedByte().toInt()
                worldType = byteBuf.readString()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_15 -> {
                byteBuf.writeInt(dimension)
                byteBuf.writeLong(hashedSeed)
                byteBuf.writeByte(gamemode)
                byteBuf.writeString(worldType)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                byteBuf.writeInt(dimension)
                byteBuf.writeByte(gamemode)
                byteBuf.writeString(worldType)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(dimension)
                byteBuf.writeByte(difficulty)
                byteBuf.writeByte(gamemode)
                byteBuf.writeString(worldType)
            }
        }
    }
}