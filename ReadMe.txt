MarkSkippedAnnotation is a custom annotation to be used with TestNG.
It can be used to mark a test method, and its dependent methods, as "Skipped".
It can also be used to completely omit a test method, and its dependent methods, from being processed by TestNG.

Where @Test's "dependsOnMethods" attribute looks back for methods that the current test method depends on, 
@MarkSkipped's "dependentMethods" attribute looks ahead for methods that depend on the current test method.

There are 3 listeners:  SkipMarkSkipped, OmitMarkSkipped, OmitAndCountMarkSkipped

When used in "Omit" mode, the time of execution can be greatly reduced.
Larger the number of tests, the more noticeable the difference will be.
However, TestNG's xml Test results will not add up to TestNG's final xml Suite result.



---------------------------------------------------------------------------------------------------------------------------------------
SkipMarkSkipped.java
---------------------------------------------------------------------------------------------------------------------------------------
Use this listener to let TestNG process methods annotated with @MarkSkipped, and its dependent methods, and mark them as skipped.
To test, run testng_SkipMarkSkipped.xml as TestNG Suite.



---------------------------------------------------------------------------------------------------------------------------------------
OmitMarkSkipped.java
---------------------------------------------------------------------------------------------------------------------------------------
This listener tells TestNG to completely omit methods annotated with @MarkSkipped, and their dependent methods.
To test, run testng_OmitMarkSkipped.xml as TestNG Suite.



---------------------------------------------------------------------------------------------------------------------------------------
OmitAndCountMarkSkipped.java
---------------------------------------------------------------------------------------------------------------------------------------
This listener tells TestNG to not process methods annotated with @MarkSkipped, and their dependent methods, 
	but will still add them to "Skipped Tests" in the final Suite level results (but not in Test level results).
To test, run testng_OmitAndCountMarkSkipped.xml as TestNG Suite.



