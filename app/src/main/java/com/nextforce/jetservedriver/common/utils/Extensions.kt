package com.nextforce.jetservedriver.common.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AnimRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.nextforce.jetservedriver.R
import java.text.SimpleDateFormat
import java.util.*

// handle user double clicked on views
inline fun View.onDebouncedListener(
    delayInClick: Long = 800L,
    crossinline listener: (View) -> Unit
) {
    val enableAgain = Runnable { isEnabled = true }
    setOnClickListener {
        if (isEnabled) {
            isEnabled = false
            postDelayed(enableAgain, delayInClick)
            listener(it)
        }
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun View.snackBar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun Fragment.hideKeyboard() {
    activity?.apply {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

val String.isDigitOnly: Boolean
    get() = matches(Regex("^\\d*\$"))

val String.isAlphabeticOnly: Boolean
    get() = matches(Regex("^[a-zA-Z]*\$"))

val String.isAlphanumericOnly: Boolean
    get() = matches(Regex("^[a-zA-Z\\d]*\$"))

val Any?.isNull get() = this == null

fun Any?.ifNull(block: () -> Unit) = run {
    if (this == null) {
        block()
    }
}

fun Context.anim(@AnimRes resId: Int) =
    AnimationUtils.loadAnimation(this, resId)

fun Context.drawable(@DrawableRes resId: Int) =
    ResourcesCompat.getDrawable(resources, resId, null)

fun View.getString(stringResId: Int): String = resources.getString(stringResId)

fun String.toDate(format: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.parse(this)
}

fun Date.toStringFormat(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}

val EditText.value
    get() = text?.toString() ?: ""
// ex: val name = etName.value

@RequiresApi(Build.VERSION_CODES.M)
fun Context.isNetworkAvailable(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
    return if (capabilities != null) {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    } else {
        false
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun Fragment.isNetworkAvailable() = requireContext().isNetworkAvailable()

/* usage
if (isNetworkAvailable()) {
    // Called when network is available
} else {
    // Called when network not available
}
 */

fun Fragment.customSnackBar(text: String, status: Boolean) {
    val snackBar = Snackbar.make(this.requireView(), text, 3000)
    (snackBar.view as Snackbar.SnackbarLayout).let { snackBarLayout ->
        snackBarLayout.removeAllViews()
        snackBarLayout.setPadding(0, 0, 0, 0)
        snackBarLayout.setBackgroundColor(Color.TRANSPARENT)
        LayoutInflater.from(requireContext())
            .inflate(R.layout.view_snackbar, snackBarLayout).let { layout ->
                val imgStatus = layout.findViewById(R.id.imgStatus) as ImageView
                val titleStatus = layout.findViewById(R.id.titleStatus) as TextView
                val body = layout.findViewById(R.id.body) as TextView
                val close = layout.findViewById(R.id.close) as TextView
                // set body of snack bar
                body.text = text

                // if statement if status success or failed
                if (status) { // status success
                    imgStatus.setImageResource(R.drawable.ic_success)
                    imgStatus.setBackgroundResource(R.color.green)
                    // imgStatus.setBackgroundColor(R.color.green)
                    titleStatus.text = getString(R.string.success)
                } else { // status success
                    imgStatus.setImageResource(R.drawable.ic_failed)
                    imgStatus.setBackgroundResource(R.color.red)
                    // imgStatus.setBackgroundColor(R.color.red)
                    titleStatus.text = getString(R.string.error)
                }

                // dismiss snack bar when click in close
                close.setOnClickListener {
                    snackBar.dismiss()
                }
                val params = snackBarLayout.layoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                snackBarLayout.layoutParams = params
                snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
            }
        snackBar.show()
    }
}

fun String.isEmpty(): Boolean {
    return (
        TextUtils.isEmpty(this) ||
            this.equals("", ignoreCase = true) ||
            this.equals("{}", ignoreCase = true) ||
            this.equals("null", ignoreCase = true) ||
            this.equals("undefined", ignoreCase = true)
        )
}

fun View.gone() = run { visibility = View.GONE }

fun View.visible() = run { visibility = View.VISIBLE }

fun View.invisible() = run { visibility = View.INVISIBLE }

fun AppCompatEditText.isValidEmail(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return !this.text.toString().isEmpty() && this.text.toString().matches(emailPattern)
}

fun EditText.isValidEmail(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return !this.text.toString().isEmpty() && this.text.toString().matches(emailPattern)
}

fun String.isValidEmail(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return !this.isEmpty() && this.matches(emailPattern)
}
