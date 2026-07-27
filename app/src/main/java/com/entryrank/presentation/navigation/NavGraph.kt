package com.entryrank.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.entryrank.presentation.auth.LoginScreen
import com.entryrank.presentation.auth.SignupScreen
import com.entryrank.presentation.dashboard.DashboardScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Dashboard : Screen("dashboard")
    object Practice : Screen("practice/{subjectId}") {
        fun createRoute(subjectId: String) = "practice/$subjectId"
    }
    object Books : Screen("books")
    object Profile : Screen("profile")
    object PracticeHistory : Screen("practice_history")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }
        
        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigateUp()
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onSubjectClick = { subjectId ->
                    navController.navigate(Screen.Practice.createRoute(subjectId))
                },
                onBooksClick = {
                    navController.navigate(Screen.Books.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onPracticeHistoryClick = {
                    navController.navigate(Screen.PracticeHistory.route)
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = Screen.Practice.route,
            arguments = listOf(
                navArgument("subjectId") { type = NavType.StringType }
            )
        ) {
            com.entryrank.presentation.practice.PracticeScreen(
                onBack = { navController.navigateUp() },
                onComplete = { sessionId ->
                    navController.navigate("result/$sessionId") {
                        popUpTo(Screen.Practice.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = "result/{sessionId}",
            arguments = listOf(
                navArgument("sessionId") { type = NavType.StringType }
            )
        ) {
            com.entryrank.presentation.practice.ResultScreen(
                onBack = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onPracticeAgain = {
                    navController.navigateUp()
                    navController.navigateUp()
                }
            )
        }
        
        composable(Screen.Books.route) {
            com.entryrank.presentation.books.BooksScreen(
                onBack = { navController.navigateUp() },
                onBookClick = { bookId ->
                    navController.navigate("book/$bookId")
                }
            )
        }
        
        composable(
            route = "book/{bookId}",
            arguments = listOf(
                navArgument("bookId") { type = NavType.StringType }
            )
        ) {
            PlaceholderScreen(
                title = "Book Chapters",
                message = "Chapter list coming soon",
                onBack = { navController.navigateUp() }
            )
        }
        
        composable(Screen.Profile.route) {
            com.entryrank.presentation.profile.ProfileScreen(
                onBack = { navController.navigateUp() },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.PracticeHistory.route) {
            com.entryrank.presentation.practice.PracticeHistoryScreen(
                onBack = { navController.navigateUp() },
                onSessionClick = { sessionId ->
                    navController.navigate("result/$sessionId")
                }
            )
        }
    }
}

@Composable
fun PlaceholderScreen(
    title: String,
    message: String,
    onBack: () -> Unit
) {
    androidx.compose.material3.Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { androidx.compose.material3.Text(title) },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = onBack) {
                        androidx.compose.material3.Icon(
                            androidx.compose.material.icons.Icons.Default.ArrowBack,
                            "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        androidx.compose.foundation.layout.Box(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = message,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
        }
    }
}
