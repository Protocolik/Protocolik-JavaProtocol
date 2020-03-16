package com.github.protocolik.protocol.java.play.clientbound.entity

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

data class PlayClientboundEntityStatusPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var entityStatus: EntityStatus = EntityStatus.LIVING_HURT
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_STATUS) {

    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        entityId = byteBuf.readInt()
        entityStatus = EntityStatus[byteBuf.readByte()]
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeInt(entityId)
        byteBuf.writeByte(entityStatus.id)
    }

    enum class EntityStatus(val id: Int) {
        TIPPED_ARROW_EMIT_PARTICLES(0),
        RABBIT_JUMP_OR_MINECART_SPAWNER_DELAY_RESET(1),
        LIVING_HURT(2),
        LIVING_DEATH(3),
        IRON_GOLEM_ATTACK(4),
        TAMEABLE_TAMING_FAILED(6),
        TAMEABLE_TAMING_SUCCEEDED(7),
        WOLF_SHAKE_WATER(8),
        PLAYER_FINISH_USING_ITEM(9),
        SHEEP_GRAZE_OR_TNT_CART_EXPLODE(10),
        IRON_GOLEM_HOLD_POPPY(11),
        VILLAGER_MATE(12),
        VILLAGER_ANGRY(13),
        VILLAGER_HAPPY(14),
        WITCH_EMIT_PARTICLES(15),
        ZOMBIE_VILLAGER_CURE(16),
        FIREWORK_EXPLODE(17),
        ANIMAL_EMIT_HEARTS(18),
        SQUID_RESET_ROTATION(19),
        MOB_EMIT_SMOKE(20),
        GUARDIAN_MAKE_SOUND(21),
        PLAYER_ENABLE_REDUCED_DEBUG(22),
        PLAYER_DISABLE_REDUCED_DEBUG(23),
        PLAYER_OP_PERMISSION_LEVEL_0(24),
        PLAYER_OP_PERMISSION_LEVEL_1(25),
        PLAYER_OP_PERMISSION_LEVEL_2(26),
        PLAYER_OP_PERMISSION_LEVEL_3(27),
        PLAYER_OP_PERMISSION_LEVEL_4(28),
        LIVING_SHIELD_BLOCK(29),
        LIVING_SHIELD_BREAK(30),
        FISHING_HOOK_PULL_PLAYER(31),
        ARMOR_STAND_HIT(32),
        LIVING_HURT_THORNS(33),
        IRON_GOLEM_EMPTY_HAND(34),
        TOTEM_OF_UNDYING_MAKE_SOUND(35),
        LIVING_DROWN(36),
        LIVING_BURN(37),
        DOLPHIN_HAPPY(38),
        RAVAGER_STUNNED(39),
        OCELOT_TAMING_FAILED(40),
        OCELOT_TAMING_SUCCEEDED(41),
        VILLAGER_SWEAT(42),
        PLAYER_EMIT_CLOUD(43),
        LIVING_HURT_SWEET_BERRY_BUSH(44),
        FOX_EATING(45),
        LIVING_TELEPORT(46),
        LIVING_EQUIPMENT_BREAK_MAIN_HAND(47),
        LIVING_EQUIPMENT_BREAK_OFF_HAND(48),
        LIVING_EQUIPMENT_BREAK_HEAD(49),
        LIVING_EQUIPMENT_BREAK_CHEST(50),
        LIVING_EQUIPMENT_BREAK_LEGS(51),
        LIVING_EQUIPMENT_BREAK_FEET(52),
        HONEY_BLOCK_SLIDE(53),
        HONEY_BLOCK_LAND(54);

        companion object {
            val values = values()
            private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            @JvmStatic
            operator fun get(id: Number) = byId[id.toInt()] ?: error("Unknown Entity status with id: $id")
        }
    }
}