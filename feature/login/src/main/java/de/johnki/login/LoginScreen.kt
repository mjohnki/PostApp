package de.johnki.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), goToPosts: @Composable () -> Unit) {

    val state: LoginUiState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val uiState = state) {
            is LoginUiState.Content -> ContentState(uiState.showError) {
                viewModel.process(LoginEvent.LoginClicked(it))
            }
            LoginUiState.LoginSuccessful -> goToPosts()
        }
    }
}

@Composable
private fun ContentState(showError: Boolean, onLoginClick: (userId: String) -> Unit) {
    var text by remember { mutableStateOf("") }

    if (showError) {
        Text(text = "Error the UserID should contain just numbers")
    }
    Row {
        Text(text = "UserID:")
        TextField(
            value = text,
            onValueChange = { text = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(all = 8.dp)
        )
    }
    Button(
        onClick = { onLoginClick(text) }
    ) {
        Text("Login")
    }
}
