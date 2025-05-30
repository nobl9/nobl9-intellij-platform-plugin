package com.nobl9.nobl9

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.builders.ModuleFixtureBuilder
import com.intellij.testFramework.fixtures.CodeInsightFixtureTestCase
import com.intellij.testFramework.fixtures.ModuleFixture
import com.intellij.testFramework.fixtures.impl.CodeInsightTestFixtureImpl

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class HighlightingTest : CodeInsightFixtureTestCase<ModuleFixtureBuilder<ModuleFixture>>() {

    fun testDiagnostics() {
        myFixture.configureByFile("diagnostics.yaml")
        myFixture.checkLspHighlighting()
    }

    override fun setUp() {
        super.setUp()
        (myFixture as CodeInsightTestFixtureImpl).canChangeDocumentDuringHighlighting(true)
        myFixture.testDataPath = "src/test/testData/highlighting"
    }
}
