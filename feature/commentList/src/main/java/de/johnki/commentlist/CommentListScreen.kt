package de.johnki.commentlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.johnki.data.comment.Comment
import de.johnki.data.comment.PostCommentList
import de.johnki.data.post.Post
import kotlinx.coroutines.flow.Flow

@Composable
fun CommentListScreen(
    viewModel: CommentListViewModel = hiltViewModel()) {

    val state: CommentListUiState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val uiState = state) {
            is CommentListUiState.Loading -> LoadingState()
            is CommentListUiState.Content -> ContentState(
                data = uiState.items,
                onFavClick = {
                    viewModel.process(CommentListEvent.PostFavedClicked(it))
                })
        }
    }
}

@Composable
private fun LoadingState() {
    Text(text = "Loading")
}

@Composable
private fun ContentState(
    data: Flow<PostCommentList>,
    onFavClick: (postId: Int) -> Unit,
) {
    val postCommentList: PostCommentList? by data.collectAsStateWithLifecycle(null)

    if(postCommentList != null){
        Text(text = "Comments")
        Post(post = postCommentList!!.post, onFavClick = onFavClick)
        LazyColumn {
            items(items = postCommentList!!.comments) {
                Comment(it)
            }
        }
    } else{
        LoadingState()
    }
}

@Suppress("MagicNumber")
@Composable
private fun Post(post: Post, onFavClick: (postId: Int) -> Unit) {

    Column(
        Modifier
            .padding(8.dp)
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary))
            .padding(8.dp)
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

@Composable
private fun Comment(comment: Comment) {

    Column(
        Modifier
            .padding(start= 24.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary))
            .padding(8.dp)
    ) {
        Text(text = comment.email)
        Text(text = comment.name)
        Text(text = comment.body)
    }
}
