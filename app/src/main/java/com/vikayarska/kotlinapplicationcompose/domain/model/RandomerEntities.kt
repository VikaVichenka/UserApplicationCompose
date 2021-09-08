package com.vikayarska.kotlinapplicationcompose.domain.model

enum class LoremType(val value: String) {
    Normal("normal"),
    Business("business")
}

enum class TextType(val value: String) {
    Paragraphs("paragraphs"),
    Words("words")
}

enum class NameType(val value: String) {
    Firstname("firstname"),
    Surname("surname"),
    FullName("fullname")
}