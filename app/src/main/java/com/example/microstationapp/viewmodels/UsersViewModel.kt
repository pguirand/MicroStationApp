package com.example.microstationapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.microstationapp.data.models.AllUsersModel
import com.example.microstationapp.data.models.UserModel
import com.example.microstationapp.data.remote.ApiResponse
import com.example.microstationapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _listAllUsers = MutableStateFlow<ApiResponse<AllUsersModel>>(ApiResponse.Loading)
    val listAllUsers : StateFlow<ApiResponse<AllUsersModel>> = _listAllUsers

    private val _singleUser = MutableStateFlow<ApiResponse<UserModel>>(ApiResponse.Loading)
    val singleUser:StateFlow<ApiResponse<UserModel>> = _singleUser

    init {
        getAllUsers()
    }


    private fun getAllUsers() {
        viewModelScope.launch {
            try {
                val usersList = repository.getUsers()
                _listAllUsers.value = ApiResponse.Success(usersList)
            } catch (e : Exception) {
                Log.e("error", e.message?:"Unexpected Error")
                _listAllUsers.value = ApiResponse.Error(e.message?:"Unexpected Error")
            }
        }
    }

    fun getUserById(id:String) {
        viewModelScope.launch {
            try {
                val user = repository.getUserById(id)
                _singleUser.value = ApiResponse.Success(user)
            } catch (e:Exception) {
                Log.e("error", e.message?:"Unexpected Error")
                _singleUser.value = ApiResponse.Error(e.message?:"Unexpected Error")
            }
        }
    }
}
