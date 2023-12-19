package fr.jaetan.jmedia.core.services.objectbox.repositories

import fr.jaetan.jmedia.core.extensions.isNotNull
import fr.jaetan.jmedia.core.services.objectbox.DemographicEntity
import fr.jaetan.jmedia.core.services.objectbox.GenreEntity
import fr.jaetan.jmedia.core.services.objectbox.ImageEntity
import fr.jaetan.jmedia.core.services.objectbox.MangaEntity
import fr.jaetan.jmedia.core.services.objectbox.ObjectBox
import io.objectbox.android.ObjectBoxLiveData

class MangaRepository {
    private val mangaBox = ObjectBox.store.boxFor(MangaEntity::class.java)
    private val authorRepository = AuthorRepository()
    private val imageBox = ObjectBox.store.boxFor(ImageEntity::class.java)
    private val genreBox = ObjectBox.store.boxFor(GenreEntity::class.java)
    private val demographicBox = ObjectBox.store.boxFor(DemographicEntity::class.java)

    init {
        ObjectBox.removeAll()
    }

    val observer: ObjectBoxLiveData<MangaEntity>
        get() = ObjectBoxLiveData(mangaBox.query().build())

    fun put(manga: MangaEntity) {
        manga.authors.forEachIndexed { index, author ->
            val dbAuthor = authorRepository.find(author)

            if (dbAuthor.isNotNull()) {
                manga.authors[index] = dbAuthor!!
            }
        }
        mangaBox.put(manga)
    }

    fun attach(manga: MangaEntity) {
        mangaBox.attach(manga)
    }

    fun remove(manga: MangaEntity) {
        mangaBox.remove(manga)
    }
}