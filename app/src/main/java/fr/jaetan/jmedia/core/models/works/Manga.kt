package fr.jaetan.jmedia.core.models.works

import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.core.services.objectbox.MangaEntity

data class Manga(
    val id: Long = 0,
    val title: String,
    val synopsis: String?,
    val volumes: Int?,
    val status: Status,
    val image: Image,
    val authors: List<Author>,
    val genres: List<Genre>,
    val demographics: List<Demographic>
)

fun Manga.toBdd(): MangaEntity {
    val manga = MangaEntity(
        id = id,
        title = title,
        synopsis = synopsis,
        volumes = volumes,
        status = status.name
    )

    MainViewModel.mangaRepository.attach(manga)
    manga.image.target = image.toBdd()
    manga.authors.addAll(authors.toBdd())
    manga.genres.addAll(genres.toBdd())
    manga.demographics.addAll(demographics.toBdd())

    return manga
}
