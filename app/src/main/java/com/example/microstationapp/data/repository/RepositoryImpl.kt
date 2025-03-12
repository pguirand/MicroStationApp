package com.example.microstationapp.data.repository

import com.example.microstationapp.data.models.AllProductsModel
import com.example.microstationapp.data.models.AllUsersModel
import com.example.microstationapp.data.models.ProductModel
import com.example.microstationapp.data.models.UserModel
import com.example.microstationapp.data.remote.ApiCall
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiCall: ApiCall) : Repository {
    override suspend fun getAllProducts(): AllProductsModel{
        return apiCall.getAllProducts()
    }

    override suspend fun getProductById(id: String): ProductModel {
        return apiCall.getProductById(id)
    }

    override suspend fun getUsers(): AllUsersModel {
        return apiCall.getUsers()
    }

    override suspend fun getUserById(id: String): UserModel {
        return apiCall.getUserById(id)
    }

}
