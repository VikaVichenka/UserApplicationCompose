package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.vikayarska.data.model.AppUser
import com.vikayarska.kotlinapplicationcompose.R
import com.vikayarska.kotlinapplicationcompose.presentation.activities.SimpleCircularProgressIndicator
import com.vikayarska.kotlinapplicationcompose.presentation.model.ViewState
import com.vikayarska.kotlinapplicationcompose.presentation.ui.RegularTextStyle
import com.vikayarska.kotlinapplicationcompose.presentation.ui.TitleTextStyle
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.UsersProfileViewModel

@Composable
fun UserProfileScreen(userProfileViewModel: UsersProfileViewModel) {
    val viewState = userProfileViewModel.viewState.observeAsState()
    when (val view = viewState.value) {
        is ViewState.Empty, is ViewState.Failure -> EmptyProfile()
        is ViewState.Loaded<*> -> UserProfile(user = (view.data as AppUser))
        is ViewState.Loading -> SimpleCircularProgressIndicator()
    }
}

@Composable
fun UserProfile(user: AppUser) {
    val padding = dimensionResource(id = R.dimen.regularPadding)
    val verticalPadding = dimensionResource(id = R.dimen.verticalPadding)
    Column(
        Modifier
            .padding(padding)
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {

        Image(
            painter = rememberImagePainter(
                data = user.imageUrl,
            ),
            contentDescription = "User Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally),
        )
        Text(
            user.fullName(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = verticalPadding),
            style = TitleTextStyle
        )
        Column(
            Modifier
                .fillMaxWidth()
        ) {

            Text(
                user.intro,
                modifier = Modifier.padding(top = verticalPadding),
                style = RegularTextStyle
            )
        }
    }
}

@Composable
fun EmptyProfile() {
    val padding = dimensionResource(id = R.dimen.regularPadding)
    Column(
        Modifier
            .padding(padding)
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_avatar_no_user),
            contentDescription = "Empty user",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally),
        )
        Text(
            stringResource(id = R.string.no_user_found),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = dimensionResource(id = R.dimen.verticalPadding)),
            style = TitleTextStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfile() {
    UserProfile(
        AppUser(
            0,
            "John",
            "Smith",
            stringResource(id = R.string.text_mock),
            "https://cdn2.thecatapi.com/images/wWZPyq5Jm.jpg"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewEmptyUserProfile() {
    EmptyProfile()
}

@Preview(showBackground = true)
@Composable
fun PreviewLoading() {
    SimpleCircularProgressIndicator()
}