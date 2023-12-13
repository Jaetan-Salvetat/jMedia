package fr.jaetan.jmedia.core.services.objectbox.converters

import fr.jaetan.jmedia.core.services.objectbox.StatusEntity
import io.objectbox.converter.PropertyConverter


class StatusEntityConverter : PropertyConverter<StatusEntity, String> {
    override fun convertToEntityProperty(databaseValue: String?): StatusEntity? {
        return databaseValue?.let { StatusEntity(id = 0, status = it) }
    }

    override fun convertToDatabaseValue(entityProperty: StatusEntity?): String? {
        return entityProperty?.status
    }
}