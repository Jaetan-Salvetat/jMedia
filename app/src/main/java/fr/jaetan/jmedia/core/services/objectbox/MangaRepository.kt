package fr.jaetan.jmedia.core.services.objectbox

object MangaRepository {
    private val mangaBox = ObjectBox.store.boxFor(MangaEntity::class.java)

    fun getNumberOfMangas(): Long {
        return mangaBox.count()
    }

    fun addManga(manga: MangaEntity) {
        mangaBox.put(manga)
    }

    fun close() {
        ObjectBox.store.close()
    }
}