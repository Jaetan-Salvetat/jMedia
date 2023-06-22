package fr.jaetan.core.repositories

import fr.jaetan.core.models.data.IRepository
import fr.jaetan.core.models.data.works.Manga
import fr.jaetan.core.services.Scrapper

class MangaRepository: IRepository {
    override suspend fun getAll(search: String): List<Manga> {
        return Scrapper.getMangas(search)
    }
}