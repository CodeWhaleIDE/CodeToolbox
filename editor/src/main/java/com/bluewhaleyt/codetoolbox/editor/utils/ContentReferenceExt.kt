package com.bluewhaleyt.codetoolbox.editor.utils

import io.github.rosemoe.sora.lang.completion.CompletionHelper
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.util.MyCharacter

internal fun ContentReference.getPrefix(position: CharPosition) =
    CompletionHelper.computePrefix(this, position, MyCharacter::isJavaIdentifierPart)