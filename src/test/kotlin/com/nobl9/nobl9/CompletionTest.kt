package com.nobl9.nobl9

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class CompletionTest : BasePlatformTestCase() {

    fun testCompletion() {
        myFixture.configureByFile("completion.yaml")
        myFixture.doHighlighting() // Triggers LSP server to start.
        val completions = myFixture.completeBasic()
        assertTrue("No completions returned", completions != null && completions.isNotEmpty())
        val completionStrings = completions!!.map { it.lookupString }
        assertSize(1, completionStrings)
        assertTrue("n9/v1alpha" in completionStrings)
    }

    override fun getTestDataPath() = "src/test/testData/completion"
}
