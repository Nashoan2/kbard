package com.openswift.keyboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openswift.keyboard.R
import com.openswift.keyboard.theme.KbTheme

@Composable
fun KeyboardPreview(
    theme: KbTheme,
    modifier: Modifier = Modifier,
    layoutId: String = "arabic"
) {
    val bgColor = Color(theme.keyBackground)
    val modifierBg = Color(theme.keyModifierBackground)
    val textColor = Color(theme.keyText)
    val accentColor = Color(theme.keyAccent)
    val subtleColor = Color(theme.suggestionText)
    val keySpacing = 3.dp
    val keyHeight = 38.dp
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.background), RoundedCornerShape(14.dp))
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(keySpacing)
    ) {
        // Top Toolbar Strip matching the screenshot
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_audio_wave),
                contentDescription = "Wave",
                tint = subtleColor,
                modifier = Modifier.size(17.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_sentiment_satisfied),
                contentDescription = "Emoji",
                tint = subtleColor,
                modifier = Modifier.size(17.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_text_cursor),
                contentDescription = "Cursor",
                tint = subtleColor,
                modifier = Modifier.size(17.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_content_paste),
                contentDescription = "Clipboard",
                tint = subtleColor,
                modifier = Modifier.size(17.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_keyboard_arrow_down),
                contentDescription = "Hide",
                tint = subtleColor,
                modifier = Modifier.size(17.dp)
            )
        }

        when (layoutId) {
            "arabic" -> {
                // Row 1 (11 keys): ض ص ث ق ف غ ع ه خ ح ج
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val row1 = listOf(
                        "ض" to "١", "ص" to "٢", "ث" to "٣", "ق" to "٤",
                        "ف" to "٥", "غ" to "٦", "ع" to "٧", "ه" to "٨",
                        "خ" to "٩", "ح" to "٠", "ج" to "چ"
                    )
                    row1.forEach { (char, hint) ->
                        PreviewKey(char, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = hint)
                    }
                }
                
                // Row 2 (11 keys): ش س ي ب ل ا ت ن م ك ة
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val row2 = listOf(
                        "ش" to "$", "س" to "&", "ي" to "ى", "ب" to "پ",
                        "ل" to "لا", "ا" to "أ", "ت" to "ـ", "ن" to ")",
                        "م" to "(", "ك" to "گ", "ة" to null
                    )
                    row2.forEach { (char, hint) ->
                        PreviewKey(char, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = hint)
                    }
                }
                
                // Row 3 (10 keys): ء ظ ط ذ د ز ر و ، Delete
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val row3 = listOf(
                        "ء" to "@", "ظ" to "#", "ط" to "_", "ذ" to "\"",
                        "د" to "!", "ز" to "؟", "ر" to "؛", "و" to "ؤ",
                        "،" to "'"
                    )
                    row3.forEach { (char, hint) ->
                        PreviewKey(char, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = hint)
                    }
                    PreviewKey("⌫", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.55f), isModifier = true)
                }
                
                // Row 4 (6 keys): 123, AR, a/،, space, ., Enter
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("123", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.35f), isModifier = true)
                    PreviewKey("AR", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.15f), isModifier = true)
                    PreviewKey("a", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.0f), hint = "،")
                    PreviewSpaceKey(bgColor, subtleColor, keyHeight, modifier = Modifier.weight(3.9f))
                    PreviewKey(".", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.9f))
                    PreviewKey("↵", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.45f), isModifier = true)
                }
            }
            "123", "numpad" -> {
                // Row 1: 1 2 3 4 5 6 7 8 9 0
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0").forEach {
                        PreviewKey(it, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    }
                }
                // Row 2: @ # $ _ & - + ( ) /
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("@", "#", "$", "_", "&", "-", "+", "(", ")", "/").forEach {
                        PreviewKey(it, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    }
                }
                // Row 3: = \ * " ' : ; ! ؟ ⌫
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("=", "\\", "*", "\"", "'", ":", ";", "!", "؟").forEach {
                        PreviewKey(it, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    }
                    PreviewKey("⌫", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.4f), isModifier = true)
                }
                // Row 4: ABC AR ، space . ↵
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("ABC", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.35f), isModifier = true)
                    PreviewKey("AR", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.15f), isModifier = true)
                    PreviewKey("،", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.0f))
                    PreviewSpaceKey(bgColor, subtleColor, keyHeight, modifier = Modifier.weight(3.9f))
                    PreviewKey(".", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.9f))
                    PreviewKey("↵", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.45f), isModifier = true)
                }
            }
            else -> {
                // Row 1: qwerty...
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p").forEachIndexed { index, c ->
                        val hint = if (index == 9) "0" else (index + 1).toString()
                        PreviewKey(c, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = hint)
                    }
                }
                
                // Row 2: asdf...
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight).padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("a", "s", "d", "f", "g", "h", "j", "k", "l").forEach {
                        PreviewKey(it, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    }
                }
                
                // Row 3: shift zxcv... delete
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("⇧", modifierBg, textColor, keyHeight, modifier = Modifier.weight(1.3f), isModifier = true)
                    listOf("z", "x", "c", "v", "b", "n", "m").forEach {
                        PreviewKey(it, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    }
                    PreviewKey("⌫", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.3f), isModifier = true)
                }
                
                // Row 4: Space bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("123", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.35f), isModifier = true)
                    PreviewKey("EN", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.15f), isModifier = true)
                    PreviewKey(",", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.0f))
                    PreviewSpaceKey(bgColor, subtleColor, keyHeight, modifier = Modifier.weight(3.9f))
                    PreviewKey(".", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.9f))
                    PreviewKey("↵", modifierBg, accentColor, keyHeight, modifier = Modifier.weight(1.45f), isModifier = true)
                }
            }
        }
    }
}

@Composable
fun PreviewSpaceKey(
    bgColor: Color,
    micColor: Color,
    height: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(height)
            .background(bgColor, RoundedCornerShape(6.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_mic),
            contentDescription = "Voice",
            tint = micColor,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 4.dp, end = 7.dp)
                .size(13.dp)
        )
    }
}

@Composable
fun PreviewKey(
    label: String,
    bgColor: Color,
    textColor: Color,
    height: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
    isModifier: Boolean = false,
    hint: String? = null
) {
    Box(
        modifier = modifier
            .height(height)
            .background(
                bgColor,
                RoundedCornerShape(6.dp)
            )
            .border(0.5.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (label == "⌫") {
            Icon(
                painter = painterResource(R.drawable.ic_backspace),
                contentDescription = "Delete",
                tint = textColor,
                modifier = Modifier.size(18.dp)
            )
        } else if (label == "↵") {
            Icon(
                painter = painterResource(R.drawable.ic_keyboard_return),
                contentDescription = "Enter",
                tint = textColor,
                modifier = Modifier.size(18.dp)
            )
        } else if (hint != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 1.dp)
            ) {
                Text(
                    label,
                    color = textColor,
                    fontSize = 12.sp,
                    maxLines = 1
                )
                Text(
                    hint,
                    color = Color(0xFF8E95A5),
                    fontSize = 9.sp,
                    maxLines = 1
                )
            }
        } else {
            Text(
                label,
                color = textColor,
                fontSize = if (label.length > 2) 11.sp else 13.sp,
                fontWeight = if (label in listOf("123", "AR", "EN", "ABC")) FontWeight.Bold else FontWeight.Normal,
                maxLines = 1
            )
        }
    }
}
