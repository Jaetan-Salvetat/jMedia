package fr.jaetan.jmedia.networking

import fr.jaetan.jmedia.core.networking.MovieApi
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.printDataClassToString
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieApiTest {
    @Test
    fun search() = runTest {
        val movies = MovieApi.search("harry potter")

        movies.forEach {
            it.printDataClassToString()
        }

        assertTrue(movies.isNotEmpty())
    }

    @Test
    fun getDetail() = runTest {
        MovieApi.search("har").forEach {
            val movie = MovieApi.getDetail(it.apiId)

            movie.printDataClassToString()

            assertTrue(movie.genres.isNotEmpty())
            assertTrue(movie.releaseDate.isNotNull())
        }
    }
}