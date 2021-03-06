package com.tiendito.cornetmovies.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Mohamed Salama on 10/14/2020.
 * Email: msalama.tiendito@gmail.com
 */

class MoviesApisTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesApis: MoviesApis

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createWebService(){
        mockWebServer = MockWebServer()
        moviesApis = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApis::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun testDiscoverMovies() {
        enqueueResponse("movies_response.json")
        runBlocking {
            val result = moviesApis.getDiscoverMovies()

            Assert.assertThat(result.body()?.movies?.size, CoreMatchers.`is`(20))
            val movie1 = result.body()?.movies?.get(0)
            Assert.assertThat(movie1?.title, CoreMatchers.`is`("Rogue"))

        }

    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

}