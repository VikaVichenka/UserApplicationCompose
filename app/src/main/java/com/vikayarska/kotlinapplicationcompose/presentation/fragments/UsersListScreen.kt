package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.vikayarska.data.model.AppUser
import com.vikayarska.kotlinapplicationcompose.R
import com.vikayarska.kotlinapplicationcompose.presentation.Routes
import com.vikayarska.kotlinapplicationcompose.presentation.model.ViewStateUpdate
import com.vikayarska.kotlinapplicationcompose.presentation.ui.RegularTextStyle
import com.vikayarska.kotlinapplicationcompose.presentation.ui.TitleTextStyle
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.UsersListViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun UsersListScreen(
    navController: NavController,
    userListViewModel: UsersListViewModel = hiltViewModel()
) {
    val onUserClick: (AppUser) -> Unit = { user ->
        navController.navigate(Routes.UserProfile.route(user.id))
    }
    val viewState = userListViewModel.viewState.observeAsState()
    when (viewState.value) {
        is ViewStateUpdate.Completed, is ViewStateUpdate.Loading -> UsersList(
            userListViewModel = userListViewModel,
            onClick = onUserClick
        )
        is ViewStateUpdate.Failure -> UserListError()
    }
}

//TODO: fix loaded list do not scroll to top
@Composable
fun UsersList(
    userListViewModel: UsersListViewModel,
    onClick: (AppUser) -> Unit
) {
    val viewState = userListViewModel.viewState.observeAsState()
    val flow = userListViewModel.usersFlow
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { userListViewModel.deleteUsers() },
                icon = {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete"
                    )
                },
                text = {
                    Text(
                        stringResource(id = R.string.delete),
                        style = TitleTextStyle
                    )
                },
                backgroundColor = colorResource(id = R.color.teal_700)
            )
        }
    ) {
        val lazyPagingItems = flow.collectAsLazyPagingItems()
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
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

            if (viewState.value == ViewStateUpdate.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.regularPadding))
                            .size(dimensionResource(id = R.dimen.loaderSize)),
                        color = colorResource(id = R.color.blue_700)
                    )
                }
            }
        }

        lazyPagingItems.apply {
            if (loadState.append.endOfPaginationReached) {
                userListViewModel.addUsers()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun UserListError() {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.regularPadding)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dinosaur_error),
            contentDescription = "Error",
            Modifier
                .padding()
                .size(dimensionResource(id = R.dimen.imageSmallSize))
        )
        Text(
            stringResource(id = R.string.unknown_error_message),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = dimensionResource(id = R.dimen.regularPadding)),
            style = TitleTextStyle
        )
    }
}


@Composable
fun UserCard(user: AppUser, onClick: (AppUser) -> Unit) {
    val padding = dimensionResource(id = R.dimen.regularPadding)
    val imageSize = dimensionResource(id = R.dimen.imageSmallSize)
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