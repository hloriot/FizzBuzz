package com.hloriot.fizzbuzz.presentation.extension

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert

fun SemanticsNodeInteraction.assertEditableText(
    value: String,
    substring: Boolean = false,
    ignoreCase: Boolean = false
): SemanticsNodeInteraction =
    assert(hasEditableText(value, substring = substring, ignoreCase = ignoreCase))


fun SemanticsNodeInteraction.assertNotEditableText(
    value: String,
    substring: Boolean = false,
    ignoreCase: Boolean = false
): SemanticsNodeInteraction =
    assert(hasNotEditableText(value, substring = substring, ignoreCase = ignoreCase))

fun hasEditableText(
    text: String,
    substring: Boolean = false,
    ignoreCase: Boolean = false
): SemanticsMatcher {
    val propertyName = SemanticsProperties.EditableText.name
    return if (substring) {
        SemanticsMatcher(
            "$propertyName contains '$text' (ignoreCase: $ignoreCase) as substring"
        ) {
            it.config.getOrNull(SemanticsProperties.EditableText)
                ?.text?.contains(text, ignoreCase) ?: false
        }
    } else {
        SemanticsMatcher(
            "$propertyName contains '$text' (ignoreCase: $ignoreCase)"
        ) {
            it.config.getOrNull(SemanticsProperties.EditableText)
                ?.text?.equals(text, ignoreCase) ?: false
        }
    }
}

fun hasNotEditableText(
    text: String,
    substring: Boolean = false,
    ignoreCase: Boolean = false
): SemanticsMatcher {
    val propertyName = SemanticsProperties.Text.name
    return if (substring) {
        SemanticsMatcher(
            "$propertyName contains '$text' (ignoreCase: $ignoreCase) as substring"
        ) {
            it.config.getOrNull(SemanticsProperties.Text)
                ?.any { item -> item.text.contains(text, ignoreCase) } ?: false
        }
    } else {
        SemanticsMatcher(
            "$propertyName contains '$text' (ignoreCase: $ignoreCase)"
        ) {
            it.config.getOrNull(SemanticsProperties.Text)
                ?.any { item -> item.text.equals(text, ignoreCase) } ?: false
        }
    }
}