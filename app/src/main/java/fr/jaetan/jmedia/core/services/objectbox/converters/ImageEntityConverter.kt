package fr.jaetan.jmedia.core.services.objectbox.converters

import fr.jaetan.jmedia.core.models.works.Image
import fr.jaetan.jmedia.core.services.objectbox.ImageEntity
import io.objectbox.converter.PropertyConverter

object ImageEntityConverter : PropertyConverter<ImageEntity, Image> {
    override fun convertToEntityProperty(databaseValue: Image): ImageEntity {
        return databaseValue.let {
            ImageEntity(id = it.id?.toLong() ?: 0, imageUrl = it.imageUrl, smallImageUrl = it.smallImageUrl, largeImageUrl = it.largeImageUrl)
        }
    }

    override fun convertToDatabaseValue(entityProperty: ImageEntity?): Image? {
        return null
    }
}