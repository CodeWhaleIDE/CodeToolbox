package com.bluewhaleyt.codetoolbox.language.java.completion

import android.content.Context
import com.bluewhaleyt.codetoolbox.core.completion.Completion
import com.bluewhaleyt.codetoolbox.core.completion.CompletionItem
import com.bluewhaleyt.codetoolbox.core.utils.readFile
import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.utils.currentComposingText

class JavaCompletion(
    override val context: Context,
    override val currentComposingText: String
) : Completion {

    override val snippetAssetsDirPath = "snippets-java"

    override val items
        get() = keywords + snippets

    private val keywords = listOf(
        CompletionItem.Keyword(
            prefix = "abstract",
            description = "Used to declare a class or method that is incomplete and needs to be extended or overridden."
        ),
        CompletionItem.Keyword(
            prefix = "assert",
            description = "Used to test assumptions about the program's state during runtime."
        ),
        CompletionItem.Keyword(
            prefix = "boolean",
            description = "A primitive data type representing true or false values."
        ),
        CompletionItem.Keyword(
            prefix = "break",
            description = "Used to terminate a loop or switch statement and continue execution after the loop or switch."
        ),
        CompletionItem.Keyword(
            prefix = "byte",
            description = "A primitive data type representing a signed 8-bit integer."
        ),
        CompletionItem.Keyword(
            prefix = "case",
            description = "Used in a switch statement to specify different cases for the value being evaluated."
        ),
        CompletionItem.Keyword(
            prefix = "catch",
            description = "Used to handle exceptions in exception handling blocks."
        ),
        CompletionItem.Keyword(
            prefix = "char",
            description = "A primitive data type representing a single character."
        ),
        CompletionItem.Keyword(
            prefix = "class",
            description = "Used to define a class in Java."
        ),
        CompletionItem.Keyword(
            prefix = "continue",
            description = "Used to skip the rest of the loop and continue with the next iteration."
        ),
        CompletionItem.Keyword(
            prefix = "default",
            description = "Used in a switch statement to specify the default case when none of the other cases match."
        ),
        CompletionItem.Keyword(
            prefix = "do",
            description = "Used to create a do-while loop, which executes a block of code at least once."
        ),
        CompletionItem.Keyword(
            prefix = "double",
            description = "A primitive data type representing a double-precision floating-point number."
        ),
        CompletionItem.Keyword(
            prefix = "else",
            description = "Used in an if-else statement to specify the block of code to be executed if the condition is false."
        ),
        CompletionItem.Keyword(
            prefix = "enum",
            description = "Used to declare an enumerated type, which represents a fixed set of constants."
        ),
        CompletionItem.Keyword(
            prefix = "extends",
            description = "Used to indicate that a class is derived from another class (inheritance)."
        ),
        CompletionItem.Keyword(
            prefix = "final",
            description = "Used to declare that a variable, method, or class cannot be changed or extended."
        ),
        CompletionItem.Keyword(
            prefix = "finally",
            description = "Used in exception handling to specify a block of code that will always be executed."
        ),
        CompletionItem.Keyword(
            prefix = "float",
            description = "A primitive data type representing a single-precision floating-point number."
        ),
        CompletionItem.Keyword(
            prefix = "for",
            description = "Used to create a for loop, which repeatedly executes a block of code based on a specified condition."
        ),
        CompletionItem.Keyword(
            prefix = "if",
            description = "Used to perform a conditional execution of a block of code based on a specified condition."
        ),
        CompletionItem.Keyword(
            prefix = "implements",
            description = "Used to indicate that a class implements an interface."
        ),
        CompletionItem.Keyword(
            prefix = "import",
            description = "Used to import classes, interfaces, or packages from other packages."
        ),
        CompletionItem.Keyword(
            prefix = "instanceof",
            description = "Used to check if an object is an instance of a particular class or implements a particular interface."
        ),
        CompletionItem.Keyword(
            prefix = "int",
            description = "A primitive data type representing a 32-bit signed integer."
        ),
        CompletionItem.Keyword(
            prefix = "interface",
            description = "Used to declare an interface, which defines a contract for implementing classes."
        ),
        CompletionItem.Keyword(
            prefix = "long",
            description = "A primitive data type representing a 64-bit signed integer."
        ),
        CompletionItem.Keyword(
            prefix = "native",
            description = "Used to indicate that a method is implemented in native code (code written in another programming language)."
        ),
        CompletionItem.Keyword(
            prefix = "new",
            description = "Used to create an instance of a class or an array."
        ),
        CompletionItem.Keyword(
            prefix = "package",
            description = "Used to declare the package to which a class belongs."
        ),
        CompletionItem.Keyword(
            prefix = "private",
            description = "Used to specify that a variable, method, or class is accessible only within its own class."
        ),
        CompletionItem.Keyword(
            prefix = "protected",
            description = "Used to specify that a variable, method, or class is accessible within its own package and subclasses."
        ),
        CompletionItem.Keyword(
            prefix = "public",
            description = "Used to specify that a variable, method, or class is accessible from any other class."
        ),
        CompletionItem.Keyword(
            prefix = "return",
            description = "Used to exit a method and return a value."
        ),
        CompletionItem.Keyword(
            prefix = "short",
            description = "A primitive data type representing a 16-bit signed integer."
        ),
        CompletionItem.Keyword(
            prefix = "static",
            description = "Used to declare a variable or method as belonging to the class rather than an instance of the class."
        ),
        CompletionItem.Keyword(
            prefix = "super",
            description = "Used to refer to the superclass or parent class."
        ),
        CompletionItem.Keyword(
            prefix = "switch",
            description = "Used to create a switch statement, which selects one of many code blocks to be executed."
        ),
        CompletionItem.Keyword(
            prefix = "synchronized",
            description = "Used to create a synchronized block of code, ensuring that only one thread can access it at a time."
        ),
        CompletionItem.Keyword(
            prefix = "this",
            description = "Used to refer to the current instance of a class."
        ),
        CompletionItem.Keyword(
            prefix = "throw",
            description = "Used to throw an exception explicitly."
        ),
        CompletionItem.Keyword(
            prefix = "throws",
            description = "Used to declare that a method may throw one or more specified exceptions."
        ),
        CompletionItem.Keyword(
            prefix = "transient",
            description = "Used to indicate that a variable should not be serialized when an object is converted to a byte stream."
        ),
        CompletionItem.Keyword(
            prefix = "try",
            description = "Used to create a try-catch block for exception handling."
        ),
        CompletionItem.Keyword(
            prefix = "void",
            description = "Used to specify that a method does not return a value."
        ),
        CompletionItem.Keyword(
            prefix = "volatile",
            description = "Used to indicate that a variable may be modified by multiple threads."
        ),
        CompletionItem.Keyword(
            prefix = "while",
            description = "Used to create a while loop, which repeatedly executes a block of code based on a specified condition."
        ),
        CompletionItem.Keyword(
            prefix = "true",
            description = "A literal representing the boolean value true."
        ),
        CompletionItem.Keyword(
            prefix = "false",
            description = "A literal representing the boolean value false."
        ),
        CompletionItem.Keyword(
            prefix = "null",
            description = "A literal representing the absence of a value or a null reference."
        )
    )

    private val contextualKeywords = listOf(
        CompletionItem.Keyword(
            prefix = "export",
            description = "Used in module declarations to specify a package that is accessible to other modules. It allows controlled visibility of packages for modules."
        ),
        CompletionItem.Keyword(
            prefix = "module",
            description = "Used to declare a module, which is a self-contained unit of code with its own namespace. Modules provide encapsulation and control over the accessibility of code."
        ),
        CompletionItem.Keyword(
            prefix = "non-sealed",
            description = "Used to declare a class or interface that can be extended or implemented by any class or interface, even if it is declared within a sealed hierarchy."
        ),
        CompletionItem.Keyword(
            prefix = "open",
            description = "Used in module declarations to make a package accessible and allow reflective access to all types within the package. It relaxes the encapsulation restrictions."
        ),
        CompletionItem.Keyword(
            prefix = "opens",
            description = "Used in module declarations to make a package accessible for deep reflection, which includes access to private members of types within the package."
        ),
        CompletionItem.Keyword(
            prefix = "permits",
            description = "Used in a sealed interface or class to specify the classes or interfaces that are permitted to extend or implement it. It restricts the subclasses or implementors."
        ),
        CompletionItem.Keyword(
            prefix = "provides",
            description = "Used in a module to declare a service implementation provider. It specifies the implementation class that provides a particular service interface."
        ),
        CompletionItem.Keyword(
            prefix = "record",
            description = "Introduces a compact way to declare immutable data classes."
        ),
        CompletionItem.Keyword(
            prefix = "requires",
            description = "Used in module declarations to specify a required dependency on another module. It ensures that the required module is available at compile-time and runtime."
        ),
        CompletionItem.Keyword(
            prefix = "sealed",
            description = "Used to declare a sealed class or interface, which restricts the subclasses or implementors that can be defined."
        ),
        CompletionItem.Keyword(
            prefix = "to",
            description = "Used in conjunction with provides to specify the service interface to which an implementation class is being provided."
        ),
        CompletionItem.Keyword(
            prefix = "transitive",
            description = "Used in module declarations to propagate dependencies transitively. It allows a module to export its dependencies to other modules that require it."
        ),
        CompletionItem.Keyword(
            prefix = "uses",
            description = "Used in module declarations to declare a service interface that a module consumes. It indicates that the module uses a service provided by another module."
        ),
        CompletionItem.Keyword(
            prefix = "var",
            label = "var (added in Java 10)",
            description = "Introduces local variable type inference, allowing the type of a local variable to be inferred from its initializer."
        ),
        CompletionItem.Keyword(
            prefix = "with",
            description = "Used in conjunction with provides to specify the implementation class(es) that provide a service interface. It is used to declare the provider class(es)."
        ),
        CompletionItem.Keyword(
            prefix = "yield",
            label = "yield (added in Java 13)",
            description = "Used in a switch expression to yield a value from a switch branch."
        )
    )

    private val unusedKeywords = listOf(
        CompletionItem.Keyword(
            prefix = "const",
            label = "const (unused)",
            description = "Not used in Java, but it is a reserved keyword."
        ),
        CompletionItem.Keyword(
            prefix = "goto",
            label = "goto (unused)",
            description = "Not used in Java, but it is a reserved keyword."
        ),
        CompletionItem.Keyword(
            prefix = "strictfp",
            label = "strictfp (unused)",
            description = "No longer used in Java, it was used to ensure consistent floating-point calculations across different platforms."
        )
    )

    private val snippets = listOf(
        CompletionItem.Snippet(
            prefix = "bg-task",
            description = "Executes the logics and functions in background process",
            body = getSnippetFileContent("bg-task")
        ),
        CompletionItem.Snippet(
            prefix = "ifn",
            description = "Inserts 'if null' statement",
            body = """
                if ($1 == null) {
                    $0
                }
            """.trimIndent()
        ),
        CompletionItem.Snippet(
            prefix = "inn",
            description = "Inserts 'if not null' statement",
            body = """
                if ($1 != null) {
                    $0
                }
            """.trimIndent()
        ),
        CompletionItem.Snippet(
            prefix = "inst",
            description = "Checks object type with instanceof and down-casts it",
            body = getSnippetFileContent("inst")
        ),
        CompletionItem.Snippet(
            prefix = "lazy",
            description = "Performs lazy initialization",
            body = getSnippetFileContent("lazy")
        ),
        CompletionItem.Snippet(
            prefix = "main",
            description = "main() method declaration",
            body = """
                public static void main(String[] args) {
                    $0
                }
            """.trimIndent()
        ),
        CompletionItem.Snippet(
            prefix = "sc",
            description = "Creates a standard input with Scanner",
            body = getSnippetFileContent("sc")
        ),
        CompletionItem.Snippet(
            prefix = "sout",
            description = "Prints a string to System.out",
            body = "System.out.println(\"$0\")"
        ),
    )

}