package com.vrashkov.bitfinexcrypto.ui.screens.crypto

import android.widget.ProgressBar
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vrashkov.bitfinexcrypto.models.ProgressBarState
import com.vrashkov.bitfinexcrypto.ui.common.InfoBox
import kotlinx.coroutines.delay

@Composable
fun CryptoScreenComponent() {

    val viewModel: CryptoVM = hiltViewModel()

    val viewState = viewModel.viewState.value
    val onTriggerEvents = viewModel::onTriggerEvent

    LaunchedEffect(Unit) {
        while(true) {
            onTriggerEvents(CryptoEvent.ReloadCurrenicesDetailsData)
            delay(5000)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = viewState.loadError != null){
            viewState.loadError?.let {
                Row (modifier = Modifier.fillMaxWidth().background(Color.Red).padding(5.dp), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = it,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }

        AnimatedVisibility(viewState.loadingCurrencies == ProgressBarState.Loading){
            Row (modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(5.dp), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Loading Currencies",
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        }
        Column (modifier = Modifier.fillMaxSize().zIndex(1f)) {
            TextField(
                modifier = Modifier.fillMaxWidth().height(78.dp).padding(10.dp),
                label = {
                    Text("Search")
                },
                value = viewState.search,
                onValueChange = {
                    onTriggerEvents(CryptoEvent.OnSearchValueChange(it))
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn (modifier = Modifier.fillMaxSize()) {
                items(viewState.currenciesFiltered){
                    InfoBox(
                        data = it
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}