package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.minecraft.entity.player.Hand
import com.github.protocolik.api.minecraft.setting.ChatVisibility
import com.github.protocolik.api.minecraft.setting.SkinPart
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.*
import io.netty.buffer.ByteBuf

data class PlayServerboundSettingsPacket
@JvmOverloads
constructor(
        var locale: String = "",
        var viewDistance: Int = 6,
        var chatVisibility: ChatVisibility = ChatVisibility.ENABLED,
        var chatColors: Boolean = true,
        var visibleSkinParts: List<SkinPart> = SkinPart.values.toList(),
        var mainHand: Hand = Hand.MAIN_HAND
) : Packet(PacketType.PLAY_SERVERBOUND_SETTINGS) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        locale = byteBuf.readString()
        viewDistance = byteBuf.readByte().toInt()

        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                chatVisibility = ChatVisibility[byteBuf.readVarInt()]
                chatColors = byteBuf.readBoolean()
                val flags = byteBuf.readUnsignedByte().toInt()
                visibleSkinParts = SkinPart.values.filter {
                    flags.getBitValue(it.id)
                }
                mainHand = Hand[byteBuf.readVarInt()]
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                chatVisibility = ChatVisibility[byteBuf.readByte()]
                chatColors = byteBuf.readBoolean()
                val flags = byteBuf.readUnsignedByte().toInt()
                visibleSkinParts = SkinPart.values.filter {
                    flags.getBitValue(it.id)
                }
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                chatVisibility = ChatVisibility[byteBuf.readByte()]
                chatColors = byteBuf.readBoolean()
                byteBuf.readUnsignedByte() // Difficulty (unused)
                visibleSkinParts = if (byteBuf.readBoolean()) {
                    SkinPart.values.toList()
                } else {
                    SkinPart.values.filter { it != SkinPart.CAPE }
                }
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeString(locale)
        byteBuf.writeByte(viewDistance)
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeVarInt(chatVisibility.id)
                byteBuf.writeBoolean(chatColors)
                var skinFlags = 0
                for (visibleSkinPart in visibleSkinParts) {
                    skinFlags = skinFlags.setBitValue(true, visibleSkinPart.id)
                }
                byteBuf.writeByte(skinFlags)
                byteBuf.writeVarInt(mainHand.id)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeByte(chatVisibility.id)
                byteBuf.writeBoolean(chatColors)
                var skinFlags = 0
                for (visibleSkinPart in visibleSkinParts) {
                    skinFlags = skinFlags.setBitValue(true, visibleSkinPart.id)
                }
                byteBuf.writeByte(skinFlags)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeByte(chatVisibility.id)
                byteBuf.writeBoolean(chatColors)
                byteBuf.writeByte(0)
                byteBuf.writeBoolean(visibleSkinParts.contains(SkinPart.CAPE))
            }
        }
    }
}
