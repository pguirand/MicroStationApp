package com.example.microstationapp.data.remote


import com.example.microstationapp.common.ApiDetails
import com.example.microstationapp.data.models.AllProductsModel
import com.example.microstationapp.data.models.DimensionsModel
import com.example.microstationapp.data.models.ProductModel
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val fakeDataJson = """
{
  "limit": 10,
  "products": [
    {
      "availabilityStatus": "In Stock",
      "brand": "TechBrand",
      "category": "Electronics",
      "description": "High-performance laptop with 16GB RAM.",
      "dimensions": {
        "width": 14.0,
        "height": 9.5,
        "depth": 0.7
      },
      "discountPercentage": 10.5,
      "id": 1,
      "images": ["https://example.com/laptop1.jpg", "https://example.com/laptop2.jpg"],
      "meta": null,
      "minimumOrderQuantity": 1,
      "price": 999.99,
      "rating": 4.5,
      "returnPolicy": "30-day return policy",
      "reviews": [],
      "shippingInformation": "Ships in 2-3 days",
      "sku": "LPT12345",
      "stock": 20,
      "tags": ["laptop", "gaming", "workstation"],
      "thumbnail": "https://example.com/thumbnail.jpg",
      "title": "Gaming Laptop Pro",
      "warrantyInformation": "1-year manufacturer warranty",
      "weight": 5
    }
  ],
  "skip": 0,
  "total": 1
}
""".trimIndent()


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
class ApiCallTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiCall: ApiCall

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        // ✅ Override BASE_URL with MockWebServer URL
        val testBaseUrl = mockWebServer.url("/").toString()

        apiCall = Retrofit.Builder()
            .baseUrl(testBaseUrl) // ✅ Use MockWebServer as the API base URL
            .addConverterFactory(GsonConverterFactory.create()) // ✅ Convert JSON to objects
            .build()
            .create(ApiCall::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getAllProducts should return product list successfully`() = runBlocking {
        val fakeDataJson = """
        {
          "limit": 10,
          "products": [
            {
              "availabilityStatus": "In Stock",
              "brand": "TechBrand",
              "category": "Electronics",
              "description": "High-performance laptop with 16GB RAM.",
              "dimensions": {
                "width": 14.0,
                "height": 9.5,
                "depth": 0.7
              },
              "discountPercentage": 10.5,
              "id": 1,
              "images": ["https://example.com/laptop1.jpg", "https://example.com/laptop2.jpg"],
              "meta": null,
              "minimumOrderQuantity": 1,
              "price": 999.99,
              "rating": 4.5,
              "returnPolicy": "30-day return policy",
              "reviews": [],
              "shippingInformation": "Ships in 2-3 days",
              "sku": "LPT12345",
              "stock": 20,
              "tags": ["laptop", "gaming", "workstation"],
              "thumbnail": "https://example.com/thumbnail.jpg",
              "title": "Gaming Laptop Pro",
              "warrantyInformation": "1-year manufacturer warranty",
              "weight": 5
            }
          ],
          "skip": 0,
          "total": 1
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(fakeDataJson)
                .setResponseCode(200)
        )

        val response = apiCall.getAllProducts()

        assertNotNull(response)
        assertEquals(1, response.products?.size)
        assertEquals("Gaming Laptop Pro", response.products?.first()?.title)
    }

    @Test
    fun `getProductById should return a single product successfully`() = runBlocking {
        val productJson = """
        {
          "availabilityStatus": "In Stock",
          "brand": "TechBrand",
          "category": "Electronics",
          "description": "High-performance laptop with 16GB RAM.",
          "dimensions": {
            "width": 14.0,
            "height": 9.5,
            "depth": 0.7
          },
          "discountPercentage": 10.5,
          "id": 1,
          "images": ["https://example.com/laptop1.jpg", "https://example.com/laptop2.jpg"],
          "meta": null,
          "minimumOrderQuantity": 1,
          "price": 999.99,
          "rating": 4.5,
          "returnPolicy": "30-day return policy",
          "reviews": [],
          "shippingInformation": "Ships in 2-3 days",
          "sku": "LPT12345",
          "stock": 20,
          "tags": ["laptop", "gaming", "workstation"],
          "thumbnail": "https://example.com/thumbnail.jpg",
          "title": "Gaming Laptop Pro",
          "warrantyInformation": "1-year manufacturer warranty",
          "weight": 5
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(productJson)
                .setResponseCode(200)
        )

        val response = apiCall.getProductById("1")

        assertNotNull(response)
        assertEquals(1, response.id)
        assertEquals("Gaming Laptop Pro", response.title)
    }
}
