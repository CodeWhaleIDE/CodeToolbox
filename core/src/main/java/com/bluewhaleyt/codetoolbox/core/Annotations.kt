package com.bluewhaleyt.codetoolbox.core

@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    message = """
        This API is for internal use of CodeToolbox library module. You might not need
        to utilize it.
    """
)
annotation class CodeToolboxInternalApi