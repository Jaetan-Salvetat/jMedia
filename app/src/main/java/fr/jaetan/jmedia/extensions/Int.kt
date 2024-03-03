package fr.jaetan.jmedia.extensions

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource


@SuppressLint("SupportAnnotationUsage")
@Composable
fun Int.localized(vararg args: Any): String = stringResource(this, *args)

@SuppressLint("SupportAnnotationUsage")
@Composable
fun Int.toPainter(): Painter = painterResource(this)