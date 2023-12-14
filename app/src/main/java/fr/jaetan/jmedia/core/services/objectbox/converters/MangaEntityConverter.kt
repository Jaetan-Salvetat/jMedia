package fr.jaetan.jmedia.core.services.objectbox.converters

import fr.jaetan.jmedia.core.models.works.Manga
import fr.jaetan.jmedia.core.services.objectbox.MangaEntity
import io.objectbox.converter.PropertyConverter

object MangaEntityConverter : PropertyConverter<MangaEntity, Manga> {
    override fun convertToEntityProperty(databaseValue: Manga): MangaEntity {
        val myManga = MangaEntity(
            id = databaseValue.id?.toLong() ?: 0,
            title = databaseValue.title,
            synopsis = databaseValue.synopsis,
            volumes = databaseValue.volumes
        )
        myManga.apply {
            status = StatusEntityConverter.convertToEntityProperty(databaseValue.status.name)
            image = ImageEntityConverter.convertToEntityProperty(databaseValue.image)
            authors.addAll(databaseValue.authors.map { AuthorEntityConverter.convertToEntityProperty(it) })
            //myMangaEntity.authors = ToMany(AuthorEntity::class.java, myMangaEntity, MangaEntity_.authors)
            genres.addAll(databaseValue.genres.map { GenreEntityConverter.convertToEntityProperty(it) })
            demographics.addAll(databaseValue.demographics.map { DemographicEntityConverter.convertToEntityProperty(it) })
        }
        return myManga
    }

    override fun convertToDatabaseValue(entityProperty: MangaEntity?): Manga? {
        /*
        return entityProperty?.let {
            Manga(
                id = it.id.toInt(),
                title = it.title,
                synopsis = it.synopsis,
                volumes = it.volumes,
                status = StatusEntityConverter.convertToDatabaseValue(it.status) ?: Status.Unknown,
                image = ImageEntityConverter.convertToDatabaseValue(it.image) ?: Image(),
                authors = it.authors.map { AuthorEntityConverter.convertToDatabaseValue(it) },
                genres = it.genres.map { GenreEntityConverter.convertToDatabaseValue(it) ?: Genre() },
                demographics = DemographicEntityConverter.convertToDatabaseValue(it.demographics) ?: Demographic()
            )
        }

         */
        return null
    }
}