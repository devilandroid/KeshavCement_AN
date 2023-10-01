package com.loyaltyworks.keshavcement.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.core.content.ContextCompat
import com.loyaltyworks.keshavcement.R

class Count(context: Context) : Drawable() {
    private val paint1: Paint
    private val paint2: Paint
    private val paint3: Paint
    private val rect1 = Rect()
    private var count1 = ""
    private var draw1 = false
    override fun draw(canvas: Canvas) {
        if (!draw1) {
            return
        }
        val bounds = bounds
        val width = (bounds.right - bounds.left).toFloat()
        val height = (bounds.bottom - bounds.top).toFloat()

        // Position the badge in the top-right quadrant of the icon.

        /*Using Math.max rather than Math.min */
        val radius = Math.max(width, height) / 2 / 2
        val centerX = width - radius - 1 + 5
        val centerY = radius - 5
        if (count1.length <= 2) {
            // Draw badge circle.
            canvas.drawCircle(centerX, centerY, (radius + 7.5).toFloat(), paint2)
            canvas.drawCircle(centerX, centerY, (radius + 5.5).toFloat(), paint1)
        } else {
            canvas.run {
                drawCircle(centerX, centerY, (radius + 8.5).toFloat(), paint2)
                drawCircle(centerX, centerY, (radius + 6.5).toFloat(), paint1)
            }
            //	        	canvas.drawRoundRect(radius, radius, radius, radius, 10, 10, paint1);
        }
        // Draw badge count text inside the circle.
        paint3.getTextBounds(count1, 0, count1.length, rect1)
        val textHeight = (rect1.bottom - rect1.top).toFloat()
        val textY = centerY + textHeight / 2f
        if (count1.length > 2) canvas.drawText("99+", centerX, textY, paint3) else canvas.drawText(
            count1,
            centerX,
            textY,
            paint3
        )
    }

    /*
    Sets the count (i.e notifications) to display.
     */
    fun setCount(count: String) {
        count1 = count

        // Only draw a badge if there are notifications.
        draw1 = !count.equals("0", ignoreCase = true)
        invalidateSelf()
    }

    override fun setAlpha(alpha: Int) {
        // do nothing
    }

    override fun setColorFilter(cf: ColorFilter?) {
        // do nothing
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    companion object {
        fun setCounting(context: Context, icon: LayerDrawable, count: String) {
            val badge: Count

            // Reuse drawable if possible
            val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
            badge = if (reuse != null && reuse is Count) {
                reuse
            } else {
                Count(context)
            }
            //if(count=="0"){
// icon.setVisible(false,true);
//}
            badge.setCount(count)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_badge, badge)
        }
    }

    init {
        // Text Size
        val mTextSize = context.resources.getDimension(R.dimen.h6)
        paint1 = Paint()
        // Background color
        paint1.color = ContextCompat.getColor(context.applicationContext, R.color.colorPrimary)
        paint1.isAntiAlias = true
        paint1.style = Paint.Style.FILL
        paint2 = Paint()
        // stroke color
        paint2.color = ContextCompat.getColor(context.applicationContext, R.color.colorPrimary)
        paint2.isAntiAlias = true
        paint2.style = Paint.Style.FILL
        paint3 = Paint()
        // Text color
        paint3.color = ContextCompat.getColor(context.applicationContext, R.color.white)
        paint3.typeface = Typeface.DEFAULT
        paint3.textSize = mTextSize
        paint3.isAntiAlias = true
        paint3.textAlign = Paint.Align.CENTER
    }
}