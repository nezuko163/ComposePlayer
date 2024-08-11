package com.nezuko.composeplayer.app.ui.screens.startScreen.registerScreen.registerProfileScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nezuko.composeplayer.app.ui.screens.startScreen.loginScreen.LoginViewModel
import com.nezuko.composeplayer.app.ui.screens.startScreen.registerScreen.RegisterViewModel
import com.nezuko.composeplayer.app.ui.screens.startScreen.registerScreen.getRegisterViewModel
import com.nezuko.composeplayer.ui.theme.Aqua
import com.nezuko.domain.model.UserProfile
import org.koin.androidx.compose.koinViewModel

private val TAG = "REGISTER_PROFILE_SCREEN"

@Preview
@Composable
fun RegisterProfileScreen(
    onAuthComplete: (String) -> Unit = {},
    registerViewModel: RegisterViewModel = getRegisterViewModel()
) {
    var userName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = if (userName != "") userName else "",
            onValueChange = {
                userName = it
            },
            keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text
            ),
            label = { Text("Псевдоним") },
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                registerViewModel.registerViaPasswordAndEmail {
                    if (!it.isNullOrEmpty()) {
                        val userProfile = UserProfile(
                            id = it,
                            userName = userName,
                        )
                        registerViewModel.registerProfile(
                            userProfile,
                            onSuccess = {},
                            onFailure = { Log.e(TAG, "RegisterProfileScreen: fail", ) }
                        )
                        onAuthComplete.invoke(it)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Aqua),
            shape = RoundedCornerShape(100.dp)
        ) {
            Text(text = "регистрация", color = Color.White)
        }

    }
}