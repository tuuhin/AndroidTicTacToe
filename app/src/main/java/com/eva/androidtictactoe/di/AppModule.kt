package com.eva.androidtictactoe.di

import com.eva.androidtictactoe.data.local.UserPreferences
import com.eva.androidtictactoe.data.local.UserPreferencesFacade
import com.eva.androidtictactoe.data.remote.KtorClientOkHttp
import com.eva.androidtictactoe.data.remote.PlayerRoomApiImpl
import com.eva.androidtictactoe.data.repository.PlayerRoomRepoImpl
import com.eva.androidtictactoe.domain.repository.PlayerRoomApiFacade
import com.eva.androidtictactoe.domain.repository.PlayerRoomRepository
import com.eva.androidtictactoe.presentation.screens.PlayerRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single { KtorClientOkHttp.getInstance() }

    factoryOf(::UserPreferences) bind UserPreferencesFacade::class

    singleOf(::PlayerRoomApiImpl) bind PlayerRoomApiFacade::class

    factoryOf(::PlayerRoomRepoImpl) bind PlayerRoomRepository::class

    viewModelOf(::PlayerRoomViewModel)
}