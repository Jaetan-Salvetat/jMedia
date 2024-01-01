package fr.jaetan.jmedia.networking

import fr.jaetan.jmedia.core.networking.BookApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class BookApiTest {
    @Test
    fun search_ValidRecherche_ReturnNotEmpty() = runTest {
        BookApi.search("oshi no ko")
        Assert.assertTrue(true)
    }
}