package com.openswift.keyboard.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.appcompat.content.res.AppCompatResources
import com.openswift.keyboard.R
import com.openswift.keyboard.data.ClipboardHistory
import com.openswift.keyboard.theme.Themes
import kotlin.math.abs

class ClipboardView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null
) : View(ctx, attrs) {

    var onItemSelected: ((String) -> Unit)? = null
    var onItemDeleted: ((String) -> Unit)? = null
    var onClose: (() -> Unit)? = null
    var onCopyRequested: (() -> Unit)? = null
    var onPasteRequested: (() -> Unit)? = null
    var onCutRequested: (() -> Unit)? = null
    var onSelectAllRequested: (() -> Unit)? = null

    var clipboard = ClipboardHistory(ctx)

    private val theme = Themes.Amoled
    private val density = resources.displayMetrics.density
    private val touchSlop = ViewConfiguration.get(ctx).scaledTouchSlop

    // Paints
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.RIGHT
        textSize = 14f * density
        color = theme.keyText
    }
    private val emptyTitlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 16f * density
        color = theme.keyText
        isFakeBoldText = true
    }
    private val emptySubPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 12f * density
        color = 0xFF8A909D.toInt()
    }
    private val headerTitlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 15f * density
        color = theme.keyText
        isFakeBoldText = true
    }
    private val headerBtnPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 13f * density
        color = theme.keyAccent
        isFakeBoldText = true
    }
    private val actionBtnPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 12.5f * density
        color = theme.keyText
        isFakeBoldText = true
    }
    private val cardBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = theme.keyBackground
        style = Paint.Style.FILL
    }
    private val cardBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0x22FFFFFF
        style = Paint.Style.STROKE
        strokeWidth = 1f * density
    }
    private val actionPillBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = theme.keyModifierBackground
        style = Paint.Style.FILL
    }
    private val actionPillBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0x332BD9FE.toInt()
        style = Paint.Style.STROKE
        strokeWidth = 1f * density
    }
    private val headerBgPaint = Paint().apply {
        color = theme.background
        style = Paint.Style.FILL
    }

    private val deleteIcon = AppCompatResources.getDrawable(ctx, R.drawable.ic_close)?.mutate()?.apply {
        setTint(0xFF8A909D.toInt())
    }

    // Interactive hit bounds
    private val itemBounds = mutableMapOf<String, RectF>()
    private val deleteBounds = mutableMapOf<String, RectF>()
    private val backBtnBounds = RectF()
    private val clearAllBounds = RectF()
    private val copyBtnBounds = RectF()
    private val pasteBtnBounds = RectF()
    private val cutBtnBounds = RectF()
    private val selectAllBtnBounds = RectF()

    // Dimensions
    private val headerHeight = 44f * density
    private val actionRowHeight = 42f * density
    private val itemHeight = 52f * density
    private val itemSpacing = 6f * density
    private val itemPaddingHorizontal = 10f * density
    private val cornerRadius = 8f * density

    // Scrolling state
    private var scrollOffsetY = 0f
    private var maxScrollY = 0f
    private var downX = 0f
    private var downY = 0f
    private var isDragging = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        // Keep height identical to the standard keyboard height for seamless transitions
        val keyHeightPx = 56f * density
        val targetHeight = (4 * keyHeightPx) + (2f * density * 3) + (keyHeightPx * 0.82f) + (8f * density)
        val h = targetHeight.toInt()
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()
        canvas.drawColor(theme.background)

        // 1. Draw Top Header Bar
        canvas.drawRect(0f, 0f, w, headerHeight, headerBgPaint)

        // Back button ("← عودة" or "ABC")
        val backBtnWidth = 72f * density
        val backBtnHeight = 32f * density
        val backBtnTop = (headerHeight - backBtnHeight) / 2f
        backBtnBounds.set(itemPaddingHorizontal, backBtnTop, itemPaddingHorizontal + backBtnWidth, backBtnTop + backBtnHeight)
        canvas.drawRoundRect(backBtnBounds, 6f * density, 6f * density, actionPillBgPaint)
        val backTextY = backBtnBounds.centerY() - ((headerBtnPaint.ascent() + headerBtnPaint.descent()) / 2f)
        canvas.drawText("ABC", backBtnBounds.centerX(), backTextY, headerBtnPaint)

        // Header Title
        val titleTextY = (headerHeight / 2f) - ((headerTitlePaint.ascent() + headerTitlePaint.descent()) / 2f)
        canvas.drawText("حافظة النصوص", w / 2f, titleTextY, headerTitlePaint)

        // Clear All button ("مسح الكل")
        val clearBtnWidth = 76f * density
        val clearBtnHeight = 32f * density
        val clearBtnTop = (headerHeight - clearBtnHeight) / 2f
        val clearBtnLeft = w - itemPaddingHorizontal - clearBtnWidth
        clearAllBounds.set(clearBtnLeft, clearBtnTop, clearBtnLeft + clearBtnWidth, clearBtnTop + clearBtnHeight)
        val hasItems = clipboard.items().isNotEmpty()
        headerBtnPaint.color = if (hasItems) 0xFFFF5555.toInt() else 0x558A909D.toInt()
        canvas.drawRoundRect(clearAllBounds, 6f * density, 6f * density, actionPillBgPaint)
        val clearTextY = clearAllBounds.centerY() - ((headerBtnPaint.ascent() + headerBtnPaint.descent()) / 2f)
        canvas.drawText("مسح الكل", clearAllBounds.centerX(), clearTextY, headerBtnPaint)
        headerBtnPaint.color = theme.keyAccent

        // 2. Draw Quick Action Toolbar Strip (نسخ, لصق, قص, تحديد الكل)
        val actionY = headerHeight
        val actionGap = 6f * density
        val totalActionWidth = w - (itemPaddingHorizontal * 2f)
        val btnWidth = (totalActionWidth - (actionGap * 3f)) / 4f
        val btnHeight = 32f * density
        val btnTop = actionY + ((actionRowHeight - btnHeight) / 2f)
        val btnBottom = btnTop + btnHeight

        val actionButtons = listOf(
            "نسخ" to copyBtnBounds,
            "لصق" to pasteBtnBounds,
            "قص" to cutBtnBounds,
            "تحديد الكل" to selectAllBtnBounds,
        )

        actionButtons.forEachIndexed { index, (label, bounds) ->
            val left = itemPaddingHorizontal + index * (btnWidth + actionGap)
            val right = left + btnWidth
            bounds.set(left, btnTop, right, btnBottom)

            canvas.drawRoundRect(bounds, 6f * density, 6f * density, actionPillBgPaint)
            canvas.drawRoundRect(bounds, 6f * density, 6f * density, actionPillBorderPaint)

            val textY = bounds.centerY() - ((actionBtnPaint.ascent() + actionBtnPaint.descent()) / 2f)
            canvas.drawText(label, bounds.centerX(), textY, actionBtnPaint)
        }

        // 3. Draw Clipboard Items List
        val listTop = headerHeight + actionRowHeight + (4f * density)
        val listBottom = h - (4f * density)
        val listHeight = listBottom - listTop

        val items = clipboard.items()
        itemBounds.clear()
        deleteBounds.clear()

        if (items.isEmpty()) {
            val emptyCenterY = listTop + (listHeight / 2f)
            canvas.drawText("الحافظة فارغة", w / 2f, emptyCenterY - (12f * density), emptyTitlePaint)
            canvas.drawText("انسخ أي نص وسيظهر هنا تلقائياً لتقوم بلصقه بلمسة واحدة", w / 2f, emptyCenterY + (14f * density), emptySubPaint)
            return
        }

        // Calculate scroll bounds
        val totalItemsHeight = items.size * (itemHeight + itemSpacing)
        maxScrollY = (totalItemsHeight - listHeight).coerceAtLeast(0f)
        scrollOffsetY = scrollOffsetY.coerceIn(0f, maxScrollY)

        // Clip canvas to the scrollable viewport
        canvas.save()
        canvas.clipRect(0f, listTop, w, listBottom)

        var curY = listTop - scrollOffsetY
        val deleteWidth = 44f * density

        for (item in items) {
            val cardRect = RectF(
                itemPaddingHorizontal,
                curY,
                w - itemPaddingHorizontal,
                curY + itemHeight
            )
            itemBounds[item] = cardRect

            // Card background and border
            canvas.drawRoundRect(cardRect, cornerRadius, cornerRadius, cardBgPaint)
            canvas.drawRoundRect(cardRect, cornerRadius, cornerRadius, cardBorderPaint)

            // Delete action button on the left edge (LTR) or right edge (RTL)
            val delRect = RectF(
                cardRect.left,
                cardRect.top,
                cardRect.left + deleteWidth,
                cardRect.bottom
            )
            deleteBounds[item] = delRect

            val iconSize = (18f * density).toInt()
            val iconX = delRect.centerX().toInt()
            val iconY = delRect.centerY().toInt()
            deleteIcon?.setBounds(
                iconX - iconSize / 2,
                iconY - iconSize / 2,
                iconX + iconSize / 2,
                iconY + iconSize / 2
            )
            deleteIcon?.draw(canvas)

            // Item text preview (aligned to right for Arabic text, left for English)
            val preview = item.trim().replace("\n", " ")
            val maxChars = 50
            val truncated = if (preview.length > maxChars) preview.take(maxChars) + "…" else preview

            val textRight = cardRect.right - (12f * density)
            val textLeft = cardRect.left + deleteWidth + (8f * density)
            val availableWidth = (textRight - textLeft).coerceAtLeast(10f)

            val textWidth = textPaint.measureText(truncated)
            val isRtl = truncated.any { it in '\u0600'..'\u06FF' }

            val drawX = if (isRtl) textRight else textLeft
            textPaint.textAlign = if (isRtl) Paint.Align.RIGHT else Paint.Align.LEFT

            val origTextSize = textPaint.textSize
            if (textWidth > availableWidth) {
                textPaint.textSize = origTextSize * (availableWidth / textWidth).coerceAtLeast(0.75f)
            }
            val textY = cardRect.centerY() - ((textPaint.ascent() + textPaint.descent()) / 2f)
            canvas.drawText(truncated, drawX, textY, textPaint)
            textPaint.textSize = origTextSize

            curY += itemHeight + itemSpacing
        }

        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                isDragging = false
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = downY - event.y
                if (!isDragging && abs(dy) > touchSlop) {
                    isDragging = true
                }
                if (isDragging && maxScrollY > 0f) {
                    scrollOffsetY = (scrollOffsetY + dy).coerceIn(0f, maxScrollY)
                    downY = event.y
                    invalidate()
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                performClick()
                if (isDragging) {
                    isDragging = false
                    return true
                }

                val x = event.x
                val y = event.y

                // 1. Back button
                if (backBtnBounds.contains(x, y)) {
                    onClose?.invoke()
                    return true
                }

                // 2. Clear all button
                if (clearAllBounds.contains(x, y) && clipboard.items().isNotEmpty()) {
                    clipboard.clear()
                    scrollOffsetY = 0f
                    refresh()
                    return true
                }

                // 3. Quick Action toolbar
                if (copyBtnBounds.contains(x, y)) {
                    onCopyRequested?.invoke()
                    return true
                }
                if (pasteBtnBounds.contains(x, y)) {
                    onPasteRequested?.invoke()
                    return true
                }
                if (cutBtnBounds.contains(x, y)) {
                    onCutRequested?.invoke()
                    return true
                }
                if (selectAllBtnBounds.contains(x, y)) {
                    onSelectAllRequested?.invoke()
                    return true
                }

                // 4. Delete item buttons
                for ((item, rect) in deleteBounds) {
                    if (rect.contains(x, y)) {
                        clipboard.remove(item)
                        onItemDeleted?.invoke(item)
                        refresh()
                        return true
                    }
                }

                // 5. Item tap -> Paste text
                for ((item, rect) in itemBounds) {
                    if (rect.contains(x, y)) {
                        onItemSelected?.invoke(item)
                        return true
                    }
                }
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun refresh() {
        clipboard = ClipboardHistory(context)
        scrollOffsetY = scrollOffsetY.coerceIn(0f, maxScrollY)
        requestLayout()
        invalidate()
    }
}
