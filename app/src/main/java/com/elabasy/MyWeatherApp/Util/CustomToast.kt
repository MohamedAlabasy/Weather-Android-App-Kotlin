package com.elabasy.MyWeatherApp.Util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.elabasy.MyWeatherApp.R
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_success.view.*


class CustomToast(Context: Context) {
    private var context = Context


    fun setErrorToast(message: String) {
        val parent: ViewGroup? = null
        val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val toastView = inflater.inflate(R.layout.toast_error, parent)
        toastView.toast_tv_text_error.text = message
        toast.view = toastView
        toast.show()
    }

    fun setSuccessToast(message: String) {
        val parent: ViewGroup? = null
        val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val toastView = inflater.inflate(R.layout.toast_success, parent)
        toastView.toast_tv_text_success.text = message
        toast.view = toastView
        toast.show()
    }
}