package fr.jaetan.jmedia.networking

import fr.jaetan.jmedia.core.networking.MangaApi
import fr.jaetan.jmedia.extensions.printDataClassToString
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class MangaApiTest {
    @Test
    fun search_ValidRecherche_ReturnNotEmpty() = runTest {
        val mangas = MangaApi.search("oshi no ko")
        println("Number of mangas: ${mangas.size}")
        mangas.forEach { print(it.printDataClassToString()) }
        assertTrue(mangas.isNotEmpty())
    }
}