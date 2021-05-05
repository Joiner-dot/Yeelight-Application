package com.example.yeelightapp.activities

import android.app.ActionBar
import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.yeelightapp.R
import com.example.yeelightapp.businesslogic.manager.ManagerAPI


class ChoosingLampPage : Activity() {
    val params: LinearLayout.LayoutParams =
        LinearLayout.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val managerBL = ManagerAPI()
        val linearLayout: LinearLayout = findViewById(R.id.listofLamps)
        linearLayout.removeAllViews()
//        val lamps = managerBL.giveAllLamps()
//        params.setMargins(0, 50, 0, 0)
//        for (i in lamps.indices) {
//            val textView = TextView(this)
//            textView.text = lamps[i].name
//            textView.isClickable = true
//            textView.textSize = 26f
//            textView.gravity = Gravity.CENTER
//            textView.layoutParams = params
//            textView.setTextColor(Color.parseColor("#000000"))
//            textView.setPadding(10, 10, 10, 10)
//            textView.background = ContextCompat.getDrawable(baseContext, R.drawable.border)
//            textView.setOnClickListener {
//                val intent = Intent(this, MenuPage::class.java)
//                intent.putExtra("IP", lamps[i].ip)
//                startActivity(intent)
//            }
//            linearLayout.addView(textView)
//        }
    }
}
