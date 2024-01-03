package fr.jaetan.jmedia.networking

import fr.jaetan.jmedia.core.networking.SerieApi
import fr.jaetan.jmedia.extensions.printDataClassToString
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class SerieApiTest {
    @Test
    fun search_ValidRecherche_ReturnNotEmpty() = runTest {
        val series = SerieApi.search("lucifer")

        series.results.forEach {
            it.printDataClassToString()
        }

        // true because, the test just crash if the request not working
        Assert.assertTrue(series.results.isNotEmpty())
    }
}