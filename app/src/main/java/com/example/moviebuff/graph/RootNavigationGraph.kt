package com.example.moviebuff.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviebuff.utils.SharedViewModel
import com.example.moviebuff.movies.MovieDetailsScreen
import com.example.moviebuff.movies.MoviesListScreen
import com.example.moviebuff.users.AddUserScreen
import com.example.moviebuff.users.UserListScreen

@Composable
fun RootNavigationGraph(navHostController: NavHostController) {
    val sharedViewModel = SharedViewModel()
    NavHost(
        navController = navHostController,
        startDestination = MovieBuffScreens.UsersScreen.route,
    ) {
        composable(route = MovieBuffScreens.UsersScreen.route) {
            UserListScreen(
                userOnClick = {
                    navHostController.navigate(MovieBuffScreens.MoviesScreen.route)
                },
                addUserOnclick = {
                   navHostController.navigate(MovieBuffScreens.AddUserScreen.route)
                }
            )
        }

        composable(route = MovieBuffScreens.MoviesScreen.route) {
            MoviesListScreen(
                onMovieClick = {
                    navHostController.navigate(MovieBuffScreens.MoviesDetailsScreen.route)
                },
                sharedViewModel = sharedViewModel
            )
        }

        composable(route = MovieBuffScreens.AddUserScreen.route) {
            AddUserScreen(
                backOnclick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(route = MovieBuffScreens.MoviesDetailsScreen.route) {
            MovieDetailsScreen(
                sharedViewModel = sharedViewModel
            )
        }

    }
}


sealed class MovieBuffScreens(
    val route: String

) {
    data object UsersScreen : MovieBuffScreens("users")
    data object MoviesScreen : MovieBuffScreens("movies")
    data object AddUserScreen : MovieBuffScreens("add_user")
    data object MoviesDetailsScreen : MovieBuffScreens("movies_details")
}