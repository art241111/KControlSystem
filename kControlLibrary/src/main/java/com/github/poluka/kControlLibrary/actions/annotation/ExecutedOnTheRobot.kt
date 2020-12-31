package com.github.poluka.kControlLibrary.actions.annotation

@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
        // function declarations
        // @Composable fun Foo() { ... }
        // lambda expressions
        // val foo = @Composable { ... }
        AnnotationTarget.FUNCTION,

        // type declarations
        // var foo: @Composable () -> Unit = { ... }
        // parameter types
        // foo: @Composable () -> Unit
        AnnotationTarget.TYPE,

        // composable types inside of type signatures
        // foo: (@Composable () -> Unit) -> Unit
        AnnotationTarget.TYPE_PARAMETER,

        // composable property declarations
        // @Composable val foo: Int get() { ... }
        AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER
)
annotation class ExecutedOnTheRobot