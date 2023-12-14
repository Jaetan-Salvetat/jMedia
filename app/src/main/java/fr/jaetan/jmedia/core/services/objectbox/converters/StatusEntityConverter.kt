package fr.jaetan.jmedia.core.services.objectbox.converters

import fr.jaetan.jmedia.core.models.works.Status
import fr.jaetan.jmedia.core.services.objectbox.StatusEntity
import io.objectbox.converter.PropertyConverter


object StatusEntityConverter : PropertyConverter<StatusEntity, String> {
    override fun convertToEntityProperty(databaseValue: String): StatusEntity {
        return StatusEntity(id = 0, status = databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: StatusEntity?): String? {
        return null
    }
}