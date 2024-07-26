package com.momentolabs.app.security.cleanarchitecture.screens.createNotes

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.LocalTextStyle
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.momentolabs.app.security.cleanarchitecture.R
import com.momentolabs.app.security.cleanarchitecture.models.Notes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CreateNotes(navController: NavController) {
    val viewModel: NotesViewModel = hiltViewModel()
    val context = LocalContext.current.applicationContext


    var notesTitle by remember { mutableStateOf("") }
    var notesDescription by remember { mutableStateOf("") }
    ConstraintLayout {
        val (topBar, bottomBar, screenData) = createRefs()
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

            Text(
                text = stringResource(id = R.string.notepad),
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(backPressButton.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(start = 10.dp),
                color = Color.White
            )





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
                        saveData(notesTitle, notesDescription, viewModel)

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
                    if (it.length <= 60)
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
                maxLines = 1, singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                )
            )

        }

        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .constrainAs(bottomBar) {
                bottom.linkTo(parent.bottom)
            }) {

        }

    }

}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(Date())
}


fun saveData(title: String, description: String, viewModel: NotesViewModel) {
    val notes = Notes(0, title, description, getCurrentDate())
    viewModel.insertNotes(notes = notes)
}

