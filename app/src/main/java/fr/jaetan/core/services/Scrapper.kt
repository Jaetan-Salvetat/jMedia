package fr.jaetan.core.services

import fr.jaetan.core.models.data.works.Manga
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object Scrapper {
    private val baseUrl by lazy {
        "https://www.nautiljon.com/mangas/?q="
    }
    private val staticUrl by lazy {
        "&webcomic=&edition_sup=2&france=&editeur_vf=&editeur_vo=&annee_vf_min=&annee_vf_max=&annee_vo_min=0&annee_vo_max=0&nb_vol_min=0&nb_vol_max=&nb_chapitres_min=0&nb_chapitres_max=&age_min=&age_max=&public_averti=&commerce=&adapte_anime=&prepublie=&societe=&pays=&titre_alternatif=1&titre_alternatif_suite=1&titre_original_latin=1&titre_original=1&has=&tri=0"
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun getMangas(search: String): List<Manga> {
        val doc = Jsoup.connect("$baseUrl${search.trim().replace(" ", "+")}$staticUrl")
            .userAgent("Mozilla")
            .get()

        val titles = getMangaTitles(doc)
        val descriptions = getMangaDescriptions(doc)
        val covers = getMangaCovers(doc)

        return titles.mapIndexed { index, title ->
            Manga(
                id = index,
                title = title,
                description = descriptions[index],
                coverImageUrl = covers[index]
            )
        }
    }
}

// Private functions
private fun getMangaTitles(doc: Document): List<String> = try {
    doc.select(".eTitre").map { it.text() }
} catch (e: Exception) {
    emptyList()
}

private fun getMangaDescriptions(doc: Document): List<String> = try {
    doc.select(".left").map { it.text().removeSuffix(" Lire la suite") }
} catch (e: Exception) {
    emptyList()
}

private fun getMangaCovers(doc: Document): List<String> = try {
    doc.select(".image img")
        .map { "https://nautiljon.com/${it.attr("src")}" }
} catch (e: Exception) {
    emptyList()
}