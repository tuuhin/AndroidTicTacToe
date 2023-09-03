package com.eva.androidtictactoe.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.getSystemService

/**
 * An utility class to save the room Id to clip board
 */
class ClipBoardSaver(
	private val context: Context
) {
	private val clipBoardManager by lazy { context.getSystemService<ClipboardManager>() }

	/**
	 * Adds the data to the clipboard
	 */
	fun addToClipBoard(data: String) {
		val clipData = ClipData.newPlainText(AppConstants.ROOM_ID_REF, data)
		clipBoardManager?.setPrimaryClip(clipData)
	}
}