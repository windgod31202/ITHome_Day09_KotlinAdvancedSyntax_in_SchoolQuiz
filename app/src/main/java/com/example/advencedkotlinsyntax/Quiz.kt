package com.example.advencedkotlinsyntax

import android.util.Log

class Quiz {

    // 定義難度
    private val easy = Difficulty.EASY
    private val medium = Difficulty.MEDIUM
    private val hard = Difficulty.HARD
    private val expert = Difficulty.EXPERT

    // 定義各個題型、答案、難度
    val question1 = Question<String>("IT_Home鐵人賽今年是第幾屆?", "15", easy)
    val question2 = Question<Boolean>("今年是否是2023年?", true, medium)
    val question3 = Question<Int>("今年是西元幾年?", 2023, hard)
    val question4 = Question<Float>("圓周率的包含整數的小數點後3為數是多少?", 3.14159f, expert)
    // 計算題目數量
    private var questionNumberArr = arrayOf(question1, question2, question3, question4)
    val questionNumber = questionNumberArr.size

    companion object StudentProgress{
        const val StudentName = "IT_Home鐵人賽_Day09"
        const val StudentNumber = "20230919"
    }

    private val TAG = "Question"

    fun Quiz.StudentProgress.printStudentInfo (): String {
        return "$StudentName($StudentNumber)"
    }

    // 建立方法供給外部呼叫並傳入答案
    fun checkQuestion(answer: Comparable<*>, index: Int): String {
        val question = questionNumberArr[index]

        val result = if (question.answer == answer) {
            "Correct"
        } else {
            "Incorrect"
        }

        Log.e(
            TAG, "AnswerResult: $result\n" +
                    "questionType: ${question.questionText}\n" +
                    "difficulty: ${question.difficulty}\n"+
                    "YourAnswer: $answer\n"+
                    "StudentInfo: ${Quiz.printStudentInfo()}"
        )

        return result
    }

    //藉由 let() 存取 question1、question2 和 question3 的屬性
    fun printQuiz() {
        //存取 questionText、answer 和 difficulty 屬性的程式碼，
        //並在前後加上在 question1、question2 和 question3 呼叫 let() 函式的程式碼。
        question1.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question2.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question3.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question4.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
    }
}