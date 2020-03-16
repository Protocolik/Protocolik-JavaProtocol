package com.github.protocolik.protocol.java.play.clientbound.entity

import com.github.protocolik.api.minecraft.Attribute
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.*
import io.netty.buffer.ByteBuf

data class PlayClientboundEntityAttributesPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var attributes: List<Attribute> = emptyList()
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_ATTRIBUTES) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                entityId = byteBuf.readVarInt()
                val attributesCount = byteBuf.readInt()

                attributes = Array(attributesCount) {
                    val type = Attribute.Type[byteBuf.readString()]
                    val value = byteBuf.readDouble()
                    val modifiersCount = byteBuf.readVarInt()

                    val modifiers = Array(modifiersCount) {
                        val modifierType = Attribute.Modifier.Type[byteBuf.readUUID()]
                        val amount = byteBuf.readDouble()
                        val operation = Attribute.Modifier.Operation[byteBuf.readByte().toInt()]
                        Attribute.Modifier(modifierType, amount, operation)
                    }.toList()
                    Attribute(type, value, modifiers)
                }.toList()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                entityId = byteBuf.readInt()
                val attributesCount = byteBuf.readInt()
                attributes = Array(attributesCount) {
                    val type = Attribute.Type[byteBuf.readString()]
                    val value = byteBuf.readDouble()
                    val modifiersCount = byteBuf.readShort().toInt()
                    val modifiers = Array(modifiersCount) {
                        val modifierType = Attribute.Modifier.Type[byteBuf.readUUID()]
                        val amount = byteBuf.readDouble()
                        val operation = Attribute.Modifier.Operation[byteBuf.readByte().toInt()]
                        Attribute.Modifier(modifierType, amount, operation)
                    }.toList()
                    Attribute(type, value, modifiers)
                }.toList()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeInt(attributes.size)
                for (attribute in attributes) {
                    byteBuf.writeString(attribute.type.namespace)
                    byteBuf.writeDouble(attribute.value)
                    byteBuf.writeVarInt(attribute.modifiers.size)
                    for (modifier in attribute.modifiers) {
                        byteBuf.writeUUID(modifier.type.uuid)
                        byteBuf.writeDouble(modifier.amount)
                        byteBuf.writeByte(modifier.operation.id)
                    }
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(entityId)
                byteBuf.writeInt(attributes.size)
                for (attribute in attributes) {
                    byteBuf.writeString(attribute.type.namespace)
                    byteBuf.writeDouble(attribute.value)
                    byteBuf.writeShort(attribute.modifiers.size)
                    for (modifier in attribute.modifiers) {
                        byteBuf.writeUUID(modifier.type.uuid)
                        byteBuf.writeDouble(modifier.amount)
                        byteBuf.writeByte(modifier.operation.id)
                    }
                }
            }
        }
    }
}