package com.rizkydian.internalmeet.utils

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.rizkydian.internalmeet.R

@Suppress("unused")
@BindingAdapter("isDisable")
fun disableTextInputEditText(view: TextInputEditText, bool: Boolean) {
    view.isFocusable = bool
    view.isClickable = bool
}

@BindingAdapter(
    "dropDownItems",
    "dropDownItemLayout",
    "dropDownItemsIncludeEmpty",
    requireAll = false
)
fun AutoCompleteTextView.setItems(
    items: Array<String>?,
    @LayoutRes layout: Int?,
    includeEmpty: Boolean?
) =
    setAdapter(
        ArrayAdapter(
            context,
            layout ?: R.layout.default_dropdown_menu_popup_item,
            (if (includeEmpty == true) arrayOf("") else emptyArray()) + (items ?: emptyArray())
        )
    )
