package fr.jaetan.core.services.scrapper

import android.util.Log
import fr.jaetan.core.models.data.works.Manga
import fr.jaetan.core.models.data.works.WorkAuthor
import fr.jaetan.core.models.data.works.WorkState
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import it.skrape.selects.html5.*

private val Scrapper.baseUrl by lazy {
    "https://www.nautiljon.com/mangas/"
}
private val Scrapper.staticUrlSearch by lazy {
    "&webcomic=&edition_sup=2&france=&editeur_vf=&editeur_vo=&annee_vf_min=&annee_vf_max=&annee_vo_min=0&annee_vo_max=0&nb_vol_min=0&nb_vol_max=&nb_chapitres_min=0&nb_chapitres_max=&age_min=&age_max=&public_averti=&commerce=&adapte_anime=&prepublie=&societe=&pays=&titre_alternatif=1&titre_alternatif_suite=1&titre_original_latin=1&titre_original=1&has=&tri=0"
}

suspend fun Scrapper.getMangas(search: String): List<Manga> {
    return skrape(HttpFetcher) {
        request { url = "$baseUrl?q=${search.trim().replace(" ", "+")}$staticUrlSearch" }

        extractIt<MangasResult> {
            htmlDocument {
                val titles = getTitlesFromList(this)
                val descriptions = getDescriptionsFromList(this)
                val covers = getCoversFromList(this)

                it.data = titles.mapIndexed { index, title ->
                    Manga(
                        id = index,
                        title = title,
                        description = descriptions[index],
                        coverImageUrl = covers[index]
                    )
                }.toMutableList()
            }
        }.data
    }
}

suspend fun Scrapper.getManga(name: String): Manga? {
    return try {
        skrape(HttpFetcher) {
            request { url = "$baseUrl${name.trim().replace(" ", "+").lowercase()}.html" }

            extractIt<MangaResult> {
                htmlDocument {
                    // val (vfTomesCount, vfWorkState) = getMangaVFStateAndNbrOfTome(this)
                    // val (vfTomesCount, vfWorkState) = getMangaVOStateAndNbrOfTome(this)

                    it.data = Manga(
                        id = 0,
                        title = titleText,
                        description = getFullDescription(this),
                        coverImageUrl = getCover(this),
                        authors = getAuthors(this)
                    )
                }
            }.data
        }
    } catch (e: Exception) {
        Log.e("testt::error", e.toString())
        null
    }
}

// Private functions
private fun Scrapper.getTitlesFromList(doc: Doc): List<String> = try {
    doc.a(".eTitre") {
        findAll {
            map { it.text }
        }
    }
} catch (e: Exception) {
    Log.e("testt::error", e.toString())
    emptyList()
}
private fun Scrapper.getDescriptionsFromList(doc: Doc): List<String> = try {
    doc.td(".left") {
        p {
            findAll {
                map { it.text.removeSuffix(" Lire la suite") }
            }
        }
    }
} catch (e: Exception) {
    Log.e("testt::error", e.toString())
    emptyList()
}
private fun Scrapper.getCoversFromList(doc: Doc): List<String> = try {
    doc.td {
        img {
            findAll {
                map { "https://nautiljon.com/${it.attributes["src"]}" }
            }
        }
    }
} catch (e: Exception) {
    Log.e("testt::error", e.toString())
    emptyList()
}

private fun Scrapper.getCover(doc: Doc): String = try {
    doc.a("#onglets_3_couverture") {
        img {
            findFirst {
                "https://nautiljon.com/${attributes["src"]}"
            }
        }
    }
} catch (e: Exception) {
    Log.e("testt::error", e.toString())
    ""
}
private fun Scrapper.getFullDescription(doc: Doc): String = try {
    doc.div(".description") {
        findFirst {
            text
        }
    }
} catch (e: Exception) {
    Log.e("testt::error", e.toString())
    ""
}

private fun Scrapper.getAuthors(doc: Doc): List<WorkAuthor> = try {
    doc.a(".sim") {
        span {
            findAll {
                map {
                    WorkAuthor(
                        name = it.text
                    )
                }
            }
        }
    }
} catch (e: Exception) {
    Log.e("testt::error", e.toString())
    emptyList()
}

private fun Scrapper.getVFStateAndNbrOfTome(doc: Doc): Pair<Int, WorkState> = try {
    throw IllegalStateException("Not implemented yet")
} catch (e: Exception) {
    Log.e("testt::error", e.toString())
    Pair(0, WorkState.Unknown)
}

private fun Scrapper.getVOStateAndNbrOfTome(doc: Doc): Pair<Int, WorkState> = try {
    throw IllegalStateException("Not implemented yet")
} catch (e: Exception) {
    Log.e("testt::error", e.toString())
    Pair(0, WorkState.Unknown)
}

private fun Scrapper.getGenres(doc: Doc): Pair<Int, WorkState> = try {
    throw IllegalStateException("Not implemented yet")
} catch (e: Exception) {
    Log.e("testt::error", e.toString())
    Pair(0, WorkState.Unknown)
}

data class MangasResult(var data: MutableList<Manga> = mutableListOf())
data class MangaResult(var data: Manga? = null)