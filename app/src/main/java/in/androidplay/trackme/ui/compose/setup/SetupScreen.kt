package `in`.androidplay.trackme.ui.compose.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.ui.navigation.BottomNavItem
import `in`.androidplay.trackme.util.Constants.HOME_GRAPH
import `in`.androidplay.trackme.util.ResultData
import kotlinx.coroutines.launch

@Composable
fun SetupScreen(
    navController: NavController,
    viewModel: SetupViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val screenState = viewModel.setupScreenState.collectAsStateWithLifecycle()

    // Screen state management
    when (screenState.value) {
        is ResultData.Success -> navController.navigate(route = HOME_GRAPH)
        is ResultData.Failed -> {
            val message = (screenState.value as ResultData.Failed).message?.asString(context)
            message?.let {
                LaunchedEffect(key1 = {}) {
                    scope.launch { snackBarHostState.showSnackbar(message = it) }
                }
            }
        }

        else -> { /*Do nothing*/
        }
    }

    // Screen scaffold container
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) })
    { innerPadding ->
        SetupScreenUI(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(all = 16.dp),
            viewModel = viewModel
        )
    }
}

@Composable
private fun SetupScreenUI(
    modifier: Modifier,
    viewModel: SetupViewModel,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        var inputName by rememberSaveable { mutableStateOf("") }
        var inputWeight by rememberSaveable { mutableStateOf("") }

        Text(
            text = stringResource(id = R.string.app_greeting),
            modifier = Modifier.padding(top = 50.dp),
            color = Color.LightGray,
            style = TextStyle(
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = inputName,
            onValueChange = { inputName = it },
            label = {
                Text(text = "Your name")
            },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputWeight,
            onValueChange = { inputWeight = it },
            label = {
                Text(text = "Body weight")
            },
        )

        FilledTonalButton(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(3.dp),
            onClick = { viewModel.saveSetupData(inputName, inputWeight) }) {
            Text(text = "Save details")
        }

        Text(
            text = stringResource(id = R.string.setup_disclaimer),
            fontSize = 14.sp
        )
    }
}