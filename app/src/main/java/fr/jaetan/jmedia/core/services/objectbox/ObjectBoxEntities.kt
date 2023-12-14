package fr.jaetan.jmedia.core.services.objectbox

import fr.jaetan.jmedia.core.models.works.Author
import fr.jaetan.jmedia.core.models.works.Demographic
import fr.jaetan.jmedia.core.models.works.Genre
import fr.jaetan.jmedia.core.models.works.Image
import fr.jaetan.jmedia.core.models.works.Manga
import fr.jaetan.jmedia.core.models.works.Status
import fr.jaetan.jmedia.core.models.works.fromString
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class ImageEntity(
    @Id var id: Long = 0,
    var imageUrl: String = "",
    var smallImageUrl: String = "",
    var largeImageUrl: String = "",
)

@Entity
data class AuthorEntity(
    @Id var id: Long = 0,
    var name: String = ""
)

@Entity
data class GenreEntity(
    @Id var id: Long = 0,
    var name: String = ""
)

@Entity
data class DemographicEntity(
    @Id var id: Long = 0,
    var name: String = ""
)

@Entity
data class MangaEntity(
    @Id var id: Long = 0,
    var title: String = "",
    var synopsis: String? = null,
    var volumes: Int? = null,
    var status: String = ""
) {
    lateinit var image: ToOne<ImageEntity>
    lateinit var authors: ToMany<AuthorEntity>
    lateinit var genres: ToMany<GenreEntity>
    lateinit var demographics: ToMany<DemographicEntity>
}
// Converters
fun ImageEntity.toImage(): Image = Image(
    imageUrl = imageUrl,
    smallImageUrl = smallImageUrl,
    largeImageUrl = largeImageUrl
)

fun List<AuthorEntity>.toAuthors(): List<Author> = map {
    Author(
        id = it.id,
        name = it.name
    )
}

fun List<GenreEntity>.toGenres(): List<Genre> = map {
    Genre(
        id = it.id,
        name = it.name
    )
}

fun List<DemographicEntity>.toDemographics(): List<Demographic> = map {
    Demographic(
        id = it.id,
        name = it.name
    )
}

fun MangaEntity.toManga(): Manga = Manga(
    id = id,
    title = title,
    synopsis = synopsis,
    volumes = volumes,
    status = Status.fromString(status),
    image = image.target.toImage(),
    authors = authors.toAuthors(),
    genres = genres.toGenres(),
    demographics = demographics.toDemographics()
)

