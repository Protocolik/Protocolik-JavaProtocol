package com.github.protocolik.protocol.java.play.serverbound.player

import com.github.protocolik.api.minecraft.entity.player.Hand
import com.github.protocolik.api.minecraft.entity.player.InteractAction
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayServerboundPlayerInteractEntityPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var action: InteractAction = InteractAction.INTERACT,
        var targetX: Float = 0.0f,
        var targetY: Float = 0.0f,
        var targetZ: Float = 0.0f,
        var hand: Hand = Hand.MAIN_HAND
) : Packet(PacketType.PLAY_SERVERBOUND_PLAYER_INTERACT_ENTITY) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                entityId = byteBuf.readVarInt()
                action = InteractAction[byteBuf.readVarInt()]
                if (action == InteractAction.INTERACT_AT) {
                    targetX = byteBuf.readFloat()
                    targetY = byteBuf.readFloat()
                    targetZ = byteBuf.readFloat()
                }
                if (action != InteractAction.ATTACK) {
                    hand = Hand[byteBuf.readVarInt()]
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                entityId = byteBuf.readVarInt()
                action = InteractAction[byteBuf.readVarInt()]
                if (action == InteractAction.INTERACT_AT) {
                    targetX = byteBuf.readFloat()
                    targetY = byteBuf.readFloat()
                    targetZ = byteBuf.readFloat()
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                entityId = byteBuf.readInt()
                action = InteractAction[byteBuf.readByte()]
                if (action == InteractAction.INTERACT_AT) {
                    targetX = byteBuf.readFloat()
                    targetY = byteBuf.readFloat()
                    targetZ = byteBuf.readFloat()
                }
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeVarInt(action.id)
                if (action == InteractAction.INTERACT_AT) {
                    byteBuf.writeFloat(targetX)
                    byteBuf.writeFloat(targetY)
                    byteBuf.writeFloat(targetZ)
                }
                if (action != InteractAction.ATTACK) {
                    byteBuf.writeVarInt(hand.id)
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeVarInt(action.id)
                if (action == InteractAction.INTERACT_AT) {
                    byteBuf.writeFloat(targetX)
                    byteBuf.writeFloat(targetY)
                    byteBuf.writeFloat(targetZ)
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(entityId)
                byteBuf.writeByte(action.id)
                if (action == InteractAction.INTERACT_AT) {
                    byteBuf.writeFloat(targetX)
                    byteBuf.writeFloat(targetY)
                    byteBuf.writeFloat(targetZ)
                }
            }
        }
    }
}