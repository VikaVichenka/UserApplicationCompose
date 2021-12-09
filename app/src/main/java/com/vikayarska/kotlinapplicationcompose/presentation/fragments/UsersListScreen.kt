package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.vikayarska.data.model.AppUser
import com.vikayarska.kotlinapplicationcompose.R
import com.vikayarska.kotlinapplicationcompose.presentation.Routes
import com.vikayarska.kotlinapplicationcompose.presentation.USER
import com.vikayarska.kotlinapplicationcompose.presentation.ui.RegularTextStyle
import com.vikayarska.kotlinapplicationcompose.presentation.ui.TitleTextStyle
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.UsersListViewModel
import kotlinx.coroutines.flow.Flow

//TODO: add loading and view model calls

@Composable
fun UsersListScreen(
    navController: NavController,
    userListViewModel: UsersListViewModel = hiltViewModel()
) {
    UsersList(flow = userListViewModel.usersFlow) { user ->
        navController.currentBackStackEntry?.arguments?.putSerializable(USER, user)
        navController.navigate(Routes.UserProfile.route)
    }
}

@Composable
fun UsersList(flow: Flow<PagingData<AppUser>>, onClick: (AppUser) -> Unit) {
    val lazyPagingItems = flow.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyPagingItems) { user ->
            user?.let {
                UserCard(it, onClick)
                Divider(
                    modifier = Modifier.fillParentMaxWidth(),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
        }
    }
}


@Composable
fun UserCard(user: AppUser, onClick: (AppUser) -> Unit) {
    val padding = dimensionResource(id = R.dimen.regularPadding)
    val imageSize = 80.dp
    Row(
        Modifier
            .clickable(onClick = { onClick(user) })
            .padding(padding)
            .height(imageSize)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Image(painter = rememberImagePainter(
            data = user.imageUrl,
            builder = {
                transformations(CircleCropTransformation())
            }
        ), contentDescription = "User Image",
            modifier = Modifier
                .requiredSize(imageSize))
        Spacer(
            modifier = Modifier
                .width(padding)
                .fillMaxHeight()
        )
        Column(verticalArrangement = Arrangement.Center) {
            Text(user.fullName(), style = TitleTextStyle)
            Text(
                user.intro, maxLines = 2,
                overflow = TextOverflow.Ellipsis, style = RegularTextStyle
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewUserList() {
    Column() {
        UserCard(
            AppUser(
                0,
                "John",
                "Smith",
                stringResource(id = R.string.text_mock),
                "https://cdn2.thecatapi.com/images/wWZPyq5Jm.jpg"
            )
        ) {}
        UserCard(
            AppUser(
                0,
                "Alex",
                "Stone",
                stringResource(id = R.string.text_mock),
                "https://cdn2.thecatapi.com/images/wWZPyq5Jm.jpg"
            )
        ) {}
    }
}