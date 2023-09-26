package com.eva.androidtictactoe.presentation.screens.feature_game

import com.eva.androidtictactoe.domain.model.GameAchievementModel

/**
 * State for allowing the back press handler and showing the dialog if not allowed
 * @property showOnBackDialog Is dialog visible
 * @property isBackAllowed isBack pressed allowed
 */
data class GameBackHandlerState(
	val showOnBackDialog: Boolean = false,
	val isBackAllowed: Boolean = false,
)

/**
 * State for showing the achievement dialog
 * @property showAchievement Is achievement dialog is visible
 * @property achievement The [GameAchievementModel] itself
 */
data class GameAchievementState(
	val showAchievement: Boolean = false,
	val achievement: GameAchievementModel? = null
)
