package ir.programmerplus.groupbox_example

import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ir.programmerplus.groupbox.GroupBoxLayout
import ir.programmerplus.groupbox_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // getting some sample color
        val tealColor = ContextCompat.getColor(this, R.color.teal_700)

        // creating a groupbox layout
        val groupBoxLayout = GroupBoxLayout(this).apply {
            setLabelText("Label Text")
            setLabelTextSize(15)
            setLabelTextColor(tealColor)
            setLabelStyleResource(R.style.GroupBoxLabel)

            setStrokeWidth(5)
            setStrokeColor(tealColor)
            setCornerRadius(15)
        }

        // setting layout params and applying margins
        groupBoxLayout.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(5, 5, 5, 5)
        }

        // setting custom padding
        groupBoxLayout.setContentPadding(20, 20, 20, 20)

        // creating a simple text view
        val textView = TextView(this).apply { text = getString(R.string.hello_world) }

        // adding text view to groupbox layout
        groupBoxLayout.addView(textView)

        binding.root.addView(groupBoxLayout)

        // removing some view from groupbox layout
        binding.lytGroupBox.removeView(binding.lytGroupBox.findViewById(R.id.txt_description))
    }
}