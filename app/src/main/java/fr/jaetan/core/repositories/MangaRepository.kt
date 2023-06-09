package fr.jaetan.core.repositories

import fr.jaetan.core.models.data.IRepository
import fr.jaetan.core.models.data.works.IWork
import fr.jaetan.core.models.data.works.Manga
import fr.jaetan.core.services.scrapper.Scrapper
import fr.jaetan.core.services.scrapper.getMangas
import fr.jaetan.core.services.scrapper.getManga

class MangaRepository: IRepository {
    override suspend fun getAll(search: String): List<Manga> {
        return Scrapper.getMangas(search)
    }

    override suspend fun getOne(name: String): IWork? {
        return Scrapper.getManga(name)
    }
}