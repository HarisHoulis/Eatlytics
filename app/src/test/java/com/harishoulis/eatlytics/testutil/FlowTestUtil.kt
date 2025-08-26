package com.harishoulis.eatlytics.testutil

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

/**
 * Simplified Turbine-like API for testing flows
 */
class FlowTurbine<T>(
    private val flow: Flow<T>,
) {

    /**
     * Collects all emissions from the flow
     */
    suspend fun collect(): List<T> = flow.toList()

    /**
     * Collects the first emission from the flow
     */
    suspend fun first(): T = flow.toList().first()

    /**
     * Collects the last emission from the flow
     */
    suspend fun last(): T = flow.toList().last()

    /**
     * Collects emissions and asserts the count
     */
    suspend fun assertCount(expectedCount: Int): List<T> {
        val emissions = collect()
        expectThat(emissions).hasSize(expectedCount)
        return emissions
    }

    /**
     * Collects emissions and asserts they match the expected values
     */
    suspend fun assertValues(vararg expectedValues: T): List<T> {
        val emissions = collect()
        expectThat(emissions).isEqualTo(expectedValues.toList())
        return emissions
    }

    /**
     * Collects emissions and asserts the first value
     */
    suspend fun assertFirst(expectedValue: T): T {
        val first = first()
        expectThat(first).isEqualTo(expectedValue)
        return first
    }

    /**
     * Collects emissions and asserts the last value
     */
    suspend fun assertLast(expectedValue: T): T {
        val last = last()
        expectThat(last).isEqualTo(expectedValue)
        return last
    }

    /**
     * Collects emissions and applies a custom assertion
     */
    suspend fun assertThat(assertion: Assertion.Builder<List<T>>.() -> Unit): List<T> {
        val emissions = collect()
        expectThat(emissions).assertion()
        return emissions
    }

    /**
     * Collects emissions and asserts they are not null
     */
    suspend fun assertNotNull(): List<T> {
        val emissions = collect()
        expectThat(emissions).isNotNull()
        return emissions
    }
}

/**
 * Extension function to create a FlowTurbine from a Flow
 */
fun <T> Flow<T>.test(): FlowTurbine<T> = FlowTurbine(this)

/**
 * Extension function to test a flow with a test block
 */
suspend fun <T> Flow<T>.test(block: suspend FlowTurbine<T>.() -> Unit) {
    val turbine = FlowTurbine(this)
    turbine.block()
}

/**
 * Extension function to run a flow test
 */
fun <T> Flow<T>.runFlowTest(block: suspend FlowTurbine<T>.() -> Unit) = runTest {
    test(block)
}

/**
 * Extension function to collect and assert flow emissions
 */
suspend fun <T> Flow<T>.assertEmissions(vararg expectedEmissions: T) {
    test {
        assertValues(*expectedEmissions)
    }
}

/**
 * Extension function to assert flow has no emissions
 */
suspend fun <T> Flow<T>.assertNoEmissions() {
    test {
        assertCount(0)
    }
}

/**
 * Extension function to assert flow has exactly one emission
 */
suspend fun <T> Flow<T>.assertSingleEmission(expectedValue: T) {
    test {
        assertCount(1)
        assertFirst(expectedValue)
    }
}

/**
 * Extension function to assert flow has multiple emissions
 */
suspend fun <T> Flow<T>.assertMultipleEmissions(expectedCount: Int) {
    test {
        assertCount(expectedCount)
    }
}
