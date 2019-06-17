package es.plexus.android.plexuschuck.presentationlayer.base

/**
 * This open class is the baseline upon which any screen state is constructed
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
sealed class ScreenState<out T : BaseState> {
    object Loading : ScreenState<Nothing>()
    class Render<out T : BaseState>(val renderState: T?) : ScreenState<T>()
}