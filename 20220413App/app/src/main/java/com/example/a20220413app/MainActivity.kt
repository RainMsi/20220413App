package com.example.a20220413app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle;
import android.view.View;
import android.widget.*
import android.os.Handler
import android.os.Looper
import android.os.Message
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var btn_calculate: Button
    private lateinit var ed_height: EditText
    private lateinit var ed_weight: EditText
    private lateinit var tv_weight: TextView
    private lateinit var tv_fat: TextView
    private lateinit var tv_bmi: TextView
    private lateinit var tv_progress: TextView
    private lateinit var progressBar2: ProgressBar
    private lateinit var ll_progress: LinearLayout
    private lateinit var btn_boy: RadioButton
    private var bmiprogress = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_calculate = findViewById(R.id.btn_calculate)
        ed_height = findViewById(R.id.ed_height)
        ed_weight = findViewById(R.id.ed_weight)
        tv_weight = findViewById(R.id.tv_weight)
        tv_fat = findViewById(R.id.tv_fat)
        tv_bmi = findViewById(R.id.tv_bmi)
        tv_progress = findViewById(R.id.tv_progress)
        progressBar2 = findViewById(R.id.progressBar2)
        ll_progress = findViewById(R.id.ll_progress)
        btn_boy = findViewById(R.id.btn_boy)

        btn_calculate.setOnClickListener{
            var msg = Message()
            when{
                ed_height.length() < 1 -> msg.what = 1
                ed_weight.length() < 1 -> msg.what = 2
                else -> {
                    dasd()
                }
            }
        }
    }
    private fun showtoast(msg:String)=
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    private fun dasd(){
        ll_progress.visibility=View.VISIBLE
        thread {
            progressBar2.progress=0
            tv_progress.text="0%"
            bmiprogress=0
            while (bmiprogress < 100) {
                Thread.sleep(50)
                bmiprogress++
                var msg = Message()
                msg.what = 3
                handler.sendMessage(msg)
            }
            var msg = Message()
            msg.what = 4
            handler.sendMessage(msg)
        }.start()
    }

    private val handler = Handler(Looper.getMainLooper()){msg->
        var msg = Message()
        if(msg.what==1 )
            Toast.makeText(this@MainActivity,"???????????????",Toast.LENGTH_SHORT).show()
        if(msg.what==2 )
            Toast.makeText(this@MainActivity,"???????????????",Toast.LENGTH_SHORT).show()
        if(msg.what==3){
            progressBar2.progress = bmiprogress
            tv_progress.text = "$bmiprogress%"
        }
        if(msg.what==4){
            ll_progress.visibility = View.GONE
            val cal_height = ed_height.text.toString().toDouble()
            val cal_weight = ed_weight.text.toString().toDouble()
            val cal_standweigh: Double
            val cal_bodyfat: Double
            if (btn_boy.isChecked){
                cal_standweigh = (cal_height -80) * 0.7
                cal_bodyfat = (cal_weight - 0.88 * cal_standweigh)
            } else {
                cal_standweigh = (cal_height -70) * 0.6
                cal_bodyfat = (cal_weight - 0.82 * cal_standweigh)
            }
            tv_weight.text = "???????????? \n${String.format("%.2f", cal_standweigh)}"
            tv_bmi.text = "????????? \n${String.format("%.2f", cal_bodyfat)}"
        }
        true
    }

}
