package com.momentolabs.app.security.cleanarchitecture.screens.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.momentolabs.app.security.cleanarchitecture.R
import com.momentolabs.app.security.cleanarchitecture.models.Notes
import com.momentolabs.app.security.cleanarchitecture.navigation.NavigationItem
import com.momentolabs.app.security.cleanarchitecture.screens.createNotes.NotesViewModel
import com.momentolabs.app.security.cleanarchitecture.utils.Resources

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: NotesViewModel = hiltViewModel()
    val notesStates by viewModel.allNotes.collectAsState()
    val isSearchMode = remember { mutableIntStateOf(0) }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    DismissibleNavigationDrawer(drawerContent = {

        ModalDrawerSheet {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
            NavigationDrawerItem(
                label = { Text(text = "Drawer Item") },
                selected = false,
                onClick = { /*TODO*/ }
            )
        }

    }, gesturesEnabled = true
    ) {


        Scaffold(
            topBar = {
                TopAppBar(

                    title = {

                        when (isSearchMode.intValue) {
                            0 -> {
                                Text(text = stringResource(id = R.string.app_name))
                            }

                            1 -> {
                                OutlinedTextField(
                                    value = searchText,
                                    onValueChange = { searchText = it },
                                    shape = RoundedCornerShape(50.dp),
                                    modifier = Modifier.height(45.dp),
                                    textStyle = TextStyle.Default.copy(color = Color.White, fontSize = 12.sp),
                                    singleLine = true,
                                    placeholder = {
                                        Text(
                                            text = context.getString(R.string.search_notes),
                                            color = Color.White, fontSize = 12.sp
                                        )
                                    }
                                )
                            }

                            2 -> {}

                        }


                    },

                    navigationIcon = {

                        when (isSearchMode.intValue) {
                            0 -> {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(Icons.Filled.Menu, null)
                                }
                            }

                            1 -> {
                                IconButton(onClick = {
                                    isSearchMode.intValue = 0
                                    searchText = TextFieldValue("")
                                }) {
                                    Icon(Icons.Filled.ArrowBack, null)
                                }
                            }

                            2 -> {}
                        }
                    },


                    colors = topAppBarColors(
                        containerColor = Color.DarkGray,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),

                    actions = {
                        if (isSearchMode.intValue == 0) {
                            IconButton(onClick = { isSearchMode.intValue = 1 }) {
                                Icon(Icons.Filled.Search, null)
                            }
                        } else if (isSearchMode.intValue == 2) {

                            IconButton(onClick = {
                                isSearchMode.intValue = 1
                                deleteAllItems(viewModel)
                            }) {
                                Icon(Icons.Filled.Delete, null)
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(NavigationItem.CreateNotes.route) }, shape = CircleShape
                ) {
                    Image(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            },
            content = {

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = it)
                ) {

                    val (placeHolderText, allNotesItems) = createRefs()


                    when (notesStates) {
                        is Resources.onLoading -> {

                        }

                        is Resources.onError -> {

                        }

                        is Resources.onSuccess -> {
                            val notesData = (notesStates as Resources.onSuccess<List<Notes>>).data
                            val filteredNotes = notesData.filter { it.noteTitle.contains(searchText.text, ignoreCase = true) }
                            if (filteredNotes.isEmpty()) {
                                Text(text = stringResource(id = R.string.empty_note), fontSize = 16.sp, color = Color.DarkGray,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.constrainAs(placeHolderText) {
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    })
                            } else {
                                LazyColumn(modifier = Modifier
                                    .fillMaxSize()
                                    .constrainAs(allNotesItems) {
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    }) {

                                    items(filteredNotes.size) { index ->
                                        AllNotesItems(eachNote = index, filteredNotes, isSearchMode)
                                    }

                                }
                            }

                        }

                    }


                }


            }
        )

    }


    BackHandler {
        isSearchMode.intValue = 0
        searchText = TextFieldValue("")
        if (isSearchMode.intValue == 0) {
            val activity = (context as? Activity)
            activity?.finish()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllNotesItems(eachNote: Int, list: List<Notes>, isSearchMode: MutableIntState) {
    val viewModel: NotesViewModel = hiltViewModel()
    var color by remember { mutableStateOf(LightGray) }
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = {

                    updateList(list[eachNote].id, viewModel, isSearchMode)

                    color = if (color == LightGray) {
                        Gray
                    } else {
                        LightGray
                    }

                }
            ),
        colors = CardDefaults.cardColors(
            color
        )
    ) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (title, description, date) = createRefs()

            Text(
                text = list[eachNote].noteTitle,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 12.dp, top = 14.dp)
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }, fontSize = 16.sp,
                style = MaterialTheme.typography.labelLarge
            )


            Text(
                text = list[eachNote].notesDescription,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 10.dp, top = 0.dp, bottom = 14.dp)
                    .constrainAs(description) {
                        start.linkTo(parent.start)
                        top.linkTo(title.bottom)
                    }, fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = list[eachNote].notesDate,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 10.dp)
                    .constrainAs(date) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }, fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium
            )


        }

    }
}


fun updateList(value: Int, viewModel: NotesViewModel, isSearchMode: MutableIntState) {
    if (viewModel.deletionList.contains(value)) {
        viewModel.deletionList.remove(value)
    } else {
        viewModel.deletionList.add(value)
    }
    if (viewModel.deletionList.isNotEmpty()) {
        isSearchMode.intValue = 2
    } else {
        isSearchMode.intValue = 0
    }
}


fun deleteAllItems(viewModel: NotesViewModel) {
    viewModel.deleteNotes(viewModel.deletionList)
}


@Preview
@Composable()
fun Preview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}


private const val TAG = "HomeScreen"