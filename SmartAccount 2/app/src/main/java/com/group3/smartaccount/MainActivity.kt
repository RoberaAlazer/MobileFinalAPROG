package com.group3.smartaccount
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.group3.smartaccount.data.local.SmartAccountDatabase
import com.group3.smartaccount.data.repository.TransactionRepository
import com.group3.smartaccount.navigation.BottomNavItem
import com.group3.smartaccount.navigation.SmartAccountNavGraph
import com.group3.smartaccount.ui.theme.SmartAccountTheme
import com.group3.smartaccount.viewmodel.TransactionViewModel
import com.group3.smartaccount.viewmodel.TransactionViewModelFactory
import androidx.compose.ui.res.stringResource
import com.group3.smartaccount.R
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartAccountTheme {
                val context = LocalContext.current

                val db = remember {
                    Room.databaseBuilder(
                        context.applicationContext,
                        SmartAccountDatabase::class.java,
                        "smart_account_db"
                    ).build()
                }

                val repository = remember { TransactionRepository(db.transactionDao()) }

                val viewModel: TransactionViewModel = viewModel(
                    factory = TransactionViewModelFactory(repository)
                )

                val uiState by viewModel.uiState.collectAsState()

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val bottomItems = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Transactions,
                    BottomNavItem.Reports
                )

                Scaffold(
                    bottomBar = {
                        // hide bottom bar on splash
                        if (currentRoute != "splash") {
                            NavigationBar {
                                bottomItems.forEach { item ->
                                    NavigationBarItem(
                                        selected = currentRoute == item.route,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo("home") { inclusive = false }
                                                launchSingleTop = true
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = stringResource(item.labelResId)

                                            )
                                        },
                                        label = { Text(stringResource(item.labelResId)) }

                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SmartAccountNavGraph(
                            navController = navController,
                            viewModel = viewModel,
                            uiState = uiState
                        )
                    }
                }
            }
        }
    }
}
