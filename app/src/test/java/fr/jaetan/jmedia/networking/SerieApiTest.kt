package fr.jaetan.jmedia.networking

import fr.jaetan.jmedia.core.networking.SerieApi
import fr.jaetan.jmedia.extensions.printDataClassToString
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class SerieApiTest {
    @Test
    fun search_ValidRecherche_ReturnNotEmpty() = runTest {
        val series = SerieApi.search("harry")

        series.forEach {
            it.printDataClassToString()
        }

        Assert.assertTrue(series.isNotEmpty())
    }
}