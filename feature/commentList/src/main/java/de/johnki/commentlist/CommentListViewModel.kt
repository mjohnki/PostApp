package de.johnki.commentlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.motorro.commonstatemachine.CommonMachineState
import com.motorro.commonstatemachine.coroutines.FlowStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import de.johnki.commentlist.state.CommentListStateFactory
import de.johnki.navigation.Destination
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val factory: CommentListStateFactory.Impl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId =
        savedStateHandle.get<String>(Destination.CommentListScreen.ID_KEY)?.toIntOrNull() ?: 0

    private fun initStateMachine(): CommonMachineState<CommentListEvent, CommentListUiState> = factory.loading(postId)
    private val stateMachine = FlowStateMachine(CommentListUiState.Loading, ::initStateMachine)

    val state: StateFlow<CommentListUiState> = stateMachine.uiState

    fun process(gesture: CommentListEvent) {
        stateMachine.process(gesture)
    }
}
