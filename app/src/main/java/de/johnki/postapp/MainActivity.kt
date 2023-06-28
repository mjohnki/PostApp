package de.johnki.postapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import de.johnki.login.LoginScreen
import de.johnki.postapp.ui.theme.PostAppTheme
import de.johnki.postlist.PostListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(goToPosts = {
                        PostListScreen(goToComments = {

                        })
                    })
                }
            }
        }
    }

    companion object {
        const val loginPath = "/Login"
        const val postListPath = "/Posts"
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainActivity.loginPath
    )
    {
        composable(route = MainActivity.loginPath){

        }
        composable(route = MainActivity.postListPath){
            PostListScreen{
                navController.navigate(MainActivity.loginPath)
            }
        }
    }
}
