package com.eva.androidtictactoe.data.mapper

import com.eva.androidtictactoe.data.remote.dto.RoomSerializer
import com.eva.androidtictactoe.data.remote.dto.VerifiedRoomSerializer
import com.eva.androidtictactoe.domain.model.RoomResponseModel
import com.eva.androidtictactoe.domain.model.RoomVerificationModel

fun RoomSerializer.toModel() = RoomResponseModel(roomId = room, rounds = rounds)

fun VerifiedRoomSerializer.toModel() =
	RoomVerificationModel(roomModel = room.toModel(), message = message)