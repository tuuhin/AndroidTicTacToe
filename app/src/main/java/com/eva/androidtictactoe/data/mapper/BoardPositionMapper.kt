package com.eva.androidtictactoe.data.mapper

import com.eva.androidtictactoe.data.remote.dto.BoardPositionDto
import com.eva.androidtictactoe.domain.model.BoardPosition

fun BoardPositionDto.toModel() = BoardPosition(x = x, y = y)

fun BoardPosition.toDto() = BoardPositionDto(x = x, y = y)