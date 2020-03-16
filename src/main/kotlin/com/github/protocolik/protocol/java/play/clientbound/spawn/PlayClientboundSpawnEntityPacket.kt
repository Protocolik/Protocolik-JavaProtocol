package com.github.protocolik.protocol.java.play.clientbound.spawn

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readUUID
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeUUID
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import java.util.*

data class PlayClientboundSpawnEntityPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var uuid: UUID = UUID(0, 0),
        var typeId: Int = 0,
        var data: Int = 0,
        var x: Double = 0.0,
        var y: Double = 0.0,
        var z: Double = 0.0,
        var yaw: Float = 0.0f,
        var pitch: Float = 0.0f,
        var velocityX: Double = 0.0,
        var velocityY: Double = 0.0,
        var velocityZ: Double = 0.0
) : Packet(PacketType.PLAY_CLIENTBOUND_SPAWN_ENTITY) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        entityId = byteBuf.readVarInt()
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                uuid = byteBuf.readUUID()
                typeId = byteBuf.readVarInt()
                x = byteBuf.readDouble()
                y = byteBuf.readDouble()
                z = byteBuf.readDouble()
                pitch = byteBuf.readByte() * 360 / 256f
                yaw = byteBuf.readByte() * 360 / 256f
                data = byteBuf.readInt()
                velocityX = byteBuf.readShort() / 8000.0
                velocityY = byteBuf.readShort() / 8000.0
                velocityZ = byteBuf.readShort() / 8000.0
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                uuid = byteBuf.readUUID()
                typeId = byteBuf.readByte().toInt()
                x = byteBuf.readDouble()
                y = byteBuf.readDouble()
                z = byteBuf.readDouble()
                pitch = byteBuf.readByte() * 360 / 256f
                yaw = byteBuf.readByte() * 360 / 256f
                data = byteBuf.readInt()
                velocityX = byteBuf.readShort() / 8000.0
                velocityY = byteBuf.readShort() / 8000.0
                velocityZ = byteBuf.readShort() / 8000.0
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                typeId = byteBuf.readByte().toInt()
                x = byteBuf.readInt() / 32.0
                y = byteBuf.readInt() / 32.0
                z = byteBuf.readInt() / 32.0
                pitch = byteBuf.readByte() * 360 / 256f
                yaw = byteBuf.readByte() * 360 / 256f
                data = byteBuf.readInt()
                if (data != 0) {
                    velocityX = byteBuf.readShort() / 8000.0
                    velocityY = byteBuf.readShort() / 8000.0
                    velocityZ = byteBuf.readShort() / 8000.0
                }
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(entityId)
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_14 -> {
                byteBuf.writeUUID(uuid)
                byteBuf.writeVarInt(typeId)
                byteBuf.writeDouble(x)
                byteBuf.writeDouble(y)
                byteBuf.writeDouble(z)
                byteBuf.writeByte((pitch * 256 / 360).toInt())
                byteBuf.writeByte((yaw * 256 / 360).toInt())
                byteBuf.writeInt(data)
                byteBuf.writeShort((velocityX * 8000).toInt())
                byteBuf.writeShort((velocityY * 8000).toInt())
                byteBuf.writeShort((velocityZ * 8000).toInt())
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeUUID(uuid)
                byteBuf.writeByte(typeId)
                byteBuf.writeDouble(x)
                byteBuf.writeDouble(y)
                byteBuf.writeDouble(z)
                byteBuf.writeByte((pitch * 256 / 360).toInt())
                byteBuf.writeByte((yaw * 256 / 360).toInt())
                byteBuf.writeInt(data)
                byteBuf.writeShort((velocityX * 8000).toInt())
                byteBuf.writeShort((velocityY * 8000).toInt())
                byteBuf.writeShort((velocityZ * 8000).toInt())
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeByte(typeId)
                byteBuf.writeInt((x * 32).toInt())
                byteBuf.writeInt((y * 32).toInt())
                byteBuf.writeInt((z * 32).toInt())
                byteBuf.writeByte((pitch * 256 / 360).toInt())
                byteBuf.writeByte((yaw * 256 / 360).toInt())
                byteBuf.writeInt(data)
                if (data != 0) {
                    byteBuf.writeShort((velocityX * 8000).toInt())
                    byteBuf.writeShort((velocityY * 8000).toInt())
                    byteBuf.writeShort((velocityZ * 8000).toInt())
                }
            }
        }
    }

    enum class EntityType(val id: Int) {
        AREA_EFFECT_CLOUD(0),
        ARMOR_STAND(1),
        ARROW(2),
        BOAT(6),
        DRAGON_FIREBALL(15),
        ENDER_CRYSTAL(18),
        EVOKER_FANGS(22),
        EXPERIENCE_ORB(24),
        ENDER_SIGNAL(25),
        FALLING_BLOCK(26),
        FIREWORK(27),
        DROPPED_ITEM(35),
        ITEM_FRAME(36),
        FIREBALL(37),
        LEASH_HITCH(38),
        LLAMA_SPIT(40),
        MINECART(42),
        MINECART_CHEST(43),
        MINECART_COMMAND(44),
        MINECART_FURNACE(45),
        MINECART_HOPPER(46),
        MINECART_MOB_SPAWNER(47),
        MINECART_TNT(48),
        PRIMED_TNT(59),
        SHULKER_BULLET(64),
        SMALL_FIREBALL(69),
        SNOWBALL(71),
        SPECTRAL_ARROW(72),
        EGG(79),
        ENDER_PEARL(80),
        THROWN_EXP_BOTTLE(81),
        SPLASH_POTION(82),
        TRIDENT(83),
        WITHER_SKULL(93),
        FISHING_HOOK(102);

        fun write(byteBuf: ByteBuf) {
            byteBuf.writeVarInt(id)
        }

        companion object {
            private val byId = values().map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            operator fun get(id: Int) = byId[id]
        }
    }

    interface EntityData {
        val value: Int
    }

    class GenericEntityData(override val value: Int) : EntityData {
        companion object {
            fun read(byteBuf: ByteBuf): GenericEntityData = GenericEntityData(byteBuf.readInt())
        }
    }

    enum class ItemFrameDirection(val id: Int) : EntityData {
        DOWN(0),
        UP(1),
        NORTH(2),
        SOUTH(3),
        WEST(4),
        EAST(5);

        override val value: Int
            get() = id

        companion object {
            val byId = values().map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            operator fun get(id: Int) = byId[id]

            fun read(byteBuf: ByteBuf) = get(byteBuf.readInt())
        }
    }

    enum class MinecartType(
            val id: Int
    ) : EntityData {
        NORMAL(0),
        CHEST(1),
        POWERED(2),
        TNT(3),
        MOB_SPAWNER(4),
        HOPPER(5),
        COMMAND_BLOCK(6);

        override val value: Int
            get() = id

        companion object {
            fun read(byteBuf: ByteBuf): MinecartType {
                val id = byteBuf.readInt()
                return values().find { it.id == id }!!
            }
        }
    }

    class FallingBlockData(
            val id: Int,
            val metadata: Int
    ) : EntityData {
        override val value: Int
            get() = id or metadata shl 16

        companion object {
            fun read(byteBuf: ByteBuf): FallingBlockData {
                val value = byteBuf.readInt()
                return FallingBlockData(value and 65535, value shr 16)
            }
        }
    }

    class PotionData(override val value: Int) : EntityData {
        companion object {
            fun read(byteBuf: ByteBuf): PotionData = PotionData(byteBuf.readInt())
        }
    }

    class ProjectileData(val ownerId: Int) : EntityData {
        override val value: Int
            get() = ownerId

        companion object {
            fun read(byteBuf: ByteBuf): ProjectileData = ProjectileData(byteBuf.readInt())
        }
    }
}