package fr.jaetan.jmedia

import fr.jaetan.jmedia.core.models.works.Status
import fr.jaetan.jmedia.core.services.objectbox.AuthorEntity
import fr.jaetan.jmedia.core.services.objectbox.DemographicEntity
import fr.jaetan.jmedia.core.services.objectbox.GenreEntity
import fr.jaetan.jmedia.core.services.objectbox.ImageEntity
import fr.jaetan.jmedia.core.services.objectbox.MangaEntity
import fr.jaetan.jmedia.core.services.objectbox.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.config.DebugFlags
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class MangaObjectBoxTest {
    lateinit var store: BoxStore

    @Before
    fun initialize() {
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
        store = MyObjectBox.builder()
            // Use a custom directory to store the database files in.
            .directory(TEST_DIRECTORY)
            // Optional: add debug flags for more detailed ObjectBox log output.
            .debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
            .build()
    }

    @After
    fun tearDown() {
        store.close()
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
    }

    @Test
    fun put_InsertMangas_MangasNotEmpty() {
        val mangaBox = store.boxFor(MangaEntity::class.java)
        mangaBox.put(MANGA_TEST1)
        mangaBox.put(MANGA_TEST2)
        assertTrue(mangaBox.all.isNotEmpty())
    }

    companion object {
        private val TEST_DIRECTORY = File("objectbox-example/test-db")
        private val AUTHOR_TEST = AuthorEntity(id = 0, name = "Author")
        private val DEMOGRAPHIC_TEST = DemographicEntity(id = 0, name = "Demographic")
        private val GENRE_TEST = GenreEntity(id = 0, name = "Genre")
        private val MANGA_TEST1 = MangaEntity(
            id = 0,
            title = "Oshi no ko",
            synopsis = "My best synopsys",
            status = Status.Unknown.name
        ).also {
            it.image.target = ImageEntity(
                id = 0,
                smallImageUrl = "small image url",
                imageUrl = "image url",
                largeImageUrl = "large image url"
            )
            it.authors.add(AUTHOR_TEST)
            it.demographics.add(DEMOGRAPHIC_TEST)
            it.genres.add(GENRE_TEST)
        }
        private val MANGA_TEST2 = MangaEntity(
            id = 0,
            title = "Chainsaw Man",
            synopsis = "My best synopsys",
            status = Status.Unknown.name
        ).also {
            it.image.target = ImageEntity(
                id = 0,
                smallImageUrl = "small image url",
                imageUrl = "image url",
                largeImageUrl = "large image url"
            )
            it.authors.addAll(listOf(AUTHOR_TEST, AUTHOR_TEST))
            it.demographics.add(DEMOGRAPHIC_TEST)
            it.genres.add(GENRE_TEST)
        }
    }
}