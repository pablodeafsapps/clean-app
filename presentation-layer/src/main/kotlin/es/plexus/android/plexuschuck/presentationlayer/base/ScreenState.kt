package es.plexus.android.plexuschuck.presentationlayer.base

sealed class ScreenState<out T : BaseState> {
    object Loading : ScreenState<Nothing>()
    class Render<out T : BaseState>(val renderState: T?) : ScreenState<T>()
}