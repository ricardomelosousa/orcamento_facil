/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.orcamento.orcamentofacilwatch.presentation

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.orcamento.orcamentofacilwatch.R
import com.orcamento.orcamentofacilwatch.presentation.navigation.WearAppNavHost
import com.orcamento.orcamentofacilwatch.presentation.theme.Orcamento_facilTheme
import com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel.WearHomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            WearApp("Android")
//        }





        val openHistory = intent?.getBooleanExtra("openHistory", false) ?: false
        val periodId = intent?.getLongExtra("periodId", -1L) ?: -1L

//        setContent {
//            val app = application as OrcamentoFacilApp
//            WearAppNavHost(
//                openHistory = openHistory,
//                notificationPeriodId = periodId
//            )
//        }

        setContent {
            val app = application as OrcamentoFacilWatchApp
            val wearHomeViewModel = remember {
                WearHomeViewModel(app.container.wearBudgetRepository)
            }

            WearAppNavHost(
                wearHomeViewModel = wearHomeViewModel
            )
        }


    }
}

@Composable
fun WearApp(greetingName: String) {
    Orcamento_facilTheme {
        AppScaffold {
            val listState = rememberTransformingLazyColumnState()
            val transformationSpec = rememberTransformationSpec()
            ScreenScaffold(
                scrollState = listState,
                edgeButton = {
                    EdgeButton(
                        onClick = { /*TODO*/ },
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                    ) {
                        Text("More")
                    }
                },
            ) { contentPadding -> // ScreenScaffold provides default padding; adjust as needed
                TransformingLazyColumn(contentPadding = contentPadding, state = listState) {
                    item {
                        ListHeader(
                            modifier =
                                Modifier.fillMaxWidth().transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text(text = stringResource(R.string.hello_world, greetingName))
                        }
                    }
                    item {
                        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                            Text("Button A")
                        }
                    }
                    item {
                        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                            Text("Button B")
                        }
                    }
                    item {
                        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                            Text("Button C")
                        }
                    }
                }
            }
        }
    }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}


