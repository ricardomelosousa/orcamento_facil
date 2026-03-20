package com.orcamento.orcamentofacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.orcamento.orcamentofacil.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                AppNavHost(appContainer = (application as OrcamentoFacilApp).container)
                //Teste()
            }
        }
    }

//    @Preview(showBackground = true)
//    @Composable
//    fun Teste(){
//        Column {
//            Box(modifier =  Modifier.height(30.dp).background(Color.Blue).width(50.dp)){
//                Image(painter = painterResource(id = R.drawable.orca_facil),
//                    contentDescription = "Imagem do produto")
//            }
//        }
//    }


}
