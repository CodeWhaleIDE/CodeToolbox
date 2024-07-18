package com.bluewhaleyt.codetoolbox.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.material3.Button
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.updateLayoutParams
import com.bluewhaleyt.codetoolbox.core.project.Project
import com.bluewhaleyt.codetoolbox.editor.utils.currentComposingText
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion
import io.github.rosemoe.sora.widget.component.EditorCompletionAdapter
import io.github.rosemoe.sora.widget.getComponent
import io.github.rosemoe.sora.R
import io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow

@SuppressLint("ViewConstructor")
class IDEEditor @JvmOverloads constructor(
    @get:JvmName("mContext") val context: Context,
    var project: Project?,
    private val attrs: AttributeSet? = null
) : CodeEditor(context, attrs) {

    init {
        setPinLineNumber(true)
        setDividerMargin(30f, 0f)
        lineNumberMarginLeft = 30f

        configureEditorCompletion()
    }

    private fun configureEditorCompletion(): EditorAutoCompletion {
        return getComponent<EditorAutoCompletion>().apply {
            val adapter = object : EditorCompletionAdapter() {
                override fun getView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup?,
                    isCurrentCursorPosition: Boolean
                ): View {
                    val view = LayoutInflater.from(context).inflate(R.layout.default_completion_result_item, parent, false) as ViewGroup
                    val item = getItem(position)

                    val tvLabel = view.findViewById<TextView>(R.id.result_item_label)
                    val tvDesc = view.findViewById<TextView>(R.id.result_item_desc)
                    val imgIcon = view.findViewById<ImageView>(R.id.result_item_image)

                    view.setPadding(0, 16, 0, 16)

                    tvLabel.typeface = editor.typefaceText
                    tvDesc.typeface = editor.typefaceText

                    tvLabel.includeFontPadding = false
                    tvDesc.includeFontPadding = false

                    tvDesc.text = item.desc
                    tvDesc.maxLines = 2

                    imgIcon.setImageDrawable(item.icon)
                    imgIcon.updateLayoutParams {
                        width = 48
                        height = 48
                    }

                    // partial text span highlighting, not stable, so not attempt temporarily
//                    val spannableString = SpannableString(item.label)
//                    val start = item.label.indexOf(editor.currentComposingText)
//                    val end = start + editor.currentComposingText.length
//
//                    val colorSpan = ForegroundColorSpan(colorPrimary.toArgb())
//                    val styleSpan = StyleSpan(Typeface.BOLD)
//
//                    spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                    spannableString.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//                    tvLabel.text = spannableString
                    tvLabel.text = item.label

                    return view
                }
                override fun getItemHeight(): Int {
                    return TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        55f,
                        context.resources.displayMetrics
                    ).toInt()
                }
            }
            setAdapter(adapter)
        }
    }

}