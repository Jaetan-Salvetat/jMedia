package fr.jaetan.jmedia

import fr.jaetan.jmedia.core.extensions.dataClassToString
import fr.jaetan.jmedia.core.networking.MangaApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class MangaApiTest {
    @Test
    fun search_ValidRecherche_ReturnNotEmpty() = runTest {
        val mangas = MangaApi.search("oshi no ko")
        println("Number of mangas: ${mangas.size}")
        mangas.forEach { print(it.dataClassToString()) }
        assertTrue(mangas.isNotEmpty())
    }
}