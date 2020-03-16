package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.minecraft.Difficulty
import com.github.protocolik.api.minecraft.entity.player.GameMode
import com.github.protocolik.api.minecraft.world.Dimension
import com.github.protocolik.api.minecraft.world.WorldType
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeString
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundJoinGamePacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var gameMode: GameMode = GameMode.SURVIVAL,
        var hardcore: Boolean = false,
        var dimension: Dimension = Dimension.OVERWORLD,
        var hashedSeed: Long = 0L,
        var difficulty: Difficulty = Difficulty.NORMAL,
        var maxPlayers: Int = 20,
        var worldType: WorldType = WorldType.DEFAULT,
        var viewDistance: Int = 0,
        var reducedDebugInfo: Boolean = false,
        var enableRespawnScreen: Boolean = true
) : Packet(PacketType.PLAY_CLIENTBOUND_JOIN_GAME) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        entityId = byteBuf.readInt()
        byteBuf.readGameMode()
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_15 -> {
                dimension = Dimension[byteBuf.readInt()]
                hashedSeed = byteBuf.readLong()
                maxPlayers = byteBuf.readUnsignedByte().toInt()
                worldType = WorldType[byteBuf.readString()]
                viewDistance = byteBuf.readVarInt()
                reducedDebugInfo = byteBuf.readBoolean()
                enableRespawnScreen = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                dimension = Dimension[byteBuf.readInt()]
                maxPlayers = byteBuf.readUnsignedByte().toInt()
                worldType = WorldType[byteBuf.readString()]
                viewDistance = byteBuf.readVarInt()
                reducedDebugInfo = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_10 -> {
                dimension = Dimension[byteBuf.readInt()]
                difficulty = Difficulty[byteBuf.readUnsignedByte()]
                maxPlayers = byteBuf.readUnsignedByte().toInt()
                worldType = WorldType[byteBuf.readString()]
                reducedDebugInfo = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                dimension = Dimension[byteBuf.readByte()]
                difficulty = Difficulty[byteBuf.readUnsignedByte()]
                maxPlayers = byteBuf.readUnsignedByte().toInt()
                worldType = WorldType[byteBuf.readString()]
                reducedDebugInfo = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                dimension = Dimension[byteBuf.readByte()]
                difficulty = Difficulty[byteBuf.readUnsignedByte()]
                maxPlayers = byteBuf.readUnsignedByte().toInt()
                worldType = WorldType[byteBuf.readString()]
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeInt(entityId)
        byteBuf.writeGameMode()
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_15 -> {
                byteBuf.writeInt(dimension.id)
                byteBuf.writeLong(hashedSeed)
                byteBuf.writeByte(maxPlayers)
                byteBuf.writeString(worldType.namespace)
                byteBuf.writeVarInt(viewDistance)
                byteBuf.writeBoolean(reducedDebugInfo)
                byteBuf.writeBoolean(enableRespawnScreen)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                byteBuf.writeInt(dimension.id)
                byteBuf.writeByte(maxPlayers)
                byteBuf.writeString(worldType.namespace)
                byteBuf.writeVarInt(viewDistance)
                byteBuf.writeBoolean(reducedDebugInfo)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_10 -> {
                byteBuf.writeInt(dimension.id)
                byteBuf.writeByte(difficulty.id)
                byteBuf.writeByte(maxPlayers)
                byteBuf.writeString(worldType.namespace)
                byteBuf.writeBoolean(reducedDebugInfo)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeByte(dimension.id)
                byteBuf.writeByte(difficulty.id)
                byteBuf.writeByte(maxPlayers)
                byteBuf.writeString(worldType.namespace)
                byteBuf.writeBoolean(reducedDebugInfo)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeByte(dimension.id)
                byteBuf.writeByte(difficulty.id)
                byteBuf.writeByte(maxPlayers)
                byteBuf.writeString(worldType.namespace)
            }
        }
    }

    private fun ByteBuf.readGameMode() {
        val value = readUnsignedByte().toInt()
        hardcore = value and GAMEMODE_FLAG_HARDCORE != 0
        gameMode = GameMode[value and GAMEMODE_MASK]
    }

    private fun ByteBuf.writeGameMode() {
        var value = gameMode.id and GAMEMODE_MASK
        if (hardcore) {
            value = value or GAMEMODE_FLAG_HARDCORE
        }
        writeByte(value)
    }

    companion object {
        private const val GAMEMODE_MASK = 0x07
        private const val GAMEMODE_FLAG_HARDCORE = 0x08
    }
}