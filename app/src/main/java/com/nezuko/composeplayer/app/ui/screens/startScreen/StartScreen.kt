package com.nezuko.composeplayer.app.ui.screens.startScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.ui.theme.LightBlue

const val TAG = "START_SCREEN"

@Preview
@Composable
fun StartScreen(
    onSignInCLick: () -> Unit = {},
    onSignUpCLick: () -> Unit = {},
) {
    val montserratFont = FontFamily(Font(R.font.montserrat_light))

    val signUp = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFont
            )
        ) {
            append("регистрация")
        }
    }

    val signIn = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFont
            )
        ) {
            append("войти")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                painter = painterResource(id = R.drawable.login_logo2),
                contentDescription = "лого",
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Добро пожаловать",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontFamily = FontFamily.Cursive,
                style = TextStyle(fontSize = 30.sp),
                color = LightBlue
            )
            Spacer(modifier = Modifier.padding(vertical = 50.dp))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onSignInCLick.invoke() },
                text = signIn,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = montserratFont
                ),
                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onSignUpCLick.invoke() },
                text = signUp,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = montserratFont
                ),
                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.padding(vertical = 40.dp))
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//fun SignUpScreen() {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var passwordVisible by remember { mutableStateOf(false) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF0F0F0)),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .padding(16.dp)
//                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
//                .padding(32.dp)
//        ) {
//            Text(text = "Sign up", fontSize = 24.sp, color = Color.Gray)
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Email") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    containerColor = Color.Transparent
//                )
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            OutlinedTextField(
//                value = password,
//                onValueChange = { it: String ->
//                    password = it
//                },
//                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                trailingIcon = {
//                    val image = if (passwordVisible)
//                        Icons.TwoTone.Lock
//                    else Icons.Filled.Lock
//
//                    IconButton(onClick = {
//                        passwordVisible = !passwordVisible
//                    }) {
//                        Icon(imageVector = image, contentDescription = null)
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    containerColor = Color.Transparent
//                )
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = { /* Handle sign up */ },
//                modifier = Modifier.fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
//            ) {
//                Text(text = "Sign up", color = Color.White)
//            }
//        }
//    }
//}