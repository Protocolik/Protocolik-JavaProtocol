package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.minecraft.world.notify.ClientNotification
import com.github.protocolik.api.minecraft.world.notify.ClientNotificationValue
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayClientboundGameStateChangePacket
@JvmOverloads
constructor(
        var notification: ClientNotification = ClientNotification.DEMO_MESSAGE,
        var value: ClientNotificationValue = ClientNotificationValue.BaseValue()
) : Packet(PacketType.PLAY_CLIENTBOUND_NOTIFY_CLIENT) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        notification = ClientNotification[byteBuf.readUnsignedByte()]
        value = notification[byteBuf.readFloat()]
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeByte(notification.id)
        byteBuf.writeFloat(value.value)
    }
}