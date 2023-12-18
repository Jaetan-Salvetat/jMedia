package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.jaetan.jmedia.core.models.WorkType
import fr.jaetan.jmedia.core.models.works.Author
import fr.jaetan.jmedia.core.models.works.Demographic
import fr.jaetan.jmedia.core.models.works.Genre
import fr.jaetan.jmedia.core.models.works.Image
import fr.jaetan.jmedia.core.models.works.Manga
import fr.jaetan.jmedia.core.models.works.Status

class LibraryViewModel: ViewModel() {
val mangaList = List(25) {
        Manga(
            0,
            "Nanaka's World",
            "Nanaka is a cute young girl that just woke up in a world where it seems she have a full controll on it",
            5,
            Status.Finished,
            Image(
                0,
                "https://cdn.discordapp.com/attachments/642109992698118147/1177694357843878008/DALLE_2023-11-24_20.36.12_-_Create_an_anime-style_portrait_using_the_previously_generated_left_image_with_a_dark_background_to_make_it_suitable_for_use_as_a_wallpaper._The_portr.png?ex=6585e557&is=65737057&hm=35efadd6ab2f3be108b6982362436f8f89724fe3b91d722d1e7507cd31e78c45&",
                "https://cdn.discordapp.com/attachments/642109992698118147/1177694357843878008/DALLE_2023-11-24_20.36.12_-_Create_an_anime-style_portrait_using_the_previously_generated_left_image_with_a_dark_background_to_make_it_suitable_for_use_as_a_wallpaper._The_portr.png?ex=6585e557&is=65737057&hm=35efadd6ab2f3be108b6982362436f8f89724fe3b91d722d1e7507cd31e78c45&",
                "https://cdn.discordapp.com/attachments/642109992698118147/1177694357843878008/DALLE_2023-11-24_20.36.12_-_Create_an_anime-style_portrait_using_the_previously_generated_left_image_with_a_dark_background_to_make_it_suitable_for_use_as_a_wallpaper._The_portr.png?ex=6585e557&is=65737057&hm=35efadd6ab2f3be108b6982362436f8f89724fe3b91d722d1e7507cd31e78c45&"),
            listOf(
                Author(0, "Nakaniel"),
                Author(0, "Nako"),
                Author(0, "Aneko")
            ),
            listOf(
                Genre(0, "Comic"),
                Genre(1, "Slice of life")
            ),
            listOf(
                Demographic(0, "Seinen")
            )
        )
    }
}