package fr.jaetan.jmedia.app.library

import androidx.lifecycle.ViewModel
import fr.jaetan.jmedia.models.works.Author
import fr.jaetan.jmedia.models.works.Demographic
import fr.jaetan.jmedia.models.works.Genre
import fr.jaetan.jmedia.models.works.Image
import fr.jaetan.jmedia.models.works.Manga
import fr.jaetan.jmedia.models.works.Status

class LibraryViewModel: ViewModel() {
val mangaList = List(25) {
        Manga(
            title = "Nanaka's World",
            "Nanaka is a cute young girl that just woke up in a world where it seems she have a full controll on it",
            Image(
                imageUrl = "https://cdn.discordapp.com/attachments/642109992698118147/1177694357843878008/DALLE_2023-11-24_20.36.12_-_Create_an_anime-style_portrait_using_the_previously_generated_left_image_with_a_dark_background_to_make_it_suitable_for_use_as_a_wallpaper._The_portr.png?ex=6585e557&is=65737057&hm=35efadd6ab2f3be108b6982362436f8f89724fe3b91d722d1e7507cd31e78c45&",
                smallImageUrl = "https://cdn.discordapp.com/attachments/642109992698118147/1177694357843878008/DALLE_2023-11-24_20.36.12_-_Create_an_anime-style_portrait_using_the_previously_generated_left_image_with_a_dark_background_to_make_it_suitable_for_use_as_a_wallpaper._The_portr.png?ex=6585e557&is=65737057&hm=35efadd6ab2f3be108b6982362436f8f89724fe3b91d722d1e7507cd31e78c45&",
                largeImageUrl = "https://cdn.discordapp.com/attachments/642109992698118147/1177694357843878008/DALLE_2023-11-24_20.36.12_-_Create_an_anime-style_portrait_using_the_previously_generated_left_image_with_a_dark_background_to_make_it_suitable_for_use_as_a_wallpaper._The_portr.png?ex=6585e557&is=65737057&hm=35efadd6ab2f3be108b6982362436f8f89724fe3b91d722d1e7507cd31e78c45&"),
            status = Status.Finished,

            authors = listOf(
                Author(name = "Nakaniel"),
                Author(name = "Nako"),
                Author(name = "Aneko")
            ),
            genres = listOf(
                Genre(name = "Comic"),
                Genre(name = "Slice of life")
            ),
            demographics = listOf(
                Demographic(name = "Seinen")
            ),
            rating = null,
            volumes = null
        )
    }
}