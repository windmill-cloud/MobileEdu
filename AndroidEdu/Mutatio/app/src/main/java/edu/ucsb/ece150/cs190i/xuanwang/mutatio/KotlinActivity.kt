/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.ece150.cs190i.xuanwang.mutatio

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        val btn = findViewById(R.id.finish_button)
        btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Str", "hello")
            startActivity(intent)
            finish()
        }

    }

}
