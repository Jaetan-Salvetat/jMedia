package fr.jaetan.core.services

import fr.jaetan.core.models.data.works.Manga
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.img
import it.skrape.selects.html5.p
import it.skrape.selects.html5.td

object Scrapper {
    private val baseUrl by lazy {
        "https://www.nautiljon.com/mangas/"
    }
    private val staticUrlSearch by lazy {
        "&webcomic=&edition_sup=2&france=&editeur_vf=&editeur_vo=&annee_vf_min=&annee_vf_max=&annee_vo_min=0&annee_vo_max=0&nb_vol_min=0&nb_vol_max=&nb_chapitres_min=0&nb_chapitres_max=&age_min=&age_max=&public_averti=&commerce=&adapte_anime=&prepublie=&societe=&pays=&titre_alternatif=1&titre_alternatif_suite=1&titre_original_latin=1&titre_original=1&has=&tri=0"
    }

    suspend fun getMangas(search: String): List<Manga> {
        return skrape(HttpFetcher) {
            request { url = "$baseUrl?q=${search.trim().replace(" ", "+")}$staticUrlSearch" }

            extractIt<MangasResult> {
                htmlDocument {
                    val titles = getMangaTitlesFromList(this)
                    val descriptions = getMangaDescriptionsFromList(this)
                    val covers = getMangaCoversFromList(this)

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

    suspend fun getManga(name: String): Manga {

        return skrape(HttpFetcher) {
            request { url = "$baseUrl${name.trim().replace(" ", "+").lowercase()}.html" }

            extractIt<MangaResult> {
                htmlDocument {
                    it.data = Manga(
                        id = 0,
                        title = titleText,
                        description = getMangaFullDescription(this),
                        coverImageUrl = getMangaCover(this)
                    )
                }
            }.data!!
        }
    }
}

// Private functions
private fun getMangaTitlesFromList(doc: Doc): List<String> = try {
    doc.a(".eTitre") {
        findAll {
            map { it.text }
        }
    }
} catch (e: Exception) {
    emptyList()
}
private fun getMangaDescriptionsFromList(doc: Doc): List<String> = try {
    doc.td(".left") {
        p {
            findAll {
                map { it.text.removeSuffix(" Lire la suite") }
            }
        }
    }
} catch (e: Exception) {
    emptyList()
}
private fun getMangaCoversFromList(doc: Doc): List<String> = try {
    doc.td {
        img {
            findAll {
                map { "https://nautiljon.com/${it.attributes["src"]}" }
            }
        }
    }
} catch (e: Exception) {
    emptyList()
}

private fun getMangaCover(doc: Doc): String = try {
    doc.a("#onglets_3_couverture") {
        img {
            findFirst {
                "https://nautiljon.com/${attributes["src"]}"
            }
        }
    }
} catch (e: Exception) {
    ""
}
private fun getMangaFullDescription(doc: Doc): String = try {
    doc.div(".description") {
        findFirst {
            text
        }
    }
} catch (e: Exception) {
    ""
}

data class MangasResult(var data: MutableList<Manga> = mutableListOf())
data class MangaResult(var data: Manga? = null)
