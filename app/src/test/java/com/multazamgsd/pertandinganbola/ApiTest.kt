package com.multazamgsd.pertandinganbola

import com.multazamgsd.pertandinganbola.api.ApiRepository
import org.junit.Test
import org.mockito.Mockito

class ApiTest {
    @Test
    fun testDoRequest() {
        val api = Mockito.mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=Spanish%20La%20Liga"
        api.doRequest(url)
        Mockito.verify(api).doRequest(url)
    }
}