package com.tangoplus.tangoq

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.tangoplus.tangoq.adapter.AlarmRVAdapter
import com.tangoplus.tangoq.callback.SwipeHelperCallback
import com.tangoplus.tangoq.data.MessageVO
import com.tangoplus.tangoq.databinding.ActivityAlarmBinding
import com.tangoplus.tangoq.listener.OnAlarmClickListener
import com.tangoplus.tangoq.listener.OnAlarmDeleteListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.min

class AlarmActivity : AppCompatActivity(), OnAlarmClickListener, OnAlarmDeleteListener {
    lateinit var binding : ActivityAlarmBinding
    private lateinit var swipeHelperCallback: SwipeHelperCallback
    private lateinit var alarmRecyclerViewAdapter : AlarmRVAdapter
    private lateinit var alarmList : MutableList<MessageVO>


    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibtnAlarmBack.setOnClickListener {
            finish()
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))

        val now: Long = System.currentTimeMillis()
        val date = Date(now)
        // 30분 전
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.MINUTE, -37)
        val minute = calendar.time
        val stringMinute = dateFormat.format(minute)
        val longMinute = dateFormat.parse(stringMinute)?.time

        calendar.add(Calendar.HOUR, -6)
        val hour = calendar.time
        val stringHour = dateFormat.format(hour)
        val longHour = dateFormat.parse(stringHour)?.time

        calendar.add(Calendar.DATE, -2)
        val day = calendar.time
        val stringDay = dateFormat.format(day)
        val longDay = dateFormat.parse(stringDay)?.time

        // 현재
        val stringMinuteTime = date
        val stringTime = dateFormat.format(date)
        val longTime = dateFormat.parse(stringTime)?.time

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                alarmList = mutableListOf(
                    MessageVO(1, "즉시 시작할 것", timestamp =  longTime ,route = "home_intermediate" ),
                    MessageVO(2, "미션이 부여됐습니다", timestamp =  longMinute , route = "pick"),
                    MessageVO(3,"운동 마무리 루틴", timestamp =  longHour ,route = "report_goal"),
                    MessageVO(4,"기기 연결이 완료 됐습니다.", timestamp =  longDay , route = "profile")
                )
                // -----! alarm touchhelper 연동 시작 !-----
                val alarmRecyclerViewAdapter = AlarmRVAdapter(alarmList, this@AlarmActivity, this@AlarmActivity)
                swipeHelperCallback = SwipeHelperCallback().apply {
                    setClamp(250f)
                }

                val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
                itemTouchHelper.attachToRecyclerView(binding.rvAlarm)
                binding.rvAlarm.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = alarmRecyclerViewAdapter
                    setOnTouchListener{ _, _ ->
                        swipeHelperCallback.removePreviousClamp(binding.rvAlarm)
                        false
                    }
                } // -----! alarm touchhelper 연동 끝 !-----
                alarmRecyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onAlarmClick(fragmentId: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fragmentId", fragmentId)
        intent.putExtra("fromAlarmActivity", true)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onAlarmDelete(sn: Long) {
        // ------! 삭제 후 스와이프 초기화 시작 !------
        alarmList.remove(alarmList.find { it.sn == sn })
        swipeHelperCallback.removePreviousClamp(binding.rvAlarm)
        binding.rvAlarm.adapter?.notifyDataSetChanged()
        binding.rvAlarm.post {
            binding.rvAlarm.invalidateItemDecorations()
        }

        // ------! 삭제 후 스와이프 초기화 끝 !------

        CoroutineScope(Dispatchers.IO).launch {
//            messageDao.deleteMessage(messageId)
//            withContext(Dispatchers.Main) {
//                val dm = messages.find { it.id == messageId }
//                messages.removeAt(messages.indexOf(dm))
//                alarmRecyclerViewAdapter.notifyDataSetChanged()
//            }
        }
    }
}