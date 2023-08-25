package com.eva.androidtictactoe.domain.repository

import com.eva.androidtictactoe.data.remote.dto.RoomSerializer

interface PlayerRoomApiFacade {

    suspend fun createRoom(rounds: Int): RoomSerializer

    suspend fun checkRoom(roomId: String): RoomSerializer
}