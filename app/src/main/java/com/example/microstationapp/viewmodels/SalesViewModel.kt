package com.example.microstationapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.microstationapp.data.models.MeterModel
import com.example.microstationapp.data.models.NewSaleModel
import com.example.microstationapp.data.models.SalePeriodModel
import com.example.microstationapp.data.room.MeterDao
import com.example.microstationapp.data.room.MeterEntity
import com.example.microstationapp.data.room.SalePeriodDao
import com.example.microstationapp.data.room.SalesDao
import com.example.microstationapp.data.room.toEntity
import com.example.microstationapp.data.room.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val meterDao: MeterDao,
    private val salesDao: SalesDao,
//    private val salePeriodDao: SalePeriodDao
): ViewModel() {
    var currentSalePeriod by mutableStateOf((SalePeriodModel()))
        private set

    var currentSalePeriodId:Int? = null

    var salePeriodModel by mutableStateOf(SalePeriodModel())
        private set

    val totalGallonsSold : Double
        get() = salePeriodModel.totalGallonsSold

    val totalSalesAmount : Double
        get() = salePeriodModel.totalSalesAmount

    val pricePerGallon : Double = 62.0

    var isSalePeriodOpen by mutableStateOf(false)
        private set

    var currentMeter by mutableDoubleStateOf(0.0)
        private set

    init {
        loadCurrentMeter()
        loadSales()
    }

    private fun loadCurrentMeter() {
        viewModelScope.launch {
            val meterEntity = meterDao.getCurrentMeter()
            currentMeter = meterEntity?.currentMeter?:0.0
        }
    }

    private fun loadSales() {
        viewModelScope.launch {
            val sales = salesDao.getAllSales().map { it.toModel() }
            salePeriodModel = salePeriodModel.copy(sales = sales.toMutableList())
        }
    }

    fun saveCurrentMeter(newMeterValue:Double) {
        viewModelScope.launch {
            val meterEntity = MeterEntity(currentMeter = newMeterValue)
            meterDao.saveCurrentMeter(meterEntity)
            currentMeter = newMeterValue
        }
    }

    fun startSalesPeriod() {
        viewModelScope.launch {
            if (isSalePeriodOpen) {
                closeSalesPeriod()
            }
            salePeriodModel = salePeriodModel.copy(
                meterModel = MeterModel(currentMeter = currentMeter),
                startTime = System.currentTimeMillis(),
                isSaleOpen = true,
                sales = mutableListOf()
            )
//            salesDao.insertSale(salesModel.)
            isSalePeriodOpen = true
        }
    }

    fun closeSalesPeriod() {
        viewModelScope.launch {
            if (salePeriodModel.isSaleOpen) {
                salePeriodModel = salePeriodModel.copy(
                    endTime = System.currentTimeMillis(),
                    isSaleOpen = false
                )
                isSalePeriodOpen = false
            }
        }
    }

    fun confirmSales(newSale: NewSaleModel) {
        viewModelScope.launch {
            val updateMeter = currentMeter + newSale.quantity
            val updateSalesList = salePeriodModel.sales.toMutableList().apply {
                add(newSale)
            }

            salePeriodModel = salePeriodModel.copy(
                meterModel = salePeriodModel.meterModel.copy(currentMeter = updateMeter),
                totalGallonsSold = salePeriodModel.totalGallonsSold + newSale.quantity,
                totalSalesAmount = salePeriodModel.totalSalesAmount + newSale.amount,
                sales = updateSalesList
            )
            saveCurrentMeter(updateMeter)
//            salesModel.sales.add(newSale)
            salesDao.insertSale(newSale.toEntity())
        }

    }
}