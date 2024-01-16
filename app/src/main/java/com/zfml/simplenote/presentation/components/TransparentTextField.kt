package com.zfml.simplenote.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    isHintVisible: Boolean,
    style: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onValueChanged: (String) -> Unit,
    onFocusChanged: (FocusState) -> Unit,
) {
    Box(modifier = modifier) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChanged(it)
                },
            value = text,
            textStyle = style,
            onValueChange = onValueChanged,
            singleLine = singleLine
        )

        if (isHintVisible) {
            Text(
                text = hint,
                style = style
            )
        }

    }

}

@Preview
@Composable
fun TransparentTextFieldPreview() {
    TransparentTextField(
        text = "Title",
        hint = "Hint",
        onFocusChanged = {

        },
        isHintVisible = true,
        onValueChanged = {

        }
    )
}