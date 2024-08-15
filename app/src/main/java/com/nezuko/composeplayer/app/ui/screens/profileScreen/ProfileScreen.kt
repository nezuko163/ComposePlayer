package com.nezuko.composeplayer.app.ui.screens.profileScreen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.getUserViewModel
import com.nezuko.composeplayer.app.utils.ImageFromFirebaseStorage
import com.nezuko.composeplayer.app.utils.galleryLauncher
import com.nezuko.composeplayer.app.utils.selectImage
import com.nezuko.domain.model.UserProfile
import org.koin.androidx.compose.koinViewModel

private val TAG = "PROFILE_SCREEN"

@Composable
fun Profile(
    uid: String,
    userViewModel: UserViewModel = getUserViewModel(),
    profileViewModel: ProfileViewModel = getProfileViewModel(),
    onNaviagetToCropImageScreen: (uri: Uri) -> Unit
) {
    val user by profileViewModel.user.observeAsState()
    val uri by profileViewModel.uri.observeAsState()

    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var isOtherUser by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val onBackPressedDispatcher = (context as ComponentActivity).onBackPressedDispatcher
    val onBackPressed = { onBackPressedDispatcher.onBackPressed() }

    LaunchedEffect(uri) {
        if (user == null || uri == null || uri.toString().isEmpty()) return@LaunchedEffect
        if (user!!.artUrl == uri.toString()) return@LaunchedEffect
        Log.i(TAG, "Profile: uri = $uri")
        userViewModel.setAvatarToUser(uri!!, onSuccess = {
            Toast.makeText(context, "аватарка изменена", Toast.LENGTH_SHORT).show()
        })
        profileViewModel.updateImage(null)
    }

    LaunchedEffect(uid) {
        if (uid == userViewModel.uid.value) {
            // профиль этого пользователя
            profileViewModel.updateUser(userViewModel.userProfile.value!!)
            loading = false
            isOtherUser = false
        } else {
            // профиль другого пользователя
            userViewModel.getUserProfileById(
                uid = uid,
                onSuccess = { userProfile: UserProfile ->
                    profileViewModel.updateUser(userProfile)
                    loading = false
                    isOtherUser = true
                },
                onFailure = {
                    loading = false
                    errorMessage = "нет такого юзера"
                    Log.e(TAG, "Profile: неа нихуя")
                }
            )
        }

    }


    var onAvatarClick = {}
    var onBackGroundImageClick = {}

    when {
        loading -> {
            Text(text = "загрузка")
        }

        errorMessage != "" -> {
            Text(text = "ошибка: $errorMessage")
        }

        (user != null && user?.userName != "") -> {
            if (!isOtherUser) {
                Log.i(TAG, "Profile: aue")
                val launcher = galleryLauncher { uri ->
                    if (uri == null) return@galleryLauncher
                    onNaviagetToCropImageScreen(uri)
                }
                onAvatarClick = {
                    Log.i(TAG, "Profile: click")
                    selectImage(launcher)
                }
            }

            ProfileScreen(
                user = user!!,
                onBackPressed = onBackPressed,
                onDotsClick = {},
                onAvatarClick = onAvatarClick,
                onBackGroundImageClick = onBackGroundImageClick
            )
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ProfileScreen(
    user: UserProfile = UserProfile(),
    onBackPressed: () -> Unit = {},
    onDotsClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onBackGroundImageClick: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Log.i(TAG, "ProfileScreen: ${user.artUrl}")

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = user.userName,
                        modifier = Modifier
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onDotsClick) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "больше"
                        )
                    }
                }

            )
        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            Box(modifier = Modifier) {
                val backgroundImageHeight = 120.dp

                if (user.backgroundArtUrl.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(backgroundImageHeight)
                            .background(Color.Black)
                            .clickable { onBackGroundImageClick() }
                    )
                } else {
                    AsyncImage(
                        model = user.backgroundArtUrl,
                        error = painterResource(id = R.drawable.left),
                        contentDescription = "бекграунд имага",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(backgroundImageHeight)
                            .clickable { onBackGroundImageClick() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(start = 20.dp)
                    .border(2.dp, Color.Black)
                    .clip(CircleShape)
            ) {
//                AsyncImage(
//                    model = user.artUrl,
//                    contentDescription = "ава",
//                    error = painterResource(id = R.drawable.left),
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clickable { onAvatarClick() }
//                )

                ImageFromFirebaseStorage(
                    imageUrl = user.artUrl,
                    errorImageResource = R.drawable.left,
                    contentDescription = "ава",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onAvatarClick() }
                )
            }

        }

//    CollapsibleScaffold(
//        state = scrollState,
//        topBar = {
//            TopBar(
//                modifier = Modifier,
//
//                onBack = { onBackPressed() },
//                actions = {
//                    IconButton(onClick = onDotsClick) {
//                        Icon(
//                            imageVector = Icons.Default.MoreVert,
//                            contentDescription = "ещё"
//                        )
//                    }
//                }
//            ) {
//                Text("ауе жизнь ворам")
//            }
//        }
//    ) { paddingValues: PaddingValues ->
//        Column(
//            modifier = Modifier
//                .verticalScroll(scrollState)
//        ) {
//            Spacer(modifier = Modifier.padding(paddingValues))
//            repeat(20) { index: Int ->
//                Text(
//                    text = "$index",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(40.dp)
//                        .clickable {
//
//                        }
//                )
//            }
//        }
//    }
    }
}