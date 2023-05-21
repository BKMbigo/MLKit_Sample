package com.github.bkmbigo.mlkitsample.ui.screens.text.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import com.github.bkmbigo.mlkitsample.R
import com.google.mlkit.nl.entityextraction.EntityExtractionRemoteModel
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions
import com.google.mlkit.nl.translate.TranslateLanguage

enum class LanguageView(
    val string: Int,
    val languageCodes: List<String>
) {
    AFRIKAANS(R.string.language_afrikaans, listOf("af")),
    AMHARIC(R.string.language_amharic, listOf("am")),
    ARABIC(R.string.language_arabic, listOf("ar", "ar-Latn")),
    AZERBAIJANI(R.string.language_azerbaijani, listOf("az")),
    BELARUSIAN(R.string.language_belarusian, listOf("be")),
    BULGARIAN(R.string.language_bulgarian, listOf("bg", "bg-Latn")),
    BENGALI(R.string.language_bengali, listOf("bn")),
    BOSNIAN(R.string.language_bosnian, listOf("bs")),
    CATALAN(R.string.language_catalan, listOf("ca")),
    CEBUANO(R.string.language_cebuano, listOf("ceb")),
    CORSICAN(R.string.language_corsican, listOf("co")),
    CZECH(R.string.language_czech, listOf("cs")),
    WELSH(R.string.language_welsh, listOf("cy")),
    DANISH(R.string.language_danish, listOf("da")),
    GERMAN(R.string.language_german, listOf("de")),
    GREEK(R.string.language_greek, listOf("el", "el-Latn")),
    ENGLISH(R.string.language_english, listOf("en")),
    ESPERANTO(R.string.language_esperanto, listOf("eo")),
    SPANISH(R.string.language_spanish, listOf("es")),
    ESTONIAN(R.string.language_estonian, listOf("et")),
    BASQUE(R.string.language_basque, listOf("eu")),
    PERSIAN(R.string.language_persian, listOf("fa")),
    FINNISH(R.string.language_finnish, listOf("fi")),
    FILIPINO(R.string.language_filipino, listOf("fil")),
    FRENCH(R.string.language_french, listOf("fr")),
    WESTERN_FRISIAN(R.string.language_western_frisian, listOf("fy")),
    IRISH(R.string.language_irish, listOf("ga")),
    SCOTS_GAELIC(R.string.language_scots_gaelic, listOf("gd")),
    GALICIAN(R.string.language_galician, listOf("gl")),
    GUJARATI(R.string.language_gujarati, listOf("gu")),
    HAUSA(R.string.language_hausa, listOf("ha")),
    HAWAIIAN(R.string.language_hawaiian, listOf("haw")),
    HEBREW(R.string.language_hebrew, listOf("he")),
    HINDI(R.string.language_hindi, listOf("hi", "hi-Latn")),
    HMONG(R.string.language_hmong, listOf("hmn")),
    CROATIAN(R.string.language_croatian, listOf("hr")),
    HAITIAN(R.string.language_haitian, listOf("ht")),
    HUNGARIAN(R.string.language_hungarian, listOf("hu")),
    ARMENIAN(R.string.language_armenian, listOf("hy")),
    INDONESIAN(R.string.language_indonesian, listOf("id")),
    IGBO(R.string.language_igbo, listOf("ig")),
    ICELANDIC(R.string.language_icelandic, listOf("is")),
    ITALIAN(R.string.language_italian, listOf("it")),
    JAPANESE(R.string.language_japanese, listOf("ja", "ja-Latn")),
    JAVANESE(R.string.language_javanese, listOf("jv")),
    GEORGIAN(R.string.language_georgian, listOf("ka")),
    KAZAKH(R.string.language_kazakh, listOf("kk")),
    KHMER(R.string.language_khmer, listOf("km")),
    KANNADA(R.string.language_kannada, listOf("kn")),
    KOREAN(R.string.language_korean, listOf("ko")),
    KURDISH(R.string.language_kurdish, listOf("ku")),
    KYRGYZ(R.string.language_kyrgyz, listOf("ky")),
    LATIN(R.string.language_latin, listOf("la")),
    LUXEMBOURGISH(R.string.language_luxembourgish, listOf("lb")),
    LAO(R.string.language_lao, listOf("lo")),
    LITHUANIAN(R.string.language_lithuanian, listOf("lt")),
    LATVIAN(R.string.language_latvian, listOf("lv")),
    MALAGASY(R.string.language_malagasy, listOf("mg")),
    MAORI(R.string.language_maori, listOf("mi")),
    MACEDONAIN(R.string.language_macedonian, listOf("mk")),
    MALAYALAM(R.string.language_malayalam, listOf("ml")),
    MONGOLIAN(R.string.language_mongolian, listOf("mn")),
    MARATHI(R.string.language_marathi, listOf("mr")),
    MALAY(R.string.language_malay, listOf("ms")),
    MALTESE(R.string.language_maltese, listOf("mt")),
    BURMESE(R.string.language_burmese, listOf("my")),
    NEPALI(R.string.language_nepali, listOf("ne")),
    DUTCH(R.string.language_dutch, listOf("nl")),
    NORWEGIAN(R.string.language_norwegian, listOf("no")),
    NYANJA(R.string.language_nyanja, listOf("ny")),
    PUNJABI(R.string.language_punjabi, listOf("pa")),
    POLISH(R.string.language_polish, listOf("pl")),
    PASHTO(R.string.language_pashto, listOf("ps")),
    PORTUGUESE(R.string.language_portuguese, listOf("pt")),
    ROMANIAN(R.string.language_romanian, listOf("ro")),
    RUSSIAN(R.string.language_russian, listOf("ru", "ru-Latn")),
    SINDHI(R.string.language_sindhi, listOf("sd")),
    SINHALA(R.string.language_sinhala, listOf("si")),
    SLOVAK(R.string.language_slovak, listOf("sk")),
    SLOVENIAN(R.string.language_slovenian, listOf("sl")),
    SAMOAN(R.string.language_samoan, listOf("sm")),
    SHONA(R.string.language_shona, listOf("sn")),
    SOMALI(R.string.language_somali, listOf("so")),
    ALBANIAN(R.string.language_albanian, listOf("sq")),
    SERBIAN(R.string.language_serbian, listOf("sr")),
    SESOTHO(R.string.language_sesotho, listOf("st")),
    SUDANESE(R.string.language_sundanese, listOf("su")),
    SWEDISH(R.string.language_swedish, listOf("sv")),
    SWAHILI(R.string.language_swahili, listOf("sw")),
    TAMIL(R.string.language_tamil, listOf("ta")),
    TELUGU(R.string.language_telugu, listOf("te")),
    TAJIK(R.string.language_tajik, listOf("tg")),
    THAI(R.string.language_thai, listOf("th")),
    TURKISH(R.string.language_turkish, listOf("tr")),
    UKRAINIAN(R.string.language_ukrainian, listOf("uk")),
    URDU(R.string.language_urdu, listOf("ur")),
    UZBEK(R.string.language_uzbek, listOf("uz")),
    VIETMANESE(R.string.language_vietnamese, listOf("vi")),
    XHOSA(R.string.language_xhosa, listOf("xh")),
    YIDDISH(R.string.language_yiddish, listOf("yi")),
    YORUBA(R.string.language_yoruba, listOf("yo")),
    CHINESE(R.string.language_chinese, listOf("zh", "zh-Latn")),
    ;

    companion object {

        private val languageCodeMap = values()
            .map { languageView -> languageView.languageCodes.map { Pair(it, languageView) } }
            .flatten()
            .associate { it }

        @Stable
        @Composable
        fun getLanguageName(languageCode: String) =
            if(languageCode == "und"){
                stringResource(id = R.string.language_unidentified)
            } else{
                languageCodeMap[languageCode]?.let {
                    stringResource(id = it.string)
                } ?: stringResource(id = R.string.language_new_language, languageCode)
            }

        /**
         * Gets the associated [LanguageView] using the provided [languageCode]
         * @return [LanguageView] or null if no value is found
         */
        @Stable
        fun getLanguageView(languageCode: String) = languageCodeMap[languageCode]

    }
}