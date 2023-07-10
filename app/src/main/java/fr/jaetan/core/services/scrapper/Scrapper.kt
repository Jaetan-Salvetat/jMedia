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
import it.skrape.selects.text

object Scrapper
