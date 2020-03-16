package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.minecraft.Position
import com.github.protocolik.api.minecraft.readPosition
import com.github.protocolik.api.minecraft.writePosition
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.nbt.NBT
import com.github.protocolik.nbt.io.readNBT
import com.github.protocolik.nbt.io.writeNBT
import io.netty.buffer.ByteBuf

data class PlayClientboundTileEntityData
@JvmOverloads
constructor(
        var position: Position = Position(),
        var actionId: Int = 0,
        var nbt: NBT = NBT()
) : Packet(PacketType.PLAY_CLIENTBOUND_TILE_ENTITY_DATA) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        position = byteBuf.readPosition(protocolVersion)
        actionId = byteBuf.readUnsignedByte().toInt()
        nbt = byteBuf.readNBT()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writePosition(position, protocolVersion)
        byteBuf.writeByte(actionId)
        byteBuf.writeNBT(nbt)
    }
}