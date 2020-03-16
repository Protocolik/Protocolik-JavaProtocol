package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.*
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import java.util.*

data class PlayClientboundBossBarPacket
@JvmOverloads
constructor(
        var entityUUID: UUID = UUID(0, 0),
        var action: Action = Action.ADD,
        var title: String = "",
        var health: Float = 0.0f,
        var color: Color = Color.PINK,
        var division: Division = Division.NONE,
        var darkenSky: Boolean = false,
        var playEndMusic: Boolean = false,
        var showFog: Boolean = false
) : Packet(PacketType.PLAY_CLIENTBOUND_BOSS_BAR) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        entityUUID = byteBuf.readUUID()
        action = Action[byteBuf.readVarInt()]
        when (action) {
            Action.ADD -> {
                title = byteBuf.readString()
                health = byteBuf.readFloat()
                color = Color[byteBuf.readVarInt()]
                division = Division[byteBuf.readVarInt()]
                byteBuf.readFlags()
            }
            Action.REMOVE -> {
            }
            Action.UPDATE_HEALTH -> {
                health = byteBuf.readFloat()
            }
            Action.UPDATE_TITLE -> {
                title = byteBuf.readString()
            }
            Action.UPDATE_STYLE -> {
                color = Color[byteBuf.readVarInt()]
                division = Division[byteBuf.readVarInt()]
            }
            Action.UPDATE_FLAGS -> {
                byteBuf.readFlags()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeUUID(entityUUID)
        byteBuf.writeVarInt(action.id)
        when (action) {
            Action.ADD -> {
                byteBuf.writeString(title)
                byteBuf.writeFloat(health)
                byteBuf.writeVarInt(color.id)
                byteBuf.writeVarInt(division.id)
                byteBuf.writeFlags()
            }
            Action.REMOVE -> {
            }
            Action.UPDATE_HEALTH -> {
                byteBuf.writeFloat(health)
            }
            Action.UPDATE_TITLE -> {
                byteBuf.writeString(title)
            }
            Action.UPDATE_STYLE -> {
                byteBuf.writeVarInt(color.id)
                byteBuf.writeVarInt(division.id)
            }
            Action.UPDATE_FLAGS -> {
                byteBuf.writeFlags()
            }
        }
    }

    private fun ByteBuf.readFlags() {
        val flags = readUnsignedByte().toInt()
        darkenSky = flags.getBitValue(0x01)
        playEndMusic = flags.getBitValue(0x02)
        showFog = flags.getBitValue(0x04)
    }

    private fun ByteBuf.writeFlags() {
        var flags = 0
        flags = flags.setBitValue(darkenSky, 0x01)
        flags = flags.setBitValue(playEndMusic, 0x02)
        flags = flags.setBitValue(showFog, 0x04)
        writeByte(flags)
    }

    enum class Action(val id: Int) {
        ADD(0),
        REMOVE(1),
        UPDATE_HEALTH(2),
        UPDATE_TITLE(3),
        UPDATE_STYLE(4),
        UPDATE_FLAGS(5);

        companion object {
            val values = values()
            private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            @JvmStatic
            operator fun get(id: Int) = byId[id]
        }
    }

    enum class Color(val id: Int) {
        PINK(0),
        CYAN(1),
        RED(2),
        LIME(3),
        YELLOW(4),
        PURPLE(5),
        WHITE(6);

        companion object {
            val values = values()
            private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            @JvmStatic
            operator fun get(id: Int) = byId[id]
        }
    }

    enum class Division(val id: Int) {
        NONE(0),
        NOTCHES_6(1),
        NOTCHES_10(2),
        NOTCHES_12(3),
        NOTCHES_20(4);

        companion object {
            val values = values()
            private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            @JvmStatic
            operator fun get(id: Int) = byId[id]
        }
    }
}