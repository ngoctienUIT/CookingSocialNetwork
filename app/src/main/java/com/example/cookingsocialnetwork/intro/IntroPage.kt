package com.example.cookingsocialnetwork.intro

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.splash.SplashPage
import com.google.android.material.button.MaterialButton

class IntroPage: AppCompatActivity() {
    private val indicatorsContainerVal: LinearLayout
        get() = findViewById(R.id.indicatorsContainer)
    private val introSliderViewPagerVal: ViewPager2
        get() = findViewById(R.id.introSliderViewPager)
    private val buttonNext: MaterialButton
        get() = findViewById(R.id.buttonNext)
    private val textSkipIntro: TextView
        get() = findViewById(R.id.textSkipIntro)
    private val introSliderAdapter
        get() = IntroSliderAdapter(
            listOf(
                IntroSlide(
                    "Ex 1",
                    "This is example 1",
                    R.drawable.example_image1
                ),
                IntroSlide(
                    "Ex 2",
                    "This is example 2",
                    R.drawable.example_image2
                ),
                IntroSlide(
                    "Ex 3",
                    "This is example 3",
                    R.drawable.example_image3
                )
            )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_page)
        introSliderViewPagerVal.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPagerVal.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        buttonNext.setOnClickListener{
            if(introSliderViewPagerVal.currentItem + 1< introSliderAdapter.itemCount){
                introSliderViewPagerVal.currentItem +=1
            }else{
                    Intent(applicationContext,SplashPage::class.java).also{
                        startActivity(it)
                    }
            }
        }
        textSkipIntro.setOnClickListener {
            Intent(applicationContext,SplashPage::class.java).also{
                startActivity(it)
            }
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainerVal.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorsContainerVal.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainerVal.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}