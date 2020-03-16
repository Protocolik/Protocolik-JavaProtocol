package com.github.protocolik.protocol.java.play.serverbound.window

import com.github.protocolik.api.minecraft.ItemStack
import com.github.protocolik.api.minecraft.container.ContainerAction
import com.github.protocolik.api.minecraft.container.param.*
import com.github.protocolik.api.minecraft.readItemStack
import com.github.protocolik.api.minecraft.writeItemStack
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayServerboundWindowClickPacket
@JvmOverloads
constructor(
        var containerId: Int = 0,
        var actionId: Int = 0,
        var slot: Int = 0,
        var clickedItem: ItemStack? = null,
        var action: ContainerAction = ContainerAction.CLICK_ITEM,
        var param: ContainerActionParam = BaseContainerActionParam()
) : Packet(PacketType.PLAY_SERVERBOUND_WINDOW_CLICK) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        containerId = byteBuf.readUnsignedByte().toInt()
        slot = byteBuf.readShort().toInt()
        val paramValue = byteBuf.readByte().toInt()
        actionId = byteBuf.readShort().toInt()
        action = ContainerAction[byteBuf.readByte()]
        clickedItem = byteBuf.readItemStack()
        param = when (action) {
            ContainerAction.CLICK_ITEM -> ClickItemContainerActionParam[paramValue]
            ContainerAction.SHIFT_CLICK_ITEM -> ClickItemContainerActionParam[paramValue]
            ContainerAction.CREATIVE_GRAB_MAX_STACK -> CreativeGrabContainerActionParam[paramValue]
            ContainerAction.DROP_ITEM -> DropItemContainerActionParam[paramValue + (if (slot != -999) 2 else 0)]
            ContainerAction.SPREAD_ITEM -> SpreadItemContainerActionParam[paramValue]
            ContainerAction.FILL_STACK -> FillStackContainerActionParam[paramValue]
            else -> BaseContainerActionParam(0)
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeByte(containerId)
        byteBuf.writeShort(slot)
        var paramValue = param.value
        if (action == ContainerAction.DROP_ITEM) {
            paramValue %= 2
        }
        byteBuf.writeByte(paramValue)
        byteBuf.writeShort(actionId)
        byteBuf.writeByte(action.id)
        byteBuf.writeItemStack(clickedItem)
    }
}