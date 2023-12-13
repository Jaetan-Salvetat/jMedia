package fr.jaetan.jmedia.core.services.objectbox

import fr.jaetan.jmedia.core.services.objectbox.converters.StatusEntityConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class StatusEntity(
    @Id(assignable = true)
    var id: Long = 0,

    var status: String,
)

@Entity
data class ImageEntity(
    @Id(assignable = true)
    var id: Long = 0,

    var imageUrl: String,
    var smallImageUrl: String,
    var largeImageUrl: String,
)

@Entity
data class AuthorEntity(
    @Id(assignable = true)
    var id: Long = 0,

    var name: String
)

@Entity
data class GenreEntity(
    @Id(assignable = true)
    var id: Long = 0,

    var name: String
)

@Entity
data class DemographicEntity(
    @Id(assignable = true)
    var id: Long = 0,

    var name: String
)

data class MangaEntity(
    @Id(assignable = true)
    var id: Long = 0,

    var title: String,
    var synopsis: String?,
    var volumes: Int?,
) {
    @Convert(converter = StatusEntityConverter::class, dbType = String::class)
    lateinit var status: StatusEntity
    lateinit var image: ImageEntity
    lateinit var authors: ToMany<AuthorEntity>
    lateinit var genres: ToMany<GenreEntity>
    lateinit var demographics: ToOne<DemographicEntity>
}