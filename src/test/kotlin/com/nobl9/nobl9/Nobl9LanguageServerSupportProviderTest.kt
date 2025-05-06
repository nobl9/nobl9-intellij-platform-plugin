package com.nobl9.nobl9

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class Nobl9LanguageServerSupportProviderTest : BasePlatformTestCase() {

    fun testCompletion() {
        myFixture.configureByFile("completion.yaml")
        myFixture.doHighlighting() // Triggers LSP server to start.
        val completions = myFixture.completeBasic()
        assertTrue("No completions returned", completions != null && completions.isNotEmpty())
        val completionStrings = completions!!.map { it.lookupString }
        assertSize(1, completionStrings)
        assertTrue("n9/v1alpha" in completionStrings)
    }

    @org.junit.Ignore("Temporarily skipped due to diagnostics issue")
    fun testDiagnostics() {
        myFixture.configureByFile("diagnostics.yaml")
        val highlights = myFixture.doHighlighting()
        // Filter for errors and warnings
        val diagnostics = highlights.filter { it.severity.name in listOf("ERROR", "WARNING") }
        assertTrue("No diagnostics found", diagnostics.isNotEmpty())
        // Optionally, check for a specific diagnostic message
        assertTrue(diagnostics.any { it.description.contains("string must match regular expression") })
    }

    override fun getTestDataPath() = "src/test/testData/lsp"
}
