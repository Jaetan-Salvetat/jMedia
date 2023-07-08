package fr.jaetan.core.models.data

import fr.jaetan.core.models.data.works.IWork

interface  IRepository {
    suspend fun getAll(search: String): List<IWork>
    suspend fun getOne(name: String): IWork?
}