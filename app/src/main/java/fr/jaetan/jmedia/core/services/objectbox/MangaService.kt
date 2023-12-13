package fr.jaetan.jmedia.core.services.objectbox

import fr.jaetan.jmedia.core.models.works.Manga

class MangaService {
    val mangaBox = ObjectBox.store.boxFor(Manga::class.java)
}