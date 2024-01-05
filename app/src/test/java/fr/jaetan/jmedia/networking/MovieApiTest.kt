package fr.jaetan.jmedia.networking

import fr.jaetan.jmedia.core.networking.MovieApi
import fr.jaetan.jmedia.extensions.printDataClassToString
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieApiTest {
    @Test
    fun search_ValidRecherche_ReturnNotEmpty() = runTest {
        val movies = MovieApi.search("harry potter")

        movies.forEach {
            it.printDataClassToString()
        }

        assertTrue(movies.isNotEmpty())
    }
}