package com.group3.smartaccount.navigation
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.group3.smartaccount.R
sealed class BottomNavItem(
    val route: String,
    @StringRes val labelResId: Int,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", R.string.nav_home, Icons.Filled.Home)
    object Transactions : BottomNavItem("transactions", R.string.nav_transactions, Icons.Filled.List)
    object Reports : BottomNavItem("reports", R.string.nav_reports, Icons.Filled.List)
}
