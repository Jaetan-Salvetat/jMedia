package fr.jaetan.jmedia.networking

import fr.jaetan.jmedia.core.networking.BookApi
import fr.jaetan.jmedia.extensions.printDataClassToString
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class BookApiTest {
    @Test
    fun search_ValidRecherche_ReturnNotEmpty() = runTest {
        val books = BookApi.search("oshi no ko")

        books.forEach {
            it.printDataClassToString()
        }

        Assert.assertTrue(books.isNotEmpty())
    }
}