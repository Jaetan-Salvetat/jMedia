package fr.jaetan.jmedia.core.services.objectbox.repositories

import fr.jaetan.jmedia.core.services.objectbox.AuthorEntity
import fr.jaetan.jmedia.core.services.objectbox.ObjectBox
import io.objectbox.kotlin.query

class AuthorRepository {
    private val authorBox = ObjectBox.store.boxFor(AuthorEntity::class.java)

    fun find(author: AuthorEntity): AuthorEntity? {
        val dbAuthor = authorBox.query {
            filter { it.name == author.name }
        }.findUnique()

        return dbAuthor
    }
}