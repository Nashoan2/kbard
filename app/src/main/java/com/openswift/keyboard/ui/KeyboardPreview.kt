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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openswift.keyboard.theme.KbTheme

@Composable
fun KeyboardPreview(
    theme: KbTheme,
    modifier: Modifier = Modifier,
    layoutId: String = "123"
) {
    val bgColor = Color(theme.keyBackground)
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
                .height(28.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🕪", color = subtleColor, fontSize = 14.sp)
            Text("🌐", color = subtleColor, fontSize = 15.sp)
            Text("<I>", color = subtleColor, fontSize = 12.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Medium)
            Text("📋", color = subtleColor, fontSize = 14.sp)
            Text("⌄", color = subtleColor, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        }

        when (layoutId) {
            "123", "numpad" -> {
                // Layout matching 123.png exactly
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight * 4 + keySpacing * 3),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing)
                ) {
                    // Column 1: Operators (+, -, *, /) and ABC at bottom
                    Column(
                        modifier = Modifier
                            .weight(1.15f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(keySpacing)
                    ) {
                        PreviewKey("+", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.75f))
                        PreviewKey("-", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.75f))
                        PreviewKey("*", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.75f))
                        PreviewKey("/", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.75f))
                        PreviewKey("ABC", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), isModifier = true)
                    }

                    // Columns 2, 3, 4, 5
                    Column(
                        modifier = Modifier
                            .weight(7.15f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(keySpacing)
                    ) {
                        // Row 1: 1 2 3 %
                        Row(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(keySpacing)
                        ) {
                            PreviewKey("1", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("2", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("3", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("%", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.15f))
                        }
                        // Row 2: 4 5 6 ␣
                        Row(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(keySpacing)
                        ) {
                            PreviewKey("4", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("5", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("6", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("␣", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.15f))
                        }
                        // Row 3: 7 8 9 ⌫
                        Row(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(keySpacing)
                        ) {
                            PreviewKey("7", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("8", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("9", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("⌫", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.15f), isModifier = true)
                        }
                        // Row 4: , !?# 0 = . ↵
                        Row(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(keySpacing)
                        ) {
                            PreviewKey(",", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.9f))
                            PreviewKey("!?#", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.1f), isModifier = true)
                            PreviewKey("0", bgColor, textColor, keyHeight, modifier = Modifier.weight(2f))
                            PreviewKey("=", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.1f))
                            PreviewKey(".", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.9f))
                            PreviewKey("↵", Color(0xFFA8C7FA), Color(0xFF041E49), keyHeight, modifier = Modifier.weight(1.15f), isModifier = true)
                        }
                    }
                }
            }
            "symbols" -> {
                // Row 1: 1 2 3 + - @ $ ( )
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("1", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("2", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("3", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("+", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("-", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "_")
                    PreviewKey("@", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "&")
                    PreviewKey("$", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "€")
                    PreviewKey("(", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "<")
                    PreviewKey(")", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = ">")
                }

                // Row 2: 4 5 6 * / ' " : #
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("4", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("5", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("6", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("*", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("/", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("'", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "<")
                    PreviewKey("\"", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "«")
                    PreviewKey(":", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "؛")
                    PreviewKey("#", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "%")
                }

                // Row 3: 7 8 9 , = ! ؟ ⌫
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("7", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("8", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("9", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey(",", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("=", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("!", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "¡")
                    PreviewKey("؟", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), hint = "?")
                    PreviewKey("⌫", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.35f), isModifier = true)
                }

                // Row 4: abc 0 . space 1/2 تنفيذ
                Row(
                    modifier = Modifier.fillMaxWidth().height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("abc", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.35f), isModifier = true)
                    PreviewKey("0", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey(".", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    PreviewKey("🎙", bgColor, Color(0xFF8A909D), keyHeight, modifier = Modifier.weight(3.6f))
                    PreviewKey("1/2", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.2f), isModifier = true)
                    PreviewKey("تنفيذ", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.4f), isModifier = true)
                }
            }
            "arabic" -> {
                // Row 1: Arabic top row (12 keys)
                KeyboardRow(
                    keys = listOf("ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج", "د"),
                    bgColor = bgColor,
                    textColor = textColor,
                    keyHeight = keyHeight,
                    keySpacing = keySpacing
                )
                
                // Row 2: Arabic middle row (12 keys)
                KeyboardRow(
                    keys = listOf("ش", "س", "ي", "ب", "ل", "ا", "ت", "ن", "م", "ك", "ط", "ذ"),
                    bgColor = bgColor,
                    textColor = textColor,
                    keyHeight = keyHeight,
                    keySpacing = keySpacing
                )
                
                // Row 3: shift + 9 keys + delete
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("⇧", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.3f), isModifier = true)
                    listOf("ئ", "ء", "ؤ", "ر", "ى", "ة", "و", "ز", "ظ").forEach {
                        PreviewKey(it, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    }
                    PreviewKey("⌫", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.3f), isModifier = true)
                }
                
                // Row 4: Space bar with language switch and action keys
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("?123", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.3f), isModifier = true)
                    PreviewKey("🌐", bgColor, textColor, keyHeight, modifier = Modifier.weight(1f), isModifier = true)
                    PreviewKey("العربية 🎙", bgColor, textColor, keyHeight, modifier = Modifier.weight(4.2f))
                    PreviewKey(".", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.9f))
                    PreviewKey("تنفيذ", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.4f), isModifier = true)
                }
            }
            else -> {
                // Row 1: qwerty...
                KeyboardRow(
                    keys = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
                    bgColor = bgColor,
                    textColor = textColor,
                    keyHeight = keyHeight,
                    keySpacing = keySpacing
                )
                
                // Row 2: asdf...
                KeyboardRow(
                    keys = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
                    bgColor = bgColor,
                    textColor = textColor,
                    keyHeight = keyHeight,
                    keySpacing = keySpacing,
                    startPadding = 12.dp
                )
                
                // Row 3: shift zxcv... delete
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("⇧", bgColor, textColor, keyHeight, modifier = Modifier.weight(1.2f), isModifier = true)
                    listOf("z", "x", "c", "v", "b", "n", "m").forEach {
                        PreviewKey(it, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
                    }
                    PreviewKey("⌫", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.2f), isModifier = true)
                }
                
                // Row 4: Space bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(keyHeight),
                    horizontalArrangement = Arrangement.spacedBy(keySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PreviewKey("?123", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.2f), isModifier = true)
                    PreviewKey("English 🎙", bgColor, textColor, keyHeight, modifier = Modifier.weight(4.8f))
                    PreviewKey(".", bgColor, textColor, keyHeight, modifier = Modifier.weight(0.9f))
                    PreviewKey("تنفيذ", bgColor, accentColor, keyHeight, modifier = Modifier.weight(1.3f), isModifier = true)
                }
            }
        }
    }
}

@Composable
fun KeyboardRow(
    keys: List<String>,
    bgColor: Color,
    textColor: Color,
    keyHeight: androidx.compose.ui.unit.Dp,
    keySpacing: androidx.compose.ui.unit.Dp,
    startPadding: androidx.compose.ui.unit.Dp = 0.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(keyHeight)
            .padding(start = startPadding),
        horizontalArrangement = Arrangement.spacedBy(keySpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        keys.forEach { key ->
            PreviewKey(key, bgColor, textColor, keyHeight, modifier = Modifier.weight(1f))
        }
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
        if (hint != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 1.dp)
            ) {
                Text(
                    label,
                    color = textColor,
                    fontSize = 11.sp,
                    maxLines = 1
                )
                Text(
                    hint,
                    color = Color(0xFF8A909D),
                    fontSize = 8.sp,
                    maxLines = 1
                )
            }
        } else {
            Text(
                label,
                color = textColor,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
    }
}
