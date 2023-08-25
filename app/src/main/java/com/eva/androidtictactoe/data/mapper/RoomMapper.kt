package com.eva.androidtictactoe.data.mapper

import com.eva.androidtictactoe.data.remote.dto.RoomSerializer
import com.eva.androidtictactoe.domain.model.RoomResponseModel

fun RoomSerializer.toModel() = RoomResponseModel(roomId = room, rounds = rounds)