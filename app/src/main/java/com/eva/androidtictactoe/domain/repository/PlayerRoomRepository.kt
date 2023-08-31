package com.eva.androidtictactoe.domain.repository

import com.eva.androidtictactoe.domain.model.RoomResponseModel
import com.eva.androidtictactoe.domain.model.RoomVerificationModel
import com.eva.androidtictactoe.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PlayerRoomRepository {

	suspend fun createRoom(rounds: Int): Flow<Resource<RoomResponseModel>>
	suspend fun joinRoom(roomId: String): Flow<Resource<RoomVerificationModel>>
}