package fr.jaetan.jmedia.core.services.objectbox

import fr.jaetan.jmedia.core.extensions.printDataClassToString
import fr.jaetan.jmedia.core.models.works.Manga
import io.objectbox.android.AndroidScheduler

class MangaRepository {
    private val mangaBox = ObjectBox.store.boxFor(MangaEntity::class.java)
    val count: Long
        get() = mangaBox.count()

    fun put(manga: MangaEntity): Manga {
        val id = mangaBox.put(manga)
        manga.id = id
        manga.printDataClassToString("testt::MangaRepository.put()")
        return manga.toManga()
    }

    fun observe(onChange: (List<Manga>) -> Unit) {
        mangaBox.query().build()
            .subscribe()
            .on(AndroidScheduler.mainThread())
            .observer { onChange(it.toMangas()) }
    }
}