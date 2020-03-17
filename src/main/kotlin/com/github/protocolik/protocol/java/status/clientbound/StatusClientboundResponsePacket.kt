package com.github.protocolik.protocol.java.status.clientbound

import com.github.protocolik.api.data.UserConnection
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.protocol.VersionInfo
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.writeString
import com.github.protocolik.mojang.api.Profile
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import io.netty.buffer.ByteBuf
import java.util.*

data class StatusClientboundResponsePacket
@JvmOverloads
constructor(
        var response: ServerStatusInfo = ServerStatusInfo()
) : Packet(PacketType.STATUS_CLIENTBOUND_RESPONSE) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        val json = JsonParser().parse(byteBuf.readString()).asJsonObject
        response = ServerStatusInfo().apply {
            val version = json.getAsJsonObject("version")
            versionInfo.displayName = version.get("name").asString
            versionInfo.id = version.get("protocol").asInt

            val players = json.getAsJsonObject("players")
            playerInfo.maxPlayers = players.get("max").asInt
            playerInfo.onlinePlayers = players.get("online").asInt
            if (players.has("sample")) {
                playerInfo.players = players.getAsJsonArray("sample").map {
                    val jsonPlayer = it.asJsonObject
                    Profile(
                            UUID.fromString(jsonPlayer.get("id").asString),
                            jsonPlayer.get("name").asString
                    )
                }
            }
            description = json.getAsJsonObject("description").asString
            if (json.has("favicon")) {
                val data: String = json.get("favicon").asString.substring(FAVICON_PREFIX.length)
                favicon = Base64.getDecoder().decode(data)
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        val response = response
        val json = JsonObject().apply {
            val version = JsonObject().apply {
                addProperty("name", response.versionInfo.displayName)
                addProperty("protocol", response.versionInfo.id)
            }
            val players = JsonObject().apply {
                addProperty("max", response.playerInfo.maxPlayers)
                addProperty("online", response.playerInfo.onlinePlayers)
                add("sample", JsonArray().apply {
                    response.playerInfo.players.map {
                        add(JsonObject().apply {
                            addProperty("name", it.name)
                            addProperty("id", it.id.toString())
                        })
                    }
                })
            }
            val description = JsonPrimitive(response.description)
            val favicon = response.favicon

            add("version", version)
            add("players", players)
            add("description", description)
            if (favicon != null) {
                addProperty(
                        "favicon",
                        "$FAVICON_PREFIX${Base64.getEncoder().encode(favicon).toString(Charsets.UTF_8)}"
                )
            }
        }
        byteBuf.writeString(json.toString())
    }

    override fun remap(
            protocolVersion: ProtocolVersion,
            userConnection: UserConnection
    ): List<Packet> {
        val response = response.copy().apply {
            versionInfo = VersionInfo(protocolVersion.id, "Protocolik")
        }
        val packet = StatusClientboundResponsePacket()
        packet.response = response
        return listOf(packet)
    }

    data class ServerStatusInfo(
            var versionInfo: VersionInfo = ProtocolVersion.CURRENT.versionInfo,
            var playerInfo: PlayerInfo = PlayerInfo(100, 0, emptyList()),
            var description: String = "Protocolik",
            var favicon: ByteArray? = null
    )

    data class PlayerInfo(
            var maxPlayers: Int,
            var onlinePlayers: Int,
            var players: List<Profile>
    )

    companion object {
        val FAVICON_PREFIX = "data:image/png;base64,"
    }
}