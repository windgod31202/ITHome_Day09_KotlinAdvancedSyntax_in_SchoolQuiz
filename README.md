# ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz

今天要來下一步學習**Kotlin**中稍微進階一點的知識了，這邊會詳細說明幾個**Kotlin**中可能會常用到的一些類別建立，以及泛型的說明。

- 目錄
    - [泛型、物件及擴充功能](#泛型、物件及擴充功能)
    - [使用列舉類別](#使用列舉類別)
    - [單例模式物件Object](#單例模式物件Object)
        - [將物件宣告成伴生物件](#將物件宣告成伴生物件)
        - [新增擴充屬性以及擴充函式](#新增擴充屬性以及擴充函式)
    - [定義資料類別](#定義資料類別)
    - [整個程式碼運作流程圖](#整個程式碼運作流程圖)
    - [結論](#結論)

## 泛型、物件及擴充功能
測驗通常會有多種題型，例如填充題或是非題。每個測驗題目都可以用一個類別代表，其中設有多種屬性。
不同的題型 (例如是非題) 可能需要用不同的資料類型代表答案。我們在此定義三種問題類型。
- 填充題：答案是一個字詞，以 `String` 代表。
- 是非題：答案以 `Boolean` 代表。
- 計算題：答案是一組數值。簡單的算數題答案會用 `Int` 代表。
- 撰寫泛型的格式
```kotlin=
class 類別名稱 <泛型的類型>{
    // 方法或功能的撰寫
}
```
- 程式碼部分：
    - Question.kt
    ```kotlin
    class Question <T>(private val questionText: String,
                       private val answer: T,
                       private val difficulty: String) {
        private val TAG = "Question"
        
        // 答題方法
        fun checkQuestion(answer: T){

            if (this.answer == answer){
                Log.e(TAG, "checkQuestion: Correct\nquestionType: $questionText\ndifficulty: $difficulty")
            } else {
                Log.e(TAG, "checkQuestion: Incorrect\nquestionType: $questionText\ndifficulty: $difficulty")
            }
        }
    }
    ```
    - MainActivity.kt
    ```kotlin
    class MainActivity : AppCompatActivity() {
        // 建立題目以及標準答案，後面透過答題來呼叫function進行題目正確與否。
        val question1 = Question<String>("IT_Home鐵人賽今年是第幾屆?", "15", "Easy")
        val question2 = Question<Boolean>("今年是否是2023年?", true, "Easy")
        val question3 = Question<Int>("今年是西元幾年?", 2023, "Easy")
        val question4 = Question<Float>("圓周率的包含整數的小數點後3為數是多少?", 3.14159f, "medium")

        override fun onCreate(savedInstanceState: Bundle?) {
            // ... 省略

            // call the function checkQuestion and pass the answer
            question1.checkQuestion("15")
            question2.checkQuestion(false)
            question3.checkQuestion(2023)
            question4.checkQuestion(3.14159f)
        }
    }
    ```
    在**MainActivity**上使用的變數宣告格式如下，當中的參數設置可以往上看到我所設定的型態中只有**answer**是`泛型`，所以這個型態必須與前面 **"自己設置的型態"** 一致。
    ```kotlin
    val 變數名稱 = 類別名稱<自己設置的型態>("參數設置")
    ```
    ![結果01](https://github.com/windgod31202/ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz/assets/88834703/6415f14d-0593-4589-bd39-ddd3d181bbd1)


- **型態不一致產生的錯誤訊息**
告知你`question1`所設置的型態是`Sting`但我嘗試使用`Int`型態的**15**進行寫入。
![泛型型態不一致的錯誤訊息](https://github.com/windgod31202/ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz/assets/88834703/dd807ba7-bf66-46e5-aabe-b385c0d1b0ba)


## 使用列舉類別
透過使用列舉類別，就能建立內含有限數量可能值的類型。
- **列舉語法**
    - 每項列舉的可能值都稱做「**列舉常數**」。
    - 列舉常數位於建構函式內，**以半形逗號分隔**。
    - 常數名稱所有字母都會使用**大寫**。
- **撰寫格式**
```kotlin
enum class 類別名稱 {
    常數名稱1, 常數名稱2, 常數名稱3 ....
}
```
- 程式碼
    - Difficulty.kt
    ```kotlin
    enum class Difficulty {
        EASY, MEDIUM, HARD, EXPERT
    }
    ```
    - Question.kt
    ```kotlin
    class Question <T>(private val questionText: String,
                       private val answer: T,
                       private val difficulty: Difficulty) {
        // ... 以下省略不變
    }
    ```
    - MainActivity.kt
    ```kotlin
    // 這邊只加入以下變數並取得Difficulty的列舉
        private val easy = Difficulty.EASY
        private val medium = Difficulty.MEDIUM
        private val hard = Difficulty.HARD
        private val expert = Difficulty.EXPERT

    // 然後把難度的輸入改成變數
    ```
可以看到難度的文字是以常數名稱顯示。
![添加Enum的結果](https://github.com/windgod31202/ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz/assets/88834703/070c3030-6c06-4031-b31b-327c15042da3)


## 單例模式物件Object
- 在很多情況下，會需要讓類別裡只有一個執行個體。例如：

    - 手機遊戲目前使用者的玩家資料。
    - 和單一硬體裝置互動時，例如將音訊傳送到音響。
    - 用來存取遠端資料來源 (例如 Firebase 資料庫) 的物件。
    - 進行驗證，一次應該只能登入一名使用者。

前面有講解過單例化模式的概念了，也就是這個物件只會建立一次，不論後續的添加修改皆無法變動其參數
- 撰寫格式
當然你也可以寫`var`進去讓外部可以隨時變動參數之類的，但變動後`object`的參數就會一直維持更改後的數值，所以不怎麼推薦。
```kotlin
object 類別名稱{
    const val 變數名稱 = 參數或名稱
}
```
- 程式碼
```kotlin
object StudentProgress {
    const val StudentName = "IT_Home鐵人賽_Day09"
    const val StudentNumber = "20230919"
}
```
- 呼叫的方法
這邊在外部呼叫時不需要引入`object`這個類別的名稱，可以直接呼叫並取得當中設定的變數名稱，如下程式碼所示：
```kotlin
class MainActivity : AppCompatActivity() {
    
    // ... 省略
    
    override fun onCreate(savedInstanceState: Bundle?) {
        
        // ... 省略
        
        // 下列這行程式碼中的 "StudentProgress.StudentName"
        // 以及 "StudentProgress.StudentNumber"。
        
        Log.e(TAG,"${StudentProgress.StudentName}(${StudentProgress.StudentNumber})：${studentAnswer} of ${questionNumber} answered." )
    }
}
```

- 結果截圖
![添加Object的結果](https://github.com/windgod31202/ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz/assets/88834703/cf6e0c2e-0de2-447f-94a0-603aecf6f13c)
![添加Object的結果02](https://github.com/windgod31202/ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz/assets/88834703/52e43f45-187e-48d6-ba44-096abef0742e)


### 將物件宣告成伴生物件
您可以使用「伴生物件」在其他類別裡面定義單例模式物件。
- 宣告伴生物件的方法
    - 只要在 object 關鍵字前方加入 companion 關鍵字即可
![](https://i.imgur.com/ILhQNH1.png)

- 撰寫格式
這邊我直接放在Question類別中
```kotlin
class Question <T>(private val questionText: String,
                   private val answer: T,
                   private val difficulty: Difficulty) {

    // 伴生物件建立
    companion object StudentProgress{
        const val StudentName = "IT_Home鐵人賽_Day09"
        const val StudentNumber = "20230919"
    }
    
    // 以下方法保持不變
}
```
- 呼叫的方式
在這邊只需要把原來呼叫StudentProgress改成呼叫Question就可以了。
```kotlin
class MainActivity : AppCompatActivity() {
    
    // ... 省略
    
    override fun onCreate(savedInstanceState: Bundle?) {
        
        // ... 省略
        
        // 下列這行程式碼中的 "Question.StudentName"
        // 以及 "Question.StudentNumber"。
        
        Log.e(TAG,"${Question.StudentName}(${Question.StudentNumber})：${studentAnswer} of ${questionNumber} answered." )
    }
}
```
### 新增擴充屬性以及擴充函式
- **新增擴充屬性**
![](https://i.imgur.com/hm8ZOVO.png)
如果想定義擴充功能屬性，請在變數名稱前面加上類型名稱和點號運算子 (.)。
```kotlin
// 我在原先建立的 "Question.StudentProgress" 再去定義擴充功能
// 當中的ProgressText是自訂義名稱
val Question.StudentProgress.ProgressText: String

// 再為這個擴充功能屬性定義 getter，讓它在 main() 裡回傳之前使用的同個字串。
get() = "${StudentName}(${StudentNumber})"
```
回到底下的function中加入 `Question.ProgressText`就能得到上面所建立的輸出了。
- **新增擴充函示**
![](https://i.imgur.com/KKo1Mmg.png)

```kotlin
fun Question.StudentProgress."自訂義函式名稱"(): "型態" {
    
    // 回傳
    return "$StudentName($StudentNumber)"
}
```
這邊建立完成後來到底下建立的function將剛剛的 `Question.ProgressText` 更改成建立的function名稱 `Question."函式名稱"()` 就好了。
- **輸出結果**
如圖中的StudentInfo就是使用以上方法。
![添加擴充函式的結果](https://github.com/windgod31202/ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz/assets/88834703/b6780be3-cf2d-4472-bf17-3ca06ff551c6)


## 定義資料類別
![](https://i.imgur.com/segzGmR.png)
如果有類別定義為資料類別，便會實作以下方法。

- equals()
- hashCode()：使用特定集合類型時就可以看到這項方法。
- toString()
- componentN()：component1()、component2() 等等。
- copy()
- 程式碼
```kotlin
data class Question<T> (
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)
```

這邊稍微修改了一下程式碼：
- Quiz.kt
```kotlin
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
}
```
- MainActivity.kt
```kotlin
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
```
## 整個程式碼運作流程圖
![學生考試流程圖 drawio (1)](https://github.com/windgod31202/ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz/assets/88834703/2af5bde3-cfb8-4e9c-894b-338de0077aad)


## 結論
以上是今天更進階一點的Kotlin程式說明，基本上都是建立類別時可能會常常使用到的一些功能類，Kotlin中有很多類別，在建立時可能會冒出一大串選項導致眼花撩亂。
- 建立Kotlin檔案時的選項如下
![建立Kotlin檔案的選項](https://github.com/windgod31202/ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz/assets/88834703/f4d63363-3251-46d9-86e0-1185b4dc933d)

- 建立Java檔案時的選項如下
![建立Java檔案的選項](https://github.com/windgod31202/ITHome_Day09_KotlinAdvancedSyntax_in_SchoolQuiz/assets/88834703/dbf245d0-c91f-4a43-82b6-a4cc98ec1a61)

