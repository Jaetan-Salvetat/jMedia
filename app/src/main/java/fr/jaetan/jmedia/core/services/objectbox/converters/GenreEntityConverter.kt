package fr.jaetan.jmedia.core.services.objectbox.converters

import fr.jaetan.jmedia.core.models.works.Genre
import fr.jaetan.jmedia.core.services.objectbox.GenreEntity
import io.objectbox.converter.PropertyConverter

object GenreEntityConverter : PropertyConverter<GenreEntity, Genre> {
    override fun convertToEntityProperty(databaseValue: Genre): GenreEntity {
        return databaseValue.let {
            GenreEntity(id = it.id?.toLong() ?: 0, name = it.name)
        }
    }

    override fun convertToDatabaseValue(entityProperty: GenreEntity?): Genre? {
        return null
    }
}