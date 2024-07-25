package com.momentolabs.app.security.cleanarchitecture.screens.createNotes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.momentolabs.app.security.cleanarchitecture.R

@Composable
fun CreateNotes(navController: NavController) {
    var notesTitle by remember { mutableStateOf("") }
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
                        saveData(notesTitle)

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
            }) {

            val (titleText) = createRefs()
            OutlinedTextField(value = notesTitle, onValueChange = { notesTitle = it })
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


fun saveData(title: String) {
    Log.d(TAG, "saveData: $title")
}


private const val TAG = "CreateNotes"