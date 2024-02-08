package fr.jaetan.jmedia.extensions

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


@SuppressLint("SupportAnnotationUsage")
@Composable
fun Int.localized(): String = stringResource(this)