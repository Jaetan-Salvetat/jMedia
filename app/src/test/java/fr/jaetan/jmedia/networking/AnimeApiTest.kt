package fr.jaetan.jmedia.networking

import fr.jaetan.jmedia.core.networking.AnimeApi
import fr.jaetan.jmedia.extensions.printDataClassToString
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class AnimeApiTest {
    @Test
    fun search_ValidRecherche_ReturnNotEmpty() = runTest {
        val animes = AnimeApi.search("oshi no ko")
        println("Number of mangas: ${animes.size}")
        animes.forEach { print(it.printDataClassToString()) }
        assertTrue(animes.isNotEmpty())
    }
}