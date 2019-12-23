package com.vincent.funny

import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

    }
    fun coroutineTest()
    {
        val viewModelJob = Job()    //用来取消协程

        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)   //初始化CoroutineScope 指定协程的运行所在线程传入 Job 方便后面取消协程

        val lunchJob = uiScope.launch { //启动一个协程
            updateUI() //suspend函数运行在协程内或者suspend另外一个函数内
        }

        viewModelJob.cancel()

    }

    suspend fun updateUI() {
        delay(1000L) //delay是一个 suspend 函数
//        textView.text = "Hello, from coroutines!"
    }
}
