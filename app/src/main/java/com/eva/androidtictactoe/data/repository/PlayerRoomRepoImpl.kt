package com.eva.androidtictactoe.data.repository

import com.eva.androidtictactoe.data.mapper.toModel
import com.eva.androidtictactoe.data.remote.dto.BaseResponseDto
import com.eva.androidtictactoe.domain.model.RoomResponseModel
import com.eva.androidtictactoe.domain.repository.PlayerRoomApiFacade
import com.eva.androidtictactoe.domain.repository.PlayerRoomRepository
import com.eva.androidtictactoe.utils.Resource
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class PlayerRoomRepoImpl(
    private val roomApi: PlayerRoomApiFacade
) : PlayerRoomRepository {
    override suspend fun createRoom(rounds: Int): Flow<Resource<RoomResponseModel>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = roomApi.createRoom(rounds)
                emit(Resource.Success(data = response.toModel()))
            } catch (e: ClientRequestException) {
                val body = e.response.body<BaseResponseDto>()
                emit(Resource.Error(message = body.detail))
            } catch (e: IOException) {
                emit(Resource.Error(message = "IO exception occurred"))
            } catch (e: Exception) {
                emit(Resource.Error(message = "Exception Occurred"))
            }
        }
    }

    override suspend fun joinRoom(roomId: String): Flow<Resource<RoomResponseModel>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = roomApi.checkRoom(roomId)
                emit(Resource.Success(data = response.toModel()))
            } catch (e: ClientRequestException) {
                val body = e.response.body<BaseResponseDto>()
                emit(Resource.Error(message = body.detail))
            } catch (e: IOException) {
                emit(Resource.Error(message = "IO exception occurred"))
            } catch (e: Exception) {
                emit(Resource.Error(message = "Exception Occurred"))
            }
        }
    }
}