package ir.programmerplus.groupbox_example

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ir.programmerplus.groupbox.GroupBoxLayout
import ir.programmerplus.groupbox_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get sample color
        val tealColor = ContextCompat.getColor(this@MainActivity, R.color.teal_700)

        // create groupbox layout
        val groupBoxLayout = GroupBoxLayout(this).apply {
            setLabelText("label text")
            setLabelTextSize(TypedValue.COMPLEX_UNIT_SP, 15)
            setLabelTextColor(tealColor)
            setLabelStyleResource(R.style.GroupBoxLabel)

            setStrokeWidth(5)
            setStrokeColor(tealColor)
            setCornerRadius(15)
        }

        // set margin
        groupBoxLayout.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(5, 5, 5, 5)
        }

        // set padding
        groupBoxLayout.setContentPadding(20, 20, 20, 20)

        // create simple text view
        val textView = TextView(this).apply { text = "Hello World!" }

        // add text view to groupbox layout
        groupBoxLayout.addView(textView)

        binding.root.addView(groupBoxLayout)
    }
}