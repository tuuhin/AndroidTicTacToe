package com.eva.androidtictactoe.presentation.screens.feature_room

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(
    userName: String,
    onUserNameChange: (String) -> Unit,
    onRoomJoin: () -> Unit,
    onCreateRoom: () -> Unit,
    modifier: Modifier = Modifier,
    navigation: (@Composable () -> Unit)? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.game_name),
                        modifier = Modifier.padding(vertical = 12.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = navigation ?: {},
                colors = topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { scPadding ->
        Column(
            modifier = modifier
                .padding(scPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.game_extra_text),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_tic_tac_toe),
                    contentDescription = "Tic Tac Toe Game",
                    colorFilter = ColorFilter
                        .tint(MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            OutlinedTextField(
                value = userName,
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = onUserNameChange,
                label = { Text(text = "User Name") },
                placeholder = { Text(text = "Player1") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Person's Icon"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Ascii
                ),
                maxLines = 1,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onRoomJoin,
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(
                        minHeight = dimensionResource(id = R.dimen.button_height)
                    ),
                elevation = ButtonDefaults
                    .elevatedButtonElevation(defaultElevation = 2.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Join Now",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            FilledTonalButton(
                onClick = onCreateRoom,
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(
                        minHeight = dimensionResource(id = R.dimen.button_height)
                    ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Create a Room",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Preview
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(
        userName = "",
        onUserNameChange = {},
        onRoomJoin = {},
        onCreateRoom = {}
    )
}