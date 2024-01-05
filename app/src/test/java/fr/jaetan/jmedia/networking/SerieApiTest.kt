package fr.jaetan.jmedia.networking

import fr.jaetan.jmedia.core.networking.SerieApi
import fr.jaetan.jmedia.extensions.printDataClassToString
import fr.jaetan.jmedia.models.works.shared.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SerieApiTest {
    @Test
    fun search() = runTest {
        val series = SerieApi.search("harry")

        series.forEach {
            it.printDataClassToString()
        }

        assertTrue(series.isNotEmpty())
    }

    @Test
    fun getDetails() = runTest {
        val series = SerieApi.search("har")

        series.forEach {
            val serie = SerieApi.getDetails(it.apiId)

            serie.printDataClassToString()

            assertTrue(serie.genres.isNotEmpty())
            assertTrue(serie.status != Status.Unknown)
            assertTrue(serie.seasons.isNotEmpty())
        }
    }
}