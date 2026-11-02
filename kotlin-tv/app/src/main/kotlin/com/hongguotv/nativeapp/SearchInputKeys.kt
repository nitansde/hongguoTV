// SPDX-License-Identifier: GPL-3.0-only
package com.hongguotv.nativeapp

import android.view.InputDevice
import android.view.KeyEvent
import android.view.inputmethod.BaseInputConnection
import android.widget.EditText

/**
 * 搜索框的确认逻辑。拼音组字期间不搜索：遥控器在候选词上按确认时，事件可能先于输入法提交到达，
 * 此时搜索会带上未转换的拼音。部分遥控器的确认键发送 ENTER，需打开键盘而不是搜索。
 */
internal fun EditText.onSearchAction(openKeyboard: () -> Unit, search: () -> Unit) {
    setOnEditorActionListener { _, _, event ->
        if (BaseInputConnection.getComposingSpanStart(text) >= 0) Unit
        else if (event?.device?.keyboardType == InputDevice.KEYBOARD_TYPE_NON_ALPHABETIC) {
            if (event.action == KeyEvent.ACTION_UP) openKeyboard()
        } else if (event == null || event.action == KeyEvent.ACTION_DOWN) search()
        true
    }
}
