package ru.alexadler9.canvas.feature.canvasscreen.ui

import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.alexadler9.canvas.R
import ru.alexadler9.canvas.domain.Color
import ru.alexadler9.canvas.domain.Size
import ru.alexadler9.canvas.domain.Style
import ru.alexadler9.canvas.domain.Tool
import ru.alexadler9.canvas.utility.RecyclerViewItemCountAssertion

@RunWith(AndroidJUnit4::class)
class CanvasActivityInstrumentedTest {

    @get:Rule
    val rule = ActivityScenarioRule(CanvasActivity::class.java)

    @Test
    fun testCanvasViewDisplayedOnStartup() {
        onView(withId(R.id.canvasView)).check(matches(isDisplayed()))
    }

    @Test
    fun testToolsNotDisplayedOnStartup() {
        onView(withId(R.id.layoutTool)).check(matches(not(isDisplayed())))
        onView(withId(R.id.layoutStyle)).check(matches(not(isDisplayed())))
        onView(withId(R.id.layoutPalette)).check(matches(not(isDisplayed())))
        onView(withId(R.id.layoutSize)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testOpeningAndClosingToolPanel() {
        // Check closure when clicking on canvas.
        onView(withId(R.id.menuItemTools)).perform(click())
        onView(withId(R.id.layoutTool)).check(matches(isDisplayed()))
        onView(withId(R.id.canvasView)).perform(click())
        onView(withId(R.id.layoutTool)).check(matches(not(isDisplayed())))

        // Check closure when clicking menuItemTools again.
        onView(withId(R.id.menuItemTools)).perform(click())
        onView(withId(R.id.layoutTool)).check(matches(isDisplayed()))
        onView(withId(R.id.canvasView)).perform(click())
        onView(withId(R.id.layoutTool)).check(matches(not(isDisplayed())))
    }

    private fun getToolsRecyclerView(@IdRes id: Int): ViewInteraction = onView(
        allOf(
            withId(R.id.rvTools),
            isDescendantOfA(withId(id))
        )
    )

    @Test
    fun testOpeningAndClosingOtherTools() {
        // Check style tool.
        onView(withId(R.id.menuItemTools)).perform(click())
        getToolsRecyclerView(R.id.layoutTool)
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Tool.STYLE.ordinal, click()))
        onView(withId(R.id.layoutStyle)).check(matches(isDisplayed()))

        // Check palette tool.
        getToolsRecyclerView(R.id.layoutTool)
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Tool.PALETTE.ordinal, click()))
        onView(withId(R.id.layoutStyle)).check(matches(not(isDisplayed())))
        onView(withId(R.id.layoutPalette)).check(matches(isDisplayed()))

        // Check size tool.
        getToolsRecyclerView(R.id.layoutTool)
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Tool.SIZE.ordinal, click()))
        onView(withId(R.id.layoutPalette)).check(matches(not(isDisplayed())))
        onView(withId(R.id.layoutSize)).check(matches(isDisplayed()))

        // Check that all tools are closed.
        onView(withId(R.id.canvasView)).perform(click())
        onView(withId(R.id.layoutTool)).check(matches(not(isDisplayed())))
        onView(withId(R.id.layoutSize)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testNumberOfToolsItems() {
        // Check the number of items for parent tool.
        onView(withId(R.id.menuItemTools)).perform(click())
        getToolsRecyclerView(R.id.layoutTool)
            .check(RecyclerViewItemCountAssertion(Matchers.equalTo(Tool.values().size)))

        // Check the number of items style tool.
        getToolsRecyclerView(R.id.layoutTool)
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Tool.STYLE.ordinal, click()))
        getToolsRecyclerView(R.id.layoutStyle)
            .check(RecyclerViewItemCountAssertion(Matchers.equalTo(Style.values().size)))

        // Check the number of items palette tool.
        getToolsRecyclerView(R.id.layoutTool)
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Tool.PALETTE.ordinal, click()))
        getToolsRecyclerView(R.id.layoutPalette)
            .check(RecyclerViewItemCountAssertion(Matchers.equalTo(Color.values().size)))

        // Check the number of items size tool.
        getToolsRecyclerView(R.id.layoutTool)
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Tool.SIZE.ordinal, click()))
        getToolsRecyclerView(R.id.layoutSize)
            .check(RecyclerViewItemCountAssertion(Matchers.equalTo(Size.values().size)))
    }
}

