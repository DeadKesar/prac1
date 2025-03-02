package com.example.prac1


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Build
import android.os.Vibrator
import android.util.AttributeSet
import android.os.VibrationEffect
import android.view.MotionEvent
import android.view.View



class Cat @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
    {
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE)
                as android.os.VibratorManager).defaultVibrator
    }
    else
    {
        context.getSystemService("vibrator") as Vibrator
    }

    private val cat1Bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.cat1)
    private val cat2Bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.cat2)
    private var xCord = 0f
    private var yCord = 0f
    private var isTouched = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        xCord = w / 2f
        yCord = h / 2f
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val currentBitmap = if (isTouched) cat2Bitmap else cat1Bitmap
        canvas.drawBitmap(currentBitmap, xCord - currentBitmap.width / 2f, yCord - currentBitmap.height / 4f, null)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouched = true
                xCord = event.x
                yCord = event.y
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                xCord = event.x
                yCord = event.y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                isTouched = false
                invalidate()
            }
        }
        return true
    }
    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}