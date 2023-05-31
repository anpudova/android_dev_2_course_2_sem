package com.example.tasksproject.presentation.screen.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.tasksproject.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val piePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val pieRect = RectF()

    private var data: List<Pair<Int, Float>> = emptyList()

    init {
        piePaint.style = Paint.Style.FILL
        textPaint.color = Color.WHITE
        textPaint.textSize = resources.getDimensionPixelSize(R.dimen.pie_chart_text_size).toFloat()
        textPaint.textAlign = Paint.Align.CENTER
        linePaint.color = Color.WHITE
        linePaint.strokeWidth = 5f
    }

    fun setData(data: List<Pair<Int, Float>>) {
        this.data = data
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val total = data.sumByDouble { it.second.toDouble() }.toFloat()
        val centerX = pieRect.centerX()
        val centerY = pieRect.centerY()
        val radius = pieRect.width() / 2
        val textRadius = radius * 0.7f

        var sweepAngle: Float
        var startAngle = START_ANGLE
        for ((color, percent) in data) {
            sweepAngle = 360 * (percent / total)
            val angle = startAngle + sweepAngle / 2

            // draw a sector
            piePaint.color = color
            canvas.drawArc(pieRect, startAngle, sweepAngle, true, piePaint)

            // draw a percent
            val textX = centerX + textRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val textY = centerY + textRadius * sin(Math.toRadians(angle.toDouble())).toFloat()
            val percentText = "${percent}%"
            canvas.drawText(percentText, textX, textY, textPaint)

            startAngle += sweepAngle
        }

        // draw boundaries between sectors
        startAngle = START_ANGLE
        for ((_, percent) in data) {
            sweepAngle = 360 * (percent / total)

            val lineEndX = centerX + radius * cos(Math.toRadians(startAngle.toDouble())).toFloat()
            val lineEndY = centerY + radius * sin(Math.toRadians(startAngle.toDouble())).toFloat()
            canvas.drawLine(centerX, centerY, lineEndX, lineEndY, linePaint)

            startAngle += sweepAngle
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val diameter = min(w, h) * 0.8f
        val centerX = w * 0.5f
        val centerY = h * 0.5f

        pieRect.set(
            centerX - diameter * 0.5f,
            centerY - diameter * 0.5f,
            centerX + diameter * 0.5f,
            centerY + diameter * 0.5f
        )
    }

    companion object {
        const val START_ANGLE = -90f
    }
}