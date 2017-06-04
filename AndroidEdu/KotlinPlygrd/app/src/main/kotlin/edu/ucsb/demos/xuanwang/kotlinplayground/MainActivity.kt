package edu.ucsb.demos.xuanwang.kotlinplayground

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    verticalLayout {
      val mText = textView {
        text = "Welcome to Kotlin"
        textSize = 20f
      }

      val mInput = editText {
        hint = "Please enter some text"
      }

      button("change text") {
        onClick {
          mText.text = mInput.text
        }
      }.lparams(width = matchParent) {
        horizontalMargin = dip(5)
        topMargin = dip(10)
      }
    }
  }
}

