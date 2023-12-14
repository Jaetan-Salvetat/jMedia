package fr.jaetan.jmedia.core.services.objectbox.converters

import fr.jaetan.jmedia.core.models.works.Demographic
import fr.jaetan.jmedia.core.services.objectbox.DemographicEntity
import io.objectbox.converter.PropertyConverter

object DemographicEntityConverter : PropertyConverter<DemographicEntity, Demographic> {
    override fun convertToEntityProperty(databaseValue: Demographic): DemographicEntity {
        return databaseValue.let {
            DemographicEntity(id = it.id?.toLong() ?: 0, name = it.name)
        }
    }

    override fun convertToDatabaseValue(entityProperty: DemographicEntity?): Demographic? {
        return null
    }
}