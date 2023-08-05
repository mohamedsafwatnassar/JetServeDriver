package com.nextforce.jetservedriver.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.nextforce.jetservedriver.databinding.AppBarMainBinding

class AppBarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val binding: AppBarMainBinding =
        AppBarMainBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setTitle(@StringRes titleStringId: Int) {
        binding.appBarTitle.setText(titleStringId)
    }

    fun setTitleColor(@ColorRes colorId: Int) {
        binding.appBarTitle.setTextColor(ContextCompat.getColor(context, colorId))
    }

    fun changeBackgroundColor(@ColorRes colorId: Int) {
        binding.appBarBackgroundV.setBackgroundColor(ContextCompat.getColor(context, colorId))
    }

    fun setTitleString(titleString: String) {
        binding.appBarTitle.setSingleLine()
        binding.appBarTitle.isSelected = true
        binding.appBarTitle.text = titleString
    }

    private fun setBackButtonAction(backButtonAction: (() -> Unit)?) {
        binding.appBarBackButton.setOnClickListener {
            backButtonAction?.invoke()
        }
    }

    private fun useBackButton(toBeUsed: Boolean) {
        binding.appBarBackButton.visibility = when (toBeUsed) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun useBackButton(toBeUsed: Boolean, backButtonAction: (() -> Unit)?) {
        useBackButton(toBeUsed)
        setBackButtonAction(backButtonAction)
    }

    fun useBackButton(toBeUsed: Boolean, backButtonAction: (() -> Unit)?, @DrawableRes icon: Int) {
        useBackButton(toBeUsed)
        setBackButtonAction(backButtonAction)
        changeBackButtonDrawable(icon)
    }

    private fun changeBackButtonDrawable(@DrawableRes icon: Int) {
        binding.appBarBackButton.setImageResource(icon)
    }

    private fun setActionButtonAction(action: (() -> Unit)?) {
        binding.actionButton.setOnClickListener {
            action?.invoke()
        }
    }

    private fun setSecondActionButtonAction(action: (() -> Unit)?) {
        binding.actionButton2.setOnClickListener {
            action?.invoke()
        }
    }

    private fun useActionButton(toBeUsed: Boolean, @DrawableRes icon: Int) {
        binding.actionButton.visibility = when (toBeUsed) {
            true -> View.VISIBLE
            else -> View.GONE
        }
        binding.actionButton.setImageResource(icon)
    }

    fun setTitleCenter(titleCenter: Boolean) {
        binding.appBarTitle.gravity = when (titleCenter) {
            true -> Gravity.START
            else -> Gravity.CENTER
        }
    }

    private fun useSecondActionButton(toBeUsed: Boolean, @DrawableRes icon: Int) {
        binding.actionButton2.visibility = when (toBeUsed) {
            true -> View.VISIBLE
            else -> View.GONE
        }
        binding.actionButton2.setImageResource(icon)
    }

    fun useActionButton(toBeUsed: Boolean, @DrawableRes icon: Int, action: (() -> Unit)?) {
        useActionButton(toBeUsed, icon)
        setActionButtonAction(action)
    }

    fun useSecondActionButton(toBeUsed: Boolean, @DrawableRes icon: Int, action: (() -> Unit)?) {
        useSecondActionButton(toBeUsed, icon)
        setSecondActionButtonAction(action)
    }

    fun changeActionButton2Drawable(@DrawableRes icon: Int) {
        binding.actionButton2.setImageResource(icon)
    }

    fun changeActionButtonDrawable(@DrawableRes icon: Int) {
        binding.actionButton.setImageResource(icon)
    }
}
