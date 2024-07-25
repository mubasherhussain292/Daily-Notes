package com.momentolabs.app.security.cleanarchitecture.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.momentolabs.app.security.cleanarchitecture.R
import com.momentolabs.app.security.cleanarchitecture.models.DrawerItems
import com.momentolabs.app.security.cleanarchitecture.navigation.NavigationItem

@Composable
fun AppDrawer(
    navController: NavController, modifier: Modifier = Modifier, closeDrawer: () -> Unit = {}
) {
    val context = LocalContext.current
    ModalDrawerSheet(modifier = Modifier) {

        DrawerHeader(modifier)
        Spacer(modifier = Modifier.height(10.dp))
        DrawerItems(
            title = context.getString(R.string.my_notes), route = NavigationItem.Home.route,
            selectedIcon = Icons.Outlined.CheckCircle, unSelectedIcon = Icons.Filled.CheckCircle
        )

        DrawerItems(
            title = context.getString(R.string.color_themes), route = NavigationItem.Home.route,
            selectedIcon = Icons.Outlined.DateRange, unSelectedIcon = Icons.Filled.DateRange
        )

        DrawerItems(
            title = context.getString(R.string.deleted_notes), route = NavigationItem.Home.route,
            selectedIcon = Icons.Outlined.Delete, unSelectedIcon = Icons.Filled.Delete
        )

        DrawerItems(
            title = context.getString(R.string.backup), route = NavigationItem.Home.route,
            selectedIcon = Icons.Outlined.KeyboardArrowUp, unSelectedIcon = Icons.Filled.KeyboardArrowUp
        )

        DrawerItems(
            title = context.getString(R.string.restore), route = NavigationItem.Home.route,
            selectedIcon = Icons.Outlined.KeyboardArrowDown, unSelectedIcon = Icons.Filled.KeyboardArrowDown
        )

        DrawerItems(
            title = context.getString(R.string.buy_ads_free), route = NavigationItem.Home.route,
            selectedIcon = Icons.Outlined.ShoppingCart, unSelectedIcon = Icons.Filled.ShoppingCart
        )

        DrawerItems(
            title = context.getString(R.string.settings), route = NavigationItem.Home.route,
            selectedIcon = Icons.Outlined.Settings, unSelectedIcon = Icons.Filled.Settings
        )

        DrawerItems(
            title = context.getString(R.string.privacy_policy), route = NavigationItem.Home.route,
            selectedIcon = Icons.Outlined.CheckCircle, unSelectedIcon = Icons.Filled.CheckCircle
        )

        DrawerItems(
            title = context.getString(R.string.terms_and_conditions), route = NavigationItem.Home.route,
            selectedIcon = Icons.Outlined.CheckCircle, unSelectedIcon = Icons.Filled.CheckCircle
        )
    }
}


@Composable
fun DrawerHeader(modifier: Modifier) {


    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {

        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = stringResource(id = R.string.settings))

    }


}