package com.example.mercardolivre

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavGraph.Companion.findStartDestination



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(bottomBar = {
        NavigationBar {
            val itens = listOf(
                NavItem("Home", Icons.Default.Home, Screen.Home.rota),
                NavItem("Favoritos", Icons.Default.Favorite, Screen.Favoritos.rota),
                NavItem("Perfil", Icons.Default.Person, Screen.Perfil.rota)
            )

            itens.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.rota,
                    onClick = {
                        navController.navigate(item.rota) {
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text(item.label) },
                    icon = {Icon(item.icon, contentDescription = item.label)}
                )
            }
        }
    }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.rota,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = Screen.Home.rota){
                HomeScreen(
                    onGoToPromos = {
                        navController.navigate(Screen.Promos.rota)
                    }
                )
            }

            composable(route = Screen.Promos.rota){
                PromosScreen(
                    onGoBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = Screen.Perfil.rota) {
                PerfilScreen(
                    onGoBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(route = Screen.Favoritos.rota) {
                FavoritosScreen(
                    onGoBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }



}