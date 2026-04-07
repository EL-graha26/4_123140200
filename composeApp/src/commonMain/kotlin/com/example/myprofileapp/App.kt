package com.example.myprofileapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myprofileapp.navigation.BottomNavItem
import com.example.myprofileapp.navigation.Screen
import com.example.myprofileapp.screens.*
import com.example.myprofileapp.viewmodel.ProfileViewModel
import com.example.myprofileapp.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val profileViewModel = remember { ProfileViewModel() }
    val notesViewModel = remember { NotesViewModel() }

    val uiState by profileViewModel.uiState.collectAsState()
    val notesList by notesViewModel.notes.collectAsState()

    val colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val bottomNavItems = listOf(BottomNavItem.Notes, BottomNavItem.Favorites, BottomNavItem.Profile)
        val showBottomNav = bottomNavItems.any { it.route == currentRoute }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Menu Note App", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Notes") },
                        label = { Text("Semua Catatan") },
                        selected = currentRoute == Screen.Notes.route,
                        onClick = {
                            navController.navigate(Screen.Notes.route) {
                                popUpTo(Screen.Notes.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                        label = { Text("Catatan Favorit") },
                        selected = currentRoute == Screen.Favorites.route,
                        onClick = {
                            navController.navigate(Screen.Favorites.route) {
                                popUpTo(Screen.Notes.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profil Saya") },
                        selected = currentRoute == Screen.Profile.route,
                        onClick = {
                            navController.navigate(Screen.Profile.route) {
                                popUpTo(Screen.Notes.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    NavigationDrawerItem(
                        label = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(if (uiState.isDarkMode) "Mode Terang" else "Mode Gelap")
                                Switch(
                                    checked = uiState.isDarkMode,
                                    onCheckedChange = { profileViewModel.toggleDarkMode(it) }
                                )
                            }
                        },
                        selected = false,
                        onClick = { profileViewModel.toggleDarkMode(!uiState.isDarkMode) },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        ) {
            Scaffold(
                topBar = {
                    if (showBottomNav) {
                        TopAppBar(
                            title = { Text("Note App") },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF8E1D1D),
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White
                            )
                        )
                    }
                },
                bottomBar = {
                    if (showBottomNav) {
                        NavigationBar(containerColor = Color(0xFF1E1E1E)) {
                            bottomNavItems.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icon, contentDescription = item.label) },
                                    label = { Text(item.label) },
                                    selected = currentRoute == item.route,
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color(0xFFB71C1C),
                                        indicatorColor = Color.Black
                                    ),
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(route = Screen.Notes.route) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                },
                floatingActionButton = {
                    if (currentRoute == Screen.Notes.route) {
                        FloatingActionButton(
                            onClick = { navController.navigate(Screen.AddNote.route) },
                            containerColor = Color(0xFFB71C1C),
                            contentColor = Color.White
                        ) { Icon(Icons.Default.Add, contentDescription = "Add Note") }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Notes.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Notes.route) {
                        NoteListScreen(
                            notes = notesList,
                            isDarkMode = uiState.isDarkMode,
                            onNoteClick = { noteId -> navController.navigate(Screen.NoteDetail.createRoute(noteId)) },
                            onToggleFavorite = { noteId -> notesViewModel.toggleFavorite(noteId) }
                        )
                    }
                    composable(Screen.Favorites.route) {
                        FavoritesScreen(
                            notes = notesList,
                            isDarkMode = uiState.isDarkMode,
                            onNoteClick = { noteId -> navController.navigate(Screen.NoteDetail.createRoute(noteId)) },
                            onToggleFavorite = { noteId -> notesViewModel.toggleFavorite(noteId) }
                        )
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen(
                            uiState = uiState,
                            onEditProfile = { newName, newBio -> profileViewModel.updateProfile(newName, newBio) },
                            onToggleDarkMode = { isDark -> profileViewModel.toggleDarkMode(isDark) }
                        )
                    }

                    composable(Screen.AddNote.route) {
                        AddNoteScreen(
                            onSave = { title, content -> notesViewModel.addNote(title, content) },
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(route = Screen.NoteDetail.route, arguments = listOf(navArgument("noteId") { type = NavType.IntType })) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                        val note = notesList.find { it.id == noteId }
                        NoteDetailScreen(
                            note = note,
                            isDarkMode = uiState.isDarkMode,
                            onBack = { navController.popBackStack() },
                            onEditClick = { id -> navController.navigate(Screen.EditNote.createRoute(id)) }
                        )
                    }

                    composable(route = Screen.EditNote.route, arguments = listOf(navArgument("noteId") { type = NavType.IntType })) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                        val note = notesList.find { it.id == noteId }
                        EditNoteScreen(
                            noteId = noteId,
                            note = note,
                            onSave = { id, title, content -> notesViewModel.editNote(id, title, content) },
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}