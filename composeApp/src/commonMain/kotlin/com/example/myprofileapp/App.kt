package com.example.myprofileapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

// --- BAGIAN IMPORT DATA ---
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.data.NotesUiState
import com.example.myprofileapp.navigation.BottomNavItem
import com.example.myprofileapp.navigation.Screen
import com.example.myprofileapp.screens.*
import com.example.myprofileapp.viewmodel.ProfileViewModel
import com.example.myprofileapp.viewmodel.NotesViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val profileViewModel: ProfileViewModel = koinInject()
    val notesViewModel: NotesViewModel = koinInject()
    val uiState by profileViewModel.uiState.collectAsState()
    val notesUiState by notesViewModel.uiState.collectAsState()
    val searchQuery by notesViewModel.searchQuery.collectAsState()
    val notesList = when (notesUiState) {
        is NotesUiState.Success -> (notesUiState as NotesUiState.Success).notes
        else -> emptyList<Note>()
    }
    val colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()
    MaterialTheme(colorScheme = colorScheme) {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val bottomNavItems = listOf(BottomNavItem.Notes, BottomNavItem.Favorites, BottomNavItem.Profile)
        val showBottomNav = bottomNavItems.any { it.route == currentRoute }
        val SageGreen = Color(0xFF8BA888)
        val softGray = Color(0xFF888888)

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.surface) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Menu", modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray.copy(alpha = 0.2f))
                    Spacer(modifier = Modifier.height(16.dp))

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Notes") }, label = { Text("Semua Catatan", fontWeight = FontWeight.Medium) },
                        selected = currentRoute == Screen.Notes.route, colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = SageGreen.copy(alpha = 0.15f), selectedIconColor = SageGreen, selectedTextColor = SageGreen),
                        onClick = { navController.navigate(Screen.Notes.route) { popUpTo(Screen.Notes.route) { saveState = true }; launchSingleTop = true; restoreState = true }; scope.launch { drawerState.close() } }, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") }, label = { Text("Catatan Favorit", fontWeight = FontWeight.Medium) },
                        selected = currentRoute == Screen.Favorites.route, colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = SageGreen.copy(alpha = 0.15f), selectedIconColor = SageGreen, selectedTextColor = SageGreen),
                        onClick = { navController.navigate(Screen.Favorites.route) { popUpTo(Screen.Notes.route) { saveState = true }; launchSingleTop = true; restoreState = true }; scope.launch { drawerState.close() } }, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }, label = { Text("Profil Saya", fontWeight = FontWeight.Medium) },
                        selected = currentRoute == Screen.Profile.route, colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = SageGreen.copy(alpha = 0.15f), selectedIconColor = SageGreen, selectedTextColor = SageGreen),
                        onClick = { navController.navigate(Screen.Profile.route) { popUpTo(Screen.Notes.route) { saveState = true }; launchSingleTop = true; restoreState = true }; scope.launch { drawerState.close() } }, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray.copy(alpha = 0.2f))
                    NavigationDrawerItem(
                        label = {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(if (uiState.isDarkMode) "Mode Terang" else "Mode Gelap", fontWeight = FontWeight.Medium)
                                Switch(checked = uiState.isDarkMode, onCheckedChange = { profileViewModel.toggleDarkMode(it) }, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = SageGreen))
                            }
                        },
                        selected = false, onClick = { profileViewModel.toggleDarkMode(!uiState.isDarkMode) }, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                }
            }
        ) {
            Scaffold(
                topBar = {
                    if (showBottomNav) {
                        TopAppBar(
                            title = { Text("Notes", fontWeight = FontWeight.ExtraBold, fontSize = 26.sp) },
                            navigationIcon = { IconButton(onClick = { scope.launch { drawerState.open() } }) { Icon(Icons.Default.Menu, contentDescription = "Menu") } },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background, titleContentColor = MaterialTheme.colorScheme.onBackground, navigationIconContentColor = MaterialTheme.colorScheme.onBackground)
                        )
                    }
                },
                bottomBar = {
                    if (showBottomNav) {
                        NavigationBar(containerColor = MaterialTheme.colorScheme.background, tonalElevation = 0.dp, modifier = Modifier.height(72.dp)) {
                            bottomNavItems.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icon, contentDescription = item.label, modifier = Modifier.size(26.dp)) }, selected = currentRoute == item.route,
                                    colors = NavigationBarItemDefaults.colors(selectedIconColor = SageGreen, indicatorColor = Color.Transparent, unselectedIconColor = softGray),
                                    onClick = { navController.navigate(item.route) { popUpTo(route = Screen.Notes.route) { saveState = true }; launchSingleTop = true; restoreState = true } }
                                )
                            }
                        }
                    }
                },
                floatingActionButton = {
                    if (currentRoute == Screen.Notes.route) {
                        FloatingActionButton(onClick = { navController.navigate(Screen.AddNote.route) }, containerColor = SageGreen, contentColor = Color.White, shape = CircleShape, elevation = FloatingActionButtonDefaults.elevation(4.dp)) {
                            Icon(Icons.Default.Add, contentDescription = "Add Note", modifier = Modifier.size(28.dp))
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(navController = navController, startDestination = Screen.Notes.route, modifier = Modifier.padding(innerPadding)) {
                    composable(Screen.Notes.route) {
                        NoteListScreen(
                            uiState = notesUiState, searchQuery = searchQuery, onSearchChanged = { notesViewModel.onSearchQueryChanged(it) },
                            isDarkMode = uiState.isDarkMode, onNoteClick = { noteId -> navController.navigate(Screen.NoteDetail.createRoute(noteId)) },
                            onToggleFavorite = { noteId, isFav -> notesViewModel.toggleFavorite(noteId, isFav) }
                        )
                    }
                    composable(Screen.Favorites.route) {
                        FavoritesScreen(
                            notes = notesList, isDarkMode = uiState.isDarkMode, onNoteClick = { noteId -> navController.navigate(Screen.NoteDetail.createRoute(noteId)) },
                            onToggleFavorite = { noteId -> val note = notesList.find { it.id == noteId }; if (note != null) notesViewModel.toggleFavorite(noteId, note.isFavorite) }
                        )
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen(uiState = uiState, onEditProfile = { newName, newBio -> profileViewModel.updateProfile(newName, newBio) }, onToggleDarkMode = { isDark -> profileViewModel.toggleDarkMode(isDark) })
                    }
                    composable(Screen.AddNote.route) {
                        AddNoteScreen(isDarkMode = uiState.isDarkMode, onSave = { title, content -> notesViewModel.addNote(title, content) }, onBack = { navController.popBackStack() })
                    }
                    composable(route = Screen.NoteDetail.route, arguments = listOf(navArgument("noteId") { type = NavType.IntType })) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                        val note = notesList.find { it.id == noteId }
                        NoteDetailScreen(
                            note = note, isDarkMode = uiState.isDarkMode, onBack = { navController.popBackStack() },
                            onEditClick = { id -> navController.navigate(Screen.EditNote.createRoute(id)) },
                            onDeleteClick = { id ->
                                notesViewModel.deleteNote(id)
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(route = Screen.EditNote.route, arguments = listOf(navArgument("noteId") { type = NavType.IntType })) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                        val note = notesList.find { it.id == noteId }
                        EditNoteScreen(noteId = noteId, note = note, isDarkMode = uiState.isDarkMode, onSave = { id, title, content -> notesViewModel.editNote(id, title, content) }, onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}