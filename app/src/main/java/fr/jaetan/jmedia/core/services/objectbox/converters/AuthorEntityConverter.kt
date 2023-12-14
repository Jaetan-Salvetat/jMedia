package fr.jaetan.jmedia.core.services.objectbox.converters

import fr.jaetan.jmedia.core.models.works.Author
import fr.jaetan.jmedia.core.services.objectbox.AuthorEntity
import io.objectbox.converter.PropertyConverter

object AuthorEntityConverter : PropertyConverter<AuthorEntity, Author> {
    override fun convertToEntityProperty(databaseValue: Author): AuthorEntity {
        return databaseValue.let {
            AuthorEntity(id = it.id?.toLong() ?: 0, name = it.name)
        }
    }

    override fun convertToDatabaseValue(entityProperty: AuthorEntity?): Author? {
        return null
    }
}