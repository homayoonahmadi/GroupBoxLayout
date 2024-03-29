# GroupBoxLayout

[![platform](https://img.shields.io/badge/platform-Android-green.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17)
[![Jitpack](https://jitpack.io/v/homayoonahmadi/GroupBoxLayout.svg)](https://jitpack.io/#homayoonahmadi/GroupBoxLayout)

GroupBoxLayout is a container layout that has a title label and draws a rounded border over the parent view. 
+ supports rtl and ltr directions (using layoutDirection attribute)
+ prevents label text overflow (by ellipsize the label text)
+ is compatible with background colors or any drawables
+ it doesn't use any white or other color backgrounds for hiding border under the label. 

# Screenshots:

![Preview 1](https://github.com/homayoonahmadi/GroupBoxLayout/blob/master/images/01.jpg)
![Preview 2](https://github.com/homayoonahmadi/GroupBoxLayout/blob/master/images/02.jpg)
![Preview 3](https://github.com/homayoonahmadi/GroupBoxLayout/blob/master/images/03.jpg)

# How to add dependency:

Step 1. Add the JitPack repository to your build.gradle file

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

```groovy
dependencies {
    implementation 'com.github.homayoonahmadi:GroupBoxLayout:1.2.0'
}
```

# How to use

+ **In XML:**

```xml
<ir.programmerplus.groupbox.GroupBoxLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="5dp"
    android:layoutDirection="ltr"
    android:padding="30dp"
    app:borderColor="@color/teal_700"
    app:borderCornerRadius="8dp"
    app:borderStrokeWidth="2.2dp"
    app:labelStyle="@style/GroupBoxLabel"
    app:labelText="Form Title"
    app:labelTextColor="@color/teal_700"
    app:labelTextSize="15dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />

</ir.programmerplus.groupbox.GroupBoxLayout>
```

You can customize label text using styles:

```xml

<style name="GroupBoxLabel">
    <item name="android:textStyle">bold</item>
    ...
</style>
```


+ **In Code:**

You can also add layout programmatically to your view like this:

```kotlin
// get sample color
val tealColor = ContextCompat.getColor(this@MainActivity, R.color.teal_700)

// create groupbox layout
val groupBoxLayout = GroupBoxLayout(this).apply {
    setLabelText("label text")
    setLabelTextSize(15)
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
``` 

# Methods

+ **GroupBoxLayout class methods**

| method                                      | description                                                                                                                 |
|---------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| setLabelText(text: String?)                 | This function will set the text of label to be displayed.                                                                   |
| getLabelText()                              | Returns the text that label TextView is displaying.                                                                         |
| setLabelTextColor(@ColorInt color: Int)     | This function will set text color of label TextView                                                                         |
| setLabelTextSize(unit: Int, size: Int)      | Set the default label text size to a given unit and value.                                                                  |
| setLabelStyleResource(@StyleRes resId: Int) | Sets the text style such as color, size, style, hint color, and highlight color from the specified TextAppearance resource. |
| setStrokeColor(@ColorInt color: Int)        | Sets the stroke color of border                                                                                             |
| setStrokeWidth(width: Int)                  | Sets stroke width of border                                                                                                 |
| setCornerRadius(radius: Int)                | Sets corner radius of border                                                                                                |
