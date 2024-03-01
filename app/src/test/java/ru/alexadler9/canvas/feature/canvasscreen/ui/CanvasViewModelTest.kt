package ru.alexadler9.canvas.feature.canvasscreen.ui

import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import ru.alexadler9.canvas.data.PreferencesRepository
import ru.alexadler9.canvas.domain.Color
import ru.alexadler9.canvas.domain.Size
import ru.alexadler9.canvas.domain.Style
import ru.alexadler9.canvas.domain.Tool
import ru.alexadler9.canvas.utility.CoroutinesTestExtension

@ExtendWith(CoroutinesTestExtension::class)
class CanvasViewModelTest {

    private lateinit var preferencesRepository: PreferencesRepository
    private lateinit var subject: CanvasViewModel

    @BeforeEach
    fun setUp() {
        preferencesRepository = mock(PreferencesRepository::class.java)
        Mockito.`when`(preferencesRepository.getStyle()).thenReturn(
            Style.NORMAL
        )
        Mockito.`when`(preferencesRepository.getColor()).thenReturn(
            Color.BLACK
        )
        Mockito.`when`(preferencesRepository.getSize()).thenReturn(
            Size.MEDIUM
        )

        subject = CanvasViewModel(preferencesRepository)
    }

    @Test
    fun `test initial parameters`() {
        assertFalse(subject.viewState.value.isToolsVisible)
        assertFalse(subject.viewState.value.isStyleToolVisible)
        assertFalse(subject.viewState.value.isPaletteToolVisible)
        assertFalse(subject.viewState.value.isSizeToolVisible)
        assertEquals(subject.viewState.value.canvasViewState.style, Style.NORMAL)
        assertEquals(subject.viewState.value.canvasViewState.color, Color.BLACK)
        assertEquals(subject.viewState.value.canvasViewState.size, Size.MEDIUM)
    }

    @Test
    fun `tools opened`() {
        subject.processUiAction(UiAction.OnMenuToolsClicked)
        assertTrue(subject.viewState.value.isToolsVisible)
        assertFalse(subject.viewState.value.isStyleToolVisible)
        assertFalse(subject.viewState.value.isPaletteToolVisible)
        assertFalse(subject.viewState.value.isSizeToolVisible)
    }

    @Test
    fun `styles opened`() {
        subject.processUiAction(UiAction.OnMenuToolsClicked)
        subject.processUiAction(UiAction.OnToolClicked(Tool.STYLE.ordinal))
        assertTrue(subject.viewState.value.isToolsVisible)
        assertTrue(subject.viewState.value.isStyleToolVisible)
        assertFalse(subject.viewState.value.isPaletteToolVisible)
        assertFalse(subject.viewState.value.isSizeToolVisible)
    }

    @Test
    fun `palette opened`() {
        subject.processUiAction(UiAction.OnMenuToolsClicked)
        subject.processUiAction(UiAction.OnToolClicked(Tool.PALETTE.ordinal))
        assertTrue(subject.viewState.value.isToolsVisible)
        assertFalse(subject.viewState.value.isStyleToolVisible)
        assertTrue(subject.viewState.value.isPaletteToolVisible)
        assertFalse(subject.viewState.value.isSizeToolVisible)
    }

    @Test
    fun `sizes opened`() {
        subject.processUiAction(UiAction.OnMenuToolsClicked)
        subject.processUiAction(UiAction.OnToolClicked(Tool.SIZE.ordinal))
        assertTrue(subject.viewState.value.isToolsVisible)
        assertFalse(subject.viewState.value.isStyleToolVisible)
        assertFalse(subject.viewState.value.isPaletteToolVisible)
        assertTrue(subject.viewState.value.isSizeToolVisible)
    }

    @Test
    fun `tools close when click on the canvas`() {
        subject.processUiAction(UiAction.OnMenuToolsClicked)
        subject.processUiAction(UiAction.OnToolClicked(Tool.STYLE.ordinal))
        subject.processUiAction(UiAction.OnCanvasClicked)
        assertFalse(subject.viewState.value.isToolsVisible)
        assertFalse(subject.viewState.value.isStyleToolVisible)
        assertFalse(subject.viewState.value.isPaletteToolVisible)
        assertFalse(subject.viewState.value.isSizeToolVisible)
    }

    @Test
    fun `style selected successfully`() {
        subject.processUiAction(UiAction.OnMenuToolsClicked)
        subject.processUiAction(UiAction.OnToolClicked(Tool.STYLE.ordinal))
        subject.processUiAction(UiAction.OnStyleClicked(Style.DASH.ordinal))
        assertEquals(subject.viewState.value.canvasViewState.style, Style.DASH)
    }

    @Test
    fun `color selected successfully`() {
        subject.processUiAction(UiAction.OnMenuToolsClicked)
        subject.processUiAction(UiAction.OnToolClicked(Tool.PALETTE.ordinal))
        subject.processUiAction(UiAction.OnColorClicked(Color.TEAL.ordinal))
        assertEquals(subject.viewState.value.canvasViewState.color, Color.TEAL)
    }

    @Test
    fun `size selected successfully`() {
        subject.processUiAction(UiAction.OnMenuToolsClicked)
        subject.processUiAction(UiAction.OnToolClicked(Tool.SIZE.ordinal))
        subject.processUiAction(UiAction.OnSizeClicked(Size.SMALL.ordinal))
        assertEquals(subject.viewState.value.canvasViewState.size, Size.SMALL)
    }
}