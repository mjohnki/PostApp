package de.johnki.postlist

import androidx.lifecycle.ViewModel
import com.motorro.commonstatemachine.CommonMachineState
import com.motorro.commonstatemachine.coroutines.FlowStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import de.johnki.postlist.state.PostListStateFactory
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val factory: PostListStateFactory.Impl,
) : ViewModel() {

    private fun initStateMachine(): CommonMachineState<PostListEvent, PostListUiState> = factory.loading()
    private val stateMachine = FlowStateMachine(PostListUiState.Loading, ::initStateMachine)

    val state: StateFlow<PostListUiState> = stateMachine.uiState

    fun process(gesture: PostListEvent) {
        stateMachine.process(gesture)
    }
}
