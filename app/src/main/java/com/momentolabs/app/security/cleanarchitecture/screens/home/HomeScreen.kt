package com.momentolabs.app.security.cleanarchitecture.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.momentolabs.app.security.cleanarchitecture.R
import com.momentolabs.app.security.cleanarchitecture.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val isSearchMode = remember { mutableStateOf(true) }
    var searchText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val notesItems = listOf("0", "1", "2")


    Scaffold(
        topBar = {
            TopAppBar(

                title = {

                    if (isSearchMode.value) {
                        Text(text = stringResource(id = R.string.app_name))
                    } else {

                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier.height(45.dp),
                            textStyle = TextStyle.Default.copy(color = Color.White, fontSize = 10.sp),
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = context.getString(R.string.search_notes),
                                    color = Color.White, fontSize = 10.sp
                                )
                            }
                        )
                    }


                },

                navigationIcon = {

                    if (isSearchMode.value) {

                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.Menu, null)
                        }
                    } else {
                        IconButton(onClick = { isSearchMode.value = true }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                },


                colors = topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),

                actions = {
                    if (isSearchMode.value) {
                        IconButton(onClick = { isSearchMode.value = false }) {
                            Icon(Icons.Filled.Search, null)
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

            ConstraintLayout(modifier = Modifier.fillMaxSize().padding(paddingValues = it)) {

                val (placeHolderText, allNotesItems) = createRefs()
                if (notesItems.isEmpty()) {
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
                        items(notesItems.size) { index ->
                            AllNotesItems(eachNote = index, notesItems)
                        }
                    }
                }
            }


        }
    )


}


@Composable
fun AllNotesItems(eachNote: Int, list: List<String>) {
    Text(text = list[eachNote],
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        style = MaterialTheme.typography.bodyLarge)
}


@Preview
@Composable()
fun Preview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}
