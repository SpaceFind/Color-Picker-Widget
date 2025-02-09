package com.example.colorpickerwidget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val colorList = listOf(
    ColorPickerSingleTab("#7ED7C1", 100),
    ColorPickerSingleTab("#F0DBAF", 300),
    ColorPickerSingleTab("#DC8686", 100),
    ColorPickerSingleTab("#B06161", 100),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorPicker(colorList)
        }
    }
}

data class ColorPickerSingleTab(val color: String, val width: Int)

private val DarkColorPalette = darkColorScheme(
    background = Color.Black,
    onBackground = Color.White
)

private val LightColorPalette = lightColorScheme(
    background = Color.White,
    onBackground = Color.Black
)

@Composable
fun DarkTheme(isDarkMode: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isDarkMode) DarkColorPalette else LightColorPalette,
        content = content
    )
}

@Composable
fun colorPickerTabs(colorPickerTabs: List<ColorPickerSingleTab>): String {
    var selectedColor by remember { mutableStateOf("n/a") }
    val colorPickerMargin = 16
    val colorPickerHeight = 30
    val colorPickerWidth = LocalConfiguration.current.screenWidthDp - (2 * colorPickerMargin)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(colorPickerMargin.dp)
            .border(
                width = 3.dp,
                color = Color.Black,
                shape = RoundedCornerShape(8.dp) // Rounded corners
            )
            .clip(RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val totalWidth = colorList.sumOf { it.width }
        colorPickerTabs.forEach { colorPickerTab ->
            Box(
                modifier = Modifier
                    .width((colorPickerTab.width * colorPickerWidth / totalWidth).dp)
                    .height(colorPickerHeight.dp)
                    .background(Color(android.graphics.Color.parseColor(colorPickerTab.color)))
                    .clickable { selectedColor = colorPickerTab.color }
            )
        }
    }
    return selectedColor
}

@Composable
fun ColorPicker(colorPickerTabs: List<ColorPickerSingleTab>) {
    var isDarkMode by remember { mutableStateOf(false) } // Toggle dark mode
    DarkTheme(isDarkMode = isDarkMode) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Color Picker",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            val selectedColor = colorPickerTabs(colorPickerTabs)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Picked Color",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = selectedColor,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isDarkMode = it }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    ColorPicker(colorList)
}