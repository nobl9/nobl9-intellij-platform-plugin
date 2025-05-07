package com.nobl9.nobl9

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class CompletionTest : BasePlatformTestCase() {

    fun testCompletion() {
        myFixture.configureByFile("completion.yaml")
        waitUntilFileOpenedByLspServer(myFixture.project, myFixture.file.virtualFile)

        val completions = myFixture.completeBasic()
        assertTrue("No completions returned", completions != null && completions.isNotEmpty())
        val completionStrings = completions!!.map { it.lookupString }
        assertSize(1, completionStrings)
        assertTrue("n9/v1alpha" in completionStrings)
    }

    override fun setUp() {
        super.setUp()
        myFixture.testDataPath = "src/test/testData/completion"
    }
}
