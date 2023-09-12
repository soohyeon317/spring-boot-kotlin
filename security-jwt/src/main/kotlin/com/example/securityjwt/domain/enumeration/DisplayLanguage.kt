package com.example.securityjwt.domain.enumeration

import java.util.*

enum class DisplayLanguage {

    KO {
        override fun toLocale(): Locale = Locale.KOREA!!
    },
    EN {
        override fun toLocale(): Locale = Locale.ENGLISH!!
    };

    abstract fun toLocale(): Locale
}