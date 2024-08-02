package com.momentolabs.app.security.cleanarchitecture.screens.createNotes

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.momentolabs.app.security.cleanarchitecture.R
import com.momentolabs.app.security.cleanarchitecture.models.Notes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "CreateNotes"

@Composable
fun CreateNotes(navController: NavController) {
    val viewModel: NotesViewModel = hiltViewModel()
    val context = LocalContext.current.applicationContext
    var notesTitle by remember { mutableStateOf("") }
    var notesDescription by remember { mutableStateOf("") }
    var images by remember { mutableStateOf(listOf<Uri>()) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        images = it
        Log.d(TAG, "CreateNotes: $images")
    }

    ConstraintLayout {
        val (topBar, bottomBar, screenData, imagesColum) = createRefs()
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color.DarkGray)
            .constrainAs(topBar) {
                top.linkTo(parent.top)
            }) {

            val (backPressButton, title, save, location, moreMenu) = createRefs()

            Image(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .clickable { navController.popBackStack() }
                    .constrainAs(backPressButton) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })

            Text(text = stringResource(id = R.string.notepad), modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(backPressButton.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 10.dp), color = Color.White)

            Image(
                imageVector = Outlined.MoreVert,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .clickable { /*todo..*/ }
                    .constrainAs(moreMenu) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })


            Image(
                imageVector = Outlined.Place,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(0.dp, 0.dp, 15.dp, 0.dp)
                    .clickable { /*todo..*/ }
                    .constrainAs(location) {
                        end.linkTo(moreMenu.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })

            Image(
                imageVector = Outlined.Warning,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(0.dp, 0.dp, 15.dp, 0.dp)
                    .clickable {
                        saveData(notesTitle, notesDescription, viewModel, navController, context)
                    }
                    .constrainAs(save) {
                        end.linkTo(location.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })


        }

        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(screenData) {
                top.linkTo(topBar.bottom)
                bottom.linkTo(bottomBar.top)
                height = Dimension.fillToConstraints
            }) {

            val (titleText, description) = createRefs()

            TextField(
                value = notesTitle,
                onValueChange = {
                    if (it.length <= 50)
                        notesTitle = it
                    else
                        Toast.makeText(context, context.getString(R.string.title_limit_exceed), Toast.LENGTH_SHORT).show()
                },
                placeholder = { Text(text = stringResource(id = R.string.title), color = Color.Gray, style = MaterialTheme.typography.titleMedium) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .constrainAs(titleText) { top.linkTo(parent.top) },
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                )
            )

            TextField(
                value = notesDescription,
                onValueChange = { notesDescription = it },
                placeholder = { Text(text = stringResource(id = R.string.description), color = Color.Gray, style = MaterialTheme.typography.titleMedium) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .constrainAs(description) { top.linkTo(titleText.bottom) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                )
            )


            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(imagesColum) {
                    top.linkTo(description.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }) {
                items(images.size) {
                    AsyncImage(model = images[it], contentDescription = null,modifier = Modifier.padding(5.dp))
                }
            }
        }



        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
            .background(Color.Gray, shape = RoundedCornerShape(15.dp))
            .constrainAs(bottomBar) {
                bottom.linkTo(parent.bottom)
            }) {

            val (selectImage) = createRefs()

            Image(
                imageVector = Outlined.Email,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .constrainAs(selectImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }
            )


        }

    }

    BackHandler {
        /*https://stackoverflow.com/questions/71534415/composable-invocations-can-only-happen-from-the-context-of-a-composable-functio*/
//        ConfirmationDialog(navController)
    }
}

@Composable
fun ConfirmationDialog(navController: NavController) {

    Dialog(onDismissRequest = { navController.popBackStack() }) {

    }


}



fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(Date())
}


fun saveData(title: String, description: String, viewModel: NotesViewModel, navController: NavController, context: Context) {
    if (title.isNotEmpty()) {
        val notes = Notes(0, title, description, getCurrentDate())
        viewModel.insertNotes(notes = notes)
        navController.popBackStack()
    } else {
        Toast.makeText(context, "please write any title", Toast.LENGTH_SHORT).show()
    }
}

