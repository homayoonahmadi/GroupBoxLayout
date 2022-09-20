package ir.programmerplus.groupbox

import android.graphics.*
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.View
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


/**
 * A {@link MaterialShapeDrawable} that can draw a cutout for the label in outline mode.
 */
class CutoutDrawable(shapeAppearanceModel: ShapeAppearanceModel?) : MaterialShapeDrawable(shapeAppearanceModel ?: ShapeAppearanceModel()) {

    private var cutoutPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var cutoutBounds: RectF = RectF()
    private var savedLayer = 0

    init {
        setPaintStyles()
    }

    private fun setPaintStyles() {
        cutoutPaint.style = Paint.Style.FILL_AND_STROKE
        cutoutPaint.color = Color.WHITE
        cutoutPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }

    fun setCutout(left: Float, top: Float, right: Float, bottom: Float) {
        // Avoid expensive redraws by only calling invalidateSelf if one of the cutout's dimensions has changed.
        if (left != cutoutBounds.left || top != cutoutBounds.top || right != cutoutBounds.right || bottom != cutoutBounds.bottom) {
            cutoutBounds[left, top, right] = bottom
            invalidateSelf()
        }
    }

    fun removeCutout() {
        // Call setCutout with empty bounds to remove the cutout.
        setCutout(0f, 0f, 0f, 0f)
    }

    override fun draw(canvas: Canvas) {
        preDraw(canvas)
        super.draw(canvas)

        // Draw mask for the cutout.
        canvas.drawRect(cutoutBounds, cutoutPaint)
        postDraw(canvas)
    }

    private fun preDraw(canvas: Canvas) {
        val callback = callback

        if (useHardwareLayer(callback)) {
            val viewCallback = callback as View?
            // Make sure we're using a hardware layer.
            if (viewCallback!!.layerType != View.LAYER_TYPE_HARDWARE) {
                viewCallback.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            }

        } else {
            // If we're not using a hardware layer, save the canvas layer.
            saveCanvasLayer(canvas)
        }
    }

    @Suppress("DEPRECATION")
    private fun saveCanvasLayer(canvas: Canvas) {
        savedLayer = if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            canvas.saveLayer(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), null)
        } else {
            canvas.saveLayer(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        }
    }

    private fun postDraw(canvas: Canvas) {
        if (!useHardwareLayer(callback)) {
            canvas.restoreToCount(savedLayer)
        }
    }

    private fun useHardwareLayer(callback: Callback?): Boolean {
        return callback is View
    }
}