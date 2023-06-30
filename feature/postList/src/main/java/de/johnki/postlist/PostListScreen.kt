package de.johnki.postlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.johnki.data.post.Post
import kotlinx.coroutines.flow.Flow

@Composable
fun PostListScreen(
    viewModel: PostListViewModel = hiltViewModel()
) {

    val state: PostListUiState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val uiState = state) {
            is PostListUiState.Loading -> LoadingState()
            is PostListUiState.AllPosts -> PostsState(
                data = uiState.items,
                allActive = true,
                showAllPosts = {
                    viewModel.process(PostListEvent.ShowAllPostClicked)
                },
                showFavPosts = {
                    viewModel.process(PostListEvent.ShowFavedPostClicked)
                },
                onFavClick = {
                    viewModel.process(PostListEvent.PostFavedClicked(it))
                },
                onPostClick = {
                    viewModel.process(PostListEvent.PostClicked(it))
                })

            is PostListUiState.FavedPosts -> PostsState(
                data = uiState.items,
                allActive = false,
                showAllPosts = {
                    viewModel.process(PostListEvent.ShowAllPostClicked)
                },
                showFavPosts = {
                    viewModel.process(PostListEvent.ShowFavedPostClicked)
                },
                onFavClick = {
                    viewModel.process(PostListEvent.PostFavedClicked(it))
                },
                onPostClick = {
                    viewModel.process(PostListEvent.PostClicked(it))
                })
        }
    }
}

@Composable
private fun LoadingState() {
    Text(text = "Loading")
}

@Suppress("LongParameterList", "LongMethod")
@Composable
private fun PostsState(
    data: Flow<List<Post>>,
    showAllPosts: () -> Unit,
    showFavPosts: () -> Unit,
    onPostClick: (postId: Int) -> Unit,
    onFavClick: (postId: Int) -> Unit,
    allActive: Boolean
) {
    val posts: List<Post> by data.collectAsStateWithLifecycle(emptyList())

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (header, content, footer) = createRefs()

        LazyColumn(modifier = Modifier.constrainAs(content) {
            top.linkTo(header.bottom)
            bottom.linkTo(footer.top)
        }) {
            items(items = posts) {
                Post(
                    post = it,
                    onFavClick = onFavClick,
                    onPostClick = onPostClick
                )
            }
        }

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(8.dp)) {
            Text(text = "My Posts")
        }

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .constrainAs(footer) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(8.dp)) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (allActive)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.background
                ),
                onClick = showAllPosts
            ) {
                Text("All")
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (!allActive)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.background
                ),
                onClick = showFavPosts
            ) {
                Text("Fav")
            }
        }
    }
}

@Suppress("MagicNumber")
@Composable
private fun Post(
    post: Post,
    onFavClick: (postId: Int) -> Unit,
    onPostClick: (postId: Int) -> Unit,
) {

    Column(
        Modifier
            .padding(8.dp)
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary))
            .padding(8.dp)
            .clickable { onPostClick(post.id) }
    ) {
        Row(Modifier.padding(bottom = 24.dp)) {
            Text(modifier = Modifier.weight(5f), text = post.title)
            Button(modifier = Modifier.weight(2f),
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (post.fav)
                        MaterialTheme.colorScheme.background
                    else
                        MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = { onFavClick(post.id) }) {
                Text("Fav")
            }
        }

        Text(text = post.body)
    }


}

