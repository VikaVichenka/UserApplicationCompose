package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.vikayarska.data.model.AppUser
import com.vikayarska.kotlinapplicationcompose.R
import com.vikayarska.kotlinapplicationcompose.presentation.ui.RegularTextStyle
import com.vikayarska.kotlinapplicationcompose.presentation.ui.TitleTextStyle

@Composable
fun UserProfileScreen(user: AppUser?) {
    user?.let { UserProfile(user = user) }
}

//TODO: pass properly user as screen renders one more time when press back as user null
//TODO: How to make square(height equals width)
@Composable
fun UserProfile(user: AppUser) {
    val padding = dimensionResource(id = R.dimen.regularPadding)
    val verticalPadding = dimensionResource(id = R.dimen.verticalPadding)
    val imageSize = 200.dp
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
                .size(imageSize)
                .align(Alignment.CenterHorizontally),
        )
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Text(
                user.fullName(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = verticalPadding),
                style = TitleTextStyle
            )
            Text(
                user.intro,
                modifier = Modifier.padding(top = verticalPadding),
                style = RegularTextStyle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfile() {
    UserProfileScreen(
        AppUser(
            0,
            "John",
            "Smith",
            stringResource(id = R.string.text_mock),
            "https://cdn2.thecatapi.com/images/wWZPyq5Jm.jpg"
        )
    )
}