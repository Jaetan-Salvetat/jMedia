package fr.jaetan.jmedia.core.services.objectbox

import fr.jaetan.jmedia.core.services.objectbox.converters.AuthorEntityConverter
import fr.jaetan.jmedia.core.services.objectbox.converters.DemographicEntityConverter
import fr.jaetan.jmedia.core.services.objectbox.converters.GenreEntityConverter
import fr.jaetan.jmedia.core.services.objectbox.converters.ImageEntityConverter
import fr.jaetan.jmedia.core.services.objectbox.converters.StatusEntityConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class StatusEntity(
    @Id
    var id: Long = 0,

    var status: String,
)

@Entity
data class ImageEntity(
    @Id
    var id: Long = 0,

    var imageUrl: String,
    var smallImageUrl: String,
    var largeImageUrl: String,
)

@Entity
data class AuthorEntity(
    @Id
    var id: Long = 0,

    var name: String
)

@Entity
data class GenreEntity(
    @Id
    var id: Long = 0,

    var name: String
)

@Entity
data class DemographicEntity(
    @Id
    var id: Long = 0,

    var name: String
)

data class MangaEntity(
    @Id
    var id: Long = 0,

    var title: String,
    var synopsis: String?,
    var volumes: Int?,
) {
    @Convert(converter = StatusEntityConverter::class, dbType = String::class)
    lateinit var status: StatusEntity
    @Convert(converter = ImageEntityConverter::class, dbType = String::class)
    lateinit var image: ImageEntity
    @Convert(converter = AuthorEntityConverter::class, dbType = String::class)
    lateinit var authors: ToMany<AuthorEntity>
    @Convert(converter = GenreEntityConverter::class, dbType = String::class)
    lateinit var genres: ToMany<GenreEntity>
    @Convert(converter = DemographicEntityConverter::class, dbType = String::class)
    lateinit var demographics: ToMany<DemographicEntity>
}