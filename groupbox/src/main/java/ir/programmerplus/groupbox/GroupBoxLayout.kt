@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.programmerplus.groupbox

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import ir.programmerplus.groupbox.databinding.LayoutGroupBoxBinding


/**
 * The GroupBoxLayout is a container layout view that has a title label and draws a rounded border
 * over the view
 */
class GroupBoxLayout : RelativeLayout {

    companion object {
        private const val DEFAULT_TEXT = ""
        private const val DEFAULT_TEXT_SIZE = 14
        private const val DEFAULT_COLOR = Color.BLACK
        private const val DEFAULT_CORNER_RADIUS = 5f
        private const val DEFAULT_STROKE_WIDTH = 1.5f
    }

    private lateinit var drawable: CutoutDrawable
    private lateinit var binding: LayoutGroupBoxBinding

    private var leftPadding: Int = 0
    private var rightPadding: Int = 0
    private var topPadding: Int = 0
    private var bottomPadding: Int = 0

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
        applyAttributes(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
        applyAttributes(context, attrs, defStyleAttr)
    }

    /**
     * This function will inflate related xml layout and initializes the view's
     * default settings and etc.
     */
    private fun initView(context: Context) {
        binding = LayoutGroupBoxBinding.inflate(LayoutInflater.from(context), this)

        leftPadding = binding.root.paddingLeft
        topPadding = binding.root.paddingTop
        rightPadding = binding.root.paddingRight
        bottomPadding = binding.root.paddingBottom

        initBorderBackground()

        setLabelText(DEFAULT_TEXT)
        setLabelTextColor(DEFAULT_COLOR)
        setLabelTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE)

        // Update the cutout part of the drawable
        binding.label.addOnLayoutChangeListener { _, left, top, right, bottom, _, _, _, _ ->

            if (binding.label.text.isNotEmpty()) {
                val realLeft: Int = left - binding.content.left
                val realTop: Int = top - binding.content.top
                val realRight: Int = right - binding.content.left
                val realBottom: Int = bottom - binding.content.top

                drawable.setCutout(realLeft.toFloat(), realTop.toFloat(), realRight.toFloat(), realBottom.toFloat())

            } else {
                drawable.removeCutout()
            }
        }
    }

    /**
     * This function will get attributes and apply them on view
     *
     * @param context      context
     * @param attrs        attributes set
     * @param defStyleAttr default style
     */
    private fun applyAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.GroupBoxLayout, defStyleAttr, 0)

        val styleResId = attributes.getResourceId(R.styleable.GroupBoxLayout_labelStyle, -1)
        if (styleResId != -1) setLabelStyleResource(styleResId)

        val labelText = attributes.getString(R.styleable.GroupBoxLayout_labelText)
        setLabelText(labelText)

        val labelTextColor = attributes.getColor(R.styleable.GroupBoxLayout_labelTextColor, DEFAULT_COLOR)
        setLabelTextColor(labelTextColor)

        val labelSize = attributes.getDimensionPixelSize(R.styleable.GroupBoxLayout_labelTextSize, 0)
        if (labelSize > 0) setLabelTextSize(TypedValue.COMPLEX_UNIT_PX, labelSize)

        val strokeColor = attributes.getColor(R.styleable.GroupBoxLayout_borderColor, DEFAULT_COLOR)
        setStrokeColor(strokeColor)

        val strokeWidth =
            attributes.getDimensionPixelSize(R.styleable.GroupBoxLayout_borderStrokeWidth, DEFAULT_STROKE_WIDTH.dp())
        setStrokeWidth(strokeWidth)

        val cornerRadius =
            attributes.getDimensionPixelSize(R.styleable.GroupBoxLayout_borderCornerRadius, DEFAULT_CORNER_RADIUS.dp())
        setCornerRadius(cornerRadius)

        attributes.recycle()
    }

    /**
     * This overridden method will add all views to content layout to make sure that layout will perform
     * as a container layout
     */
    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (this::binding.isInitialized) binding.content.addView(child, index, params)
        else super.addView(child, index, params)
    }

    /**
     * This overridden method will remove views from content layout to make sure that layout will perform
     * as a container layout
     */
    override fun removeView(view: View?) {
        if (this::binding.isInitialized) binding.content.removeView(view)
        else super.removeView(view)
    }


    /**
     * This method will add set padding to content layout
     */
    fun setContentPadding(left: Int, top: Int, right: Int, bottom: Int) {
        leftPadding = left
        topPadding = top
        rightPadding = right
        bottomPadding = bottom
    }

    /**
     * Here we recalculate top margin to make label center vertically
     * and we set parent root margin to the content layout
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        recalculateTopMargin()

        binding.root.setPadding(0, 0, 0, 0)

        binding.content.setPadding(
            leftPadding,
            topPadding,
            rightPadding,
            bottomPadding
        )
    }

    /**
     * This function will set border background
     */
    private fun initBorderBackground() {
        // configuration of the shape for the outline
        val shape = ShapeAppearanceModel.Builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(DEFAULT_CORNER_RADIUS)
            .build()

        // configuration of the CutOutDrawable
        drawable = CutoutDrawable(shape).apply {
            strokeWidth = DEFAULT_STROKE_WIDTH.dp().toFloat()
            strokeColor = ColorStateList.valueOf(DEFAULT_COLOR)
            fillColor = ColorStateList.valueOf(Color.TRANSPARENT)
        }

        binding.content.background = drawable
    }

    /**
     * Set top margin to half of label height
     */
    private fun recalculateTopMargin() {
        val marginTop: Int = binding.label.measuredHeight / 2
        val layoutParams: LayoutParams

        if (binding.content.layoutParams is LayoutParams) {
            layoutParams = binding.content.layoutParams as LayoutParams
            layoutParams.setMargins(0, marginTop, 0, 0)

        } else {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(layoutParams.leftMargin, marginTop, layoutParams.rightMargin, layoutParams.bottomMargin)
        }

        binding.content.layoutParams = layoutParams
    }

    /**
     * This function will set the text of label to be displayed.
     *
     * @param text text string to be set
     */
    fun setLabelText(text: String?) {
        binding.label.text = text
        binding.label.visibility = VISIBLE
    }

    /**
     * Return the text that label TextView is displaying.
     *
     * @return label text
     */
    @Suppress("unused")
    fun getLabelText(): CharSequence? {
        return binding.label.text
    }

    /**
     * This function will set text color of label TextView
     *
     * @param color color to be set
     */
    fun setLabelTextColor(@ColorInt color: Int) {
        binding.label.setTextColor(color)
    }

    /**
     * Set the default label text size to a given unit and value.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     */
    fun setLabelTextSize(unit: Int, size: Int) {
        binding.label.setTextSize(unit, size.toFloat())
    }

    /**
     * Set the default label text size to a given value.
     *
     * @param size The desired size in SP unit.
     */
    fun setLabelTextSize(size: Int) {
        binding.label.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
    }

    /**
     * Sets the text style such as color, size, style, hint color, and highlight color
     * from the specified TextAppearance resource.
     *
     * @param resId   style resource id
     */
    fun setLabelStyleResource(@StyleRes resId: Int) {
        @Suppress("DEPRECATION")
        binding.label.setTextAppearance(context, resId)
    }

    /**
     * Sets the stroke color of border
     *
     * @param color color
     */
    fun setStrokeColor(@ColorInt color: Int) {
        drawable.strokeColor = ColorStateList.valueOf(color)
    }

    /**
     * Sets stroke width of border
     *
     * @param width stroke width
     */
    fun setStrokeWidth(width: Int) {
        drawable.strokeWidth = width.toFloat()
    }

    /**
     * Sets corner radius of border
     *
     * @param radius corner radius
     */
    fun setCornerRadius(radius: Int) {
        val appearanceModel = drawable.shapeAppearanceModel.toBuilder().setAllCornerSizes(radius.toFloat()).build()
        drawable.shapeAppearanceModel = appearanceModel
    }

    /**
     * This function will convert value to dp size
     *
     * @return value in dp
     */
    private fun Float.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

}