package com.example.advencedkotlinsyntax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    var studentAnswer = 0
    // 建立一個Quiz的物件
    val quiz = Quiz()

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 建立學生答題的陣列
        val studentanswers = arrayOf("15", false, 2023, 3.14159f)

        // 利用for迴圈調用Quiz的checkQuestion方法來確認答題是否正確
        for (i in studentanswers.indices){
            if(quiz.checkQuestion(studentanswers[i],i).equals("Correct") ){
                studentAnswer++
            }
            Log.e(TAG,"${studentAnswer} of ${quiz.questionNumber} answered." )
        }
    }
}