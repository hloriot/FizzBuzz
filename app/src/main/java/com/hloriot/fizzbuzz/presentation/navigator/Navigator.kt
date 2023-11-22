package com.hloriot.fizzbuzz.presentation.navigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hloriot.fizzbuzz.R
import com.hloriot.fizzbuzz.domain.model.Buzz
import com.hloriot.fizzbuzz.domain.model.Fizz
import com.hloriot.fizzbuzz.presentation.form.FizzBuzzForm
import com.hloriot.fizzbuzz.presentation.form.FizzBuzzFormInputs
import com.hloriot.fizzbuzz.presentation.list.FizzBuzzList
import com.hloriot.fizzbuzz.presentation.navigator.Navigator.FORM_ROUTE
import com.hloriot.fizzbuzz.presentation.navigator.Navigator.LIST_ROUTE
import com.hloriot.fizzbuzz.presentation.navigator.Navigator.LIST_ROUTE_ARGUMENTS

private object Navigator {
    const val FORM_ROUTE = "form"

    private const val LIST_ROUTE_UNFORMATTED = "list/%s/%s/%s/%s/%s"
    private const val LIST_ROUTE_FIZZ_WORD_KEY = "fizzWord"
    private const val LIST_ROUTE_FIZZ_MULTIPLE_KEY = "fizzMultiple"
    private const val LIST_ROUTE_BUZZ_WORD_KEY = "buzzWord"
    private const val LIST_ROUTE_BUZZ_MULTIPLE_KEY = "buzzMultiple"
    private const val LIST_ROUTE_LIMIT_KEY = "limit"

    val LIST_ROUTE = LIST_ROUTE_UNFORMATTED.format(
        "{$LIST_ROUTE_FIZZ_WORD_KEY}",
        "{$LIST_ROUTE_FIZZ_MULTIPLE_KEY}",
        "{$LIST_ROUTE_BUZZ_WORD_KEY}",
        "{$LIST_ROUTE_BUZZ_MULTIPLE_KEY}",
        "{$LIST_ROUTE_LIMIT_KEY}"
    )

    val LIST_ROUTE_ARGUMENTS = listOf(
        navArgument(LIST_ROUTE_FIZZ_WORD_KEY) { type = NavType.StringType },
        navArgument(LIST_ROUTE_FIZZ_MULTIPLE_KEY) { type = NavType.LongType },
        navArgument(LIST_ROUTE_BUZZ_WORD_KEY) { type = NavType.StringType },
        navArgument(LIST_ROUTE_BUZZ_MULTIPLE_KEY) { type = NavType.LongType },
        navArgument(LIST_ROUTE_LIMIT_KEY) { type = NavType.LongType },
    )

    fun toListRoute(inputs: FizzBuzzFormInputs): String {
        return LIST_ROUTE_UNFORMATTED.format(
            inputs.fizz.word,
            inputs.fizz.multiple.toString(),
            inputs.buzz.word,
            inputs.buzz.multiple.toString(),
            inputs.limit.toString()
        )
    }

    fun parseListRouteArguments(backStackEntry: NavBackStackEntry): FizzBuzzFormInputs {
        val fizzWord = backStackEntry.getOrDefault(LIST_ROUTE_FIZZ_WORD_KEY, "fizz")
        val fizzMultiple = backStackEntry.getOrDefault(LIST_ROUTE_FIZZ_MULTIPLE_KEY, 2)
        val buzzWord = backStackEntry.getOrDefault(LIST_ROUTE_BUZZ_WORD_KEY, "buzz")
        val buzzMultiple = backStackEntry.getOrDefault(LIST_ROUTE_BUZZ_MULTIPLE_KEY, 3)
        val limit = backStackEntry.getOrDefault(LIST_ROUTE_LIMIT_KEY, 10)

        return FizzBuzzFormInputs(
            Fizz(fizzWord, fizzMultiple),
            Buzz(buzzWord, buzzMultiple),
            limit
        )
    }

    private fun NavBackStackEntry.getOrDefault(key: String, defaultValue: String): String {
        return arguments?.getString(key, defaultValue) ?: defaultValue
    }

    private fun NavBackStackEntry.getOrDefault(key: String, defaultValue: Long): Long {
        return arguments?.getLong(key, defaultValue) ?: defaultValue
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var showBackArrow by remember { mutableStateOf(false) }

    navController.addOnDestinationChangedListener { _, _, _ ->
        showBackArrow = navController.previousBackStackEntry != null
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        modifier = Modifier.height(36.dp),
                        painter = painterResource(id = R.drawable.full_name),
                        contentDescription = stringResource(id = R.string.app_name)
                    )
                },
                navigationIcon = {
                    if (showBackArrow) {
                        IconButton(onClick = navController::navigateUp) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = FORM_ROUTE) {
            composable(FORM_ROUTE) {
                FizzBuzzForm(
                    modifier = Modifier.padding(innerPadding),
                    onFormCompleted = { inputs ->
                        navController.navigate(Navigator.toListRoute(inputs))
                    }
                )
            }

            composable(
                LIST_ROUTE,
                arguments = LIST_ROUTE_ARGUMENTS
            ) { backStackEntry ->
                val inputs = Navigator.parseListRouteArguments(backStackEntry)

                FizzBuzzList(
                    modifier = Modifier.padding(innerPadding),
                    inputs = inputs
                )
            }
        }
    }
}