package com.rodrigoguerrero.flickrgallery

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestingRule : TestRule {

    val dispatcher = TestCoroutineDispatcher()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    Dispatchers.setMain(dispatcher)
                    base.evaluate()
                } finally {
                    Dispatchers.resetMain()
                    dispatcher.cleanupTestCoroutines()
                }
            }
        }
    }
}
