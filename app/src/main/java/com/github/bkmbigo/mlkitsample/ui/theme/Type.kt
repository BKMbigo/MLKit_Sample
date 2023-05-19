package com.github.bkmbigo.mlkitsample.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.mlkitsample.R

private val defaultTypography = Typography()

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val kalam = GoogleFont("Kalam")
private val asapCondensed = GoogleFont("Asap Condensed")

private val kalamFontFamily = FontFamily(
    Font(
        googleFont = kalam,
        fontProvider = provider,
    )
)

private val asapCondensedFontFamily = FontFamily(
    Font(
        googleFont = asapCondensed,
        fontProvider = provider,
    ),
    Font(
        googleFont = asapCondensed,
        fontProvider = provider,
        weight = FontWeight.Light
    ),
    Font(
        googleFont = asapCondensed,
        fontProvider = provider,
        style = FontStyle.Italic
    ),
    Font(
        googleFont = asapCondensed,
        fontProvider = provider,
        weight = FontWeight.SemiBold
    ),
    Font(
        googleFont = asapCondensed,
        fontProvider = provider,
        weight = FontWeight.Bold
    ),

)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = asapCondensedFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = asapCondensedFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = asapCondensedFontFamily),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = asapCondensedFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = asapCondensedFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = asapCondensedFontFamily),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = asapCondensedFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = asapCondensedFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = asapCondensedFontFamily),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = asapCondensedFontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = asapCondensedFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = asapCondensedFontFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = asapCondensedFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = asapCondensedFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = asapCondensedFontFamily),
)