package com.example.microstationapp.ui.sales

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.microstationapp.data.models.NewSaleModel
import com.example.microstationapp.viewmodels.SalesViewModel
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween


@Composable
fun SalesScreen(
    modifier: Modifier = Modifier,
    viewModel : SalesViewModel
) {
    val salesModel = viewModel.salePeriodModel
    val totalGallonSold = viewModel.totalGallonsSold
    val totalSalesAmount = viewModel.totalSalesAmount
    val pricePerGallon = viewModel.pricePerGallon
    val isSalePeriodOpen = viewModel.isSalePeriodOpen
    val currentMeter = viewModel.currentMeter
    val sales = salesModel.sales

    var showModal by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Sales Dashboard",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            DashBoardItem("Current Meter : ", "$currentMeter")
            DashBoardItem("Gallons Sold :", "$totalGallonSold")
            DashBoardItem("Price per Gallon : ", "$pricePerGallon")
            DashBoardItem("Total Sales : ", "$totalSalesAmount")

            Spacer(modifier = Modifier.height(24.dp))

            //Sale Period Section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color(0xFFF1F1F1)),
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Sales Period", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(16.dp))

                    SalePeriodButtons(
                        isSalePeriodOpen = isSalePeriodOpen,
                        onStartPeriod = { viewModel.startSalesPeriod() },
                        onClosePeriod = { viewModel.closeSalesPeriod() }
                    )
                }
            }

            if (isSalePeriodOpen) {
                Spacer(modifier = Modifier.height(24.dp))

                // New Sale Section
                Button(
                    onClick = { showModal = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("New Sale")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Confirmed Sales",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (sales.isEmpty()) {
                Text(
                    "No sales confirmed yet",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    sales.forEachIndexed { index, sale ->
                        SaleItem(saleId = index + 1, sale = sale)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    if (showModal) {
        NewSaleModal(
            currentMeter = currentMeter,
            onDismiss = { showModal = false },
            onConfirm = { gallons ->
                val newSale = NewSaleModel(
                    product = "Propane Gas",
                    unitPrice = pricePerGallon,
                    quantity = gallons,
                    amount = gallons * pricePerGallon,
                    meterBeforeSale = currentMeter,
                    meterAfterSale = currentMeter + gallons
                )
                viewModel.confirmSales(newSale)
                showModal = false
            }
        )

//        NewSaleDialog(
//            onDismiss = { showModal = false },
//            onConfirm = { gallons ->
//                val newSale = NewSaleModel(
//                    product = "Propane Gas",
//                    unitPrice = pricePerGallon,
//                    quantity = gallons,
//                    amount = gallons * pricePerGallon,
//                    meterBeforeSale = salesModel.meterModel.currentMeter,
//                    meterAfterSale = salesModel.meterModel.currentMeter + gallons
//                )
//                viewModel.confirmSales(newSale)
//                viewModel.saveCurrentMeter(newSale.meterAfterSale)
//                showModal = false
//            }
//        )


    }
}

@Composable
fun SaleItem(
    saleId: Int,
    sale: NewSaleModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Column(modifier = Modifier.weight(1f)) {
            Text("Sale ID: $saleId", fontWeight = FontWeight.Bold)
            Text("Quantity: ${sale.quantity} gallons")
            Text("Amount: $${sale.amount}")
        }
        Column(modifier = Modifier.weight(1f)) {
            Text("Payment: ${sale.paymentMethod}")
            Text("Timestamp: ${sale.timestamp}")
        }
    }
}

@Composable
fun DashBoardItem(label : String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold)
        Text(value, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun NewSaleModal(
    modifier: Modifier = Modifier,
    currentMeter : Double,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    var gallons by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        AnimatedVisibility(
            visible = true,
            enter = scaleIn(initialScale = 0.8f) + fadeIn(),
            exit = scaleOut(targetScale = 0.8f) + fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
//                    .padding(WindowInsets.systemBars.asPaddingValues()),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colors.surface,
                elevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "New Sale",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Current Meter : $currentMeter")
                    Text("Enter number of gallons : ")
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = gallons,
                        onValueChange = {
                            gallons = it
                            isError = false
                        },
                        singleLine = true,
                        label = { Text("Gallons") },
                        isError = isError
                    )
                    if (isError) {
                        Text(
                            text = "Invalid, please enter positive number",
                            color = Color.Red,
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Spacer(modifier =  Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                val gallonsValue = gallons.toDoubleOrNull()
                                if (gallonsValue == null || gallonsValue <= 0) {
                                    isError = true
                                } else {
                                    onConfirm(gallonsValue)
                                }
                            }
                        ) {
                            Text("Confirm")
                        }
                        Button(onClick = onDismiss) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SalePeriodButtons(
    isSalePeriodOpen :Boolean,
    onStartPeriod: () -> Unit,
    onClosePeriod: () -> Unit
) {
    val enterAnimation = slideInHorizontally(initialOffsetX = { it }) + fadeIn()
    val exitAnimation = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    // Define common colors for buttons
    val enabledColor = Color(0xFF2196F3)
    val disabledColor = Color.LightGray
    val enabledTextColor = Color.White
    val disabledTextColor = Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F1F1))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //Open Sale Button
        AnimatedVisibility(
            visible = !isSalePeriodOpen,
            enter = slideInHorizontally(initialOffsetX = { -it },
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(),
//            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        ) {
            Button(
                onClick = onStartPeriod,
                enabled = !isSalePeriodOpen,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isSalePeriodOpen) enabledColor else disabledColor,
                    contentColor = if (!isSalePeriodOpen) enabledTextColor else disabledTextColor
                )
            ) {
                Text("Open Sale")
            }

        }

        //Close Sale Button
        AnimatedVisibility(
            visible = isSalePeriodOpen,
            enter = slideInHorizontally(initialOffsetX = { it },
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(),
//            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        ) {
            Button(
                onClick = onClosePeriod,
                enabled = isSalePeriodOpen,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSalePeriodOpen) enabledColor else disabledColor,
                    contentColor = if (isSalePeriodOpen) enabledTextColor else disabledTextColor
                )
            ) {
                Text("Close Sale")
            }
        }

    }
}

@Composable
fun NewSaleDialog(onDismiss : () -> Unit, onConfirm : (Double) -> Unit) {
    var gallons by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("New Sale") },
        text = {
            Column {
                Text("Enter number of Gallons : ")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = gallons,
                    onValueChange = { gallons = it },
                    singleLine = true,
                    label = { Text("Gallons") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val gallonsSold = gallons.toDoubleOrNull() ?:0.0
                    onConfirm(gallonsSold)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun NewSaleModalPreview() {
    NewSaleModal(
        currentMeter = 20.0,
        onDismiss = {},
        onConfirm = {}
    )
}