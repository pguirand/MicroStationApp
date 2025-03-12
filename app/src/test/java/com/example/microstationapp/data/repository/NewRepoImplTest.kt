package com.example.microstationapp.data.repository

import com.example.microstationapp.data.models.AllProductsModel
import com.example.microstationapp.data.models.DimensionsModel
import com.example.microstationapp.data.models.MetaModel
import com.example.microstationapp.data.models.ProductModel
import com.example.microstationapp.data.models.ReviewModel
import com.example.microstationapp.data.remote.ApiResponse
import com.example.microstationapp.viewmodels.ProductsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test


val fakData = AllProductsModel(
    limit = 0,
    products = listOf(
        ProductModel(
            availabilityStatus = "In Stock",
            brand = "TechBrand",
            category = "Electronics",
            description = "High-performance laptop with 16GB RAM.",
            dimensions = DimensionsModel(
                14.0,
                9.5,
                0.7
            ), // Assuming `DimensionsModel(width, height, depth)`
            discountPercentage = 10.5,
            id = 1,
            images = listOf("https://example.com/laptop1.jpg", "https://example.com/laptop2.jpg"),
            meta = null,
            minimumOrderQuantity = 1,
            price = 999.99,
            rating = 4.5,
            returnPolicy = "30-day return policy",
            reviews = emptyList(),
            shippingInformation = "Ships in 2-3 days",
            sku = "LPT12345",
            stock = 20,
            tags = listOf("laptop", "gaming", "workstation"),
            thumbnail = "https://example.com/thumbnail.jpg",
            title = "Gaming Laptop Pro",
            warrantyInformation = "1-year manufacturer warranty",
            weight = 5
        )
    ),
    skip = 0,
    total = 0

)

@ExperimentalCoroutinesApi
class NewRepoImplTest {

    private lateinit var viewModel: ProductsViewModel
    private val repository : Repository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductsViewModel(repository)
    }


    @Test
    fun `getAllProducts should return success`() = runTest {

        val fakeProducts = fakData

        coEvery { repository.getAllProducts() } returns fakeProducts
        viewModel.getAllProducts()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.listAllProducts.value is ApiResponse.Success)
    }

    @Test
    fun `getAllProducts should return error when repository throws exception`() = runTest{
        val errorMessage = "Network error"

        coEvery { repository.getAllProducts() } throws Exception(errorMessage)

        viewModel.getAllProducts()
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.listAllProducts.value
        assertTrue(result is ApiResponse.Error)
        assertEquals(errorMessage, (result as ApiResponse.Error).exception)
    }

    @Test
    fun `getProduct by Id should return success`() = runTest {
        val productId = "1"
        val fakeProduct = fakData.products?.first() ?: throw IllegalStateException("Fake data issue")

        coEvery { repository.getProductById(productId) } returns fakeProduct

        viewModel.getProductById(productId)
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.productDetail.value
        assertTrue(result is ApiResponse.Success)
        assertEquals(fakeProduct, (result as ApiResponse.Success).data)
    }

    @Test
    fun `getProductById should return error when repository fails`() = runTest {
        val productId = "99"
        val errorMessage = "Product not found"

        coEvery { repository.getProductById(productId) } throws Exception(errorMessage)

        viewModel.getProductById(productId)

        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.productDetail.value
        assertTrue(result is ApiResponse.Error)
        assertEquals(errorMessage, (result as ApiResponse.Error).exception)
    }

    @Test
    fun `verify repository calls when fetching all products`() = runTest {
        coEvery { repository.getAllProducts() } returns fakData

//        viewModel.getAllProducts()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { repository.getAllProducts() }
    }
}