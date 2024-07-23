package com.tangoplus.tangoq

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.shuhart.stepview.StepView
import com.tangoplus.tangoq.adapter.SetupVPAdapter
import com.tangoplus.tangoq.listener.OnSingleClickListener
import com.tangoplus.tangoq.`object`.NetworkUser.fetchUserUPDATEJson
import com.tangoplus.tangoq.`object`.Singleton_t_user
import com.tangoplus.tangoq.data.UserViewModel
import com.tangoplus.tangoq.databinding.ActivitySetupBinding
import org.json.JSONObject
import java.net.URLEncoder

class SetupActivity : AppCompatActivity() {
    lateinit var binding : ActivitySetupBinding
    val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // -----! singletom에 넣고, update 통신 !-----
        val t_userData = Singleton_t_user.getInstance(this).jsonObject

        val userSn = t_userData?.optString("user_sn")
        Log.v("싱글턴", "${t_userData}")

        binding.pvSetup.progress = viewModel.setupProgress
        binding.svSetup.go(viewModel.setupStep, false)
        binding.svSetup.getState()
            .animationType(StepView.ANIMATION_CIRCLE)
            .steps(object : ArrayList<String?>() {
                init {
                    add("성별")
                    add("신장")
                    add("몸무게")
                    add("목표")
                }
            })
            .stepsNumber(4)

            .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
            // other state methods are equal to the corresponding xml attributes
            .commit()

        // ------! viewmodel + viewpager2 초기화 시작 !------
        //        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        initViewPager()


        binding.btnSu.setOnSingleClickListener {
            // ---- 설정 완료 시, 선택한 데이터 저장 및 페이지 이동 코드 시작 ----
            if (binding.btnSu.text == "완료") {
                val JsonObj = JSONObject()
                JsonObj.put("user_gender", viewModel.User.value?.optString("user_gender"))
                Log.w("성별", viewModel.User.value?.optString("user_gender").toString())
                JsonObj.put("user_height", viewModel.User.value?.optString("user_height"))
                Log.w("키", viewModel.User.value?.optString("user_height").toString())
                JsonObj.put("user_weight", viewModel.User.value?.optString("user_weight"))
                Log.w("몸무게", viewModel.User.value?.optString("user_weight").toString())
                Log.w("JSON몸통", "$JsonObj")

                if (userSn != null) {
                    fetchUserUPDATEJson(getString(R.string.IP_ADDRESS_t_user), JsonObj.toString(), userSn) {
                        t_userData.put("user_gender", viewModel.User.value?.optString("user_gender"))
                        t_userData.put("user_height", viewModel.User.value?.optString("user_height"))
                        t_userData.put("user_weight", viewModel.User.value?.optString("user_weight"))
                        Log.w(ContentValues.TAG +" 싱글톤객체추가", t_userData.optString("user_weight"))
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        ActivityCompat.finishAffinity(this)
                    }
                } // -----! view model에 값을 넣기 끝 !-----
                // -----! 설정 완료 시, 선택한 데이터 저장 및 페이지 이동 코드 끝 !-----
            } else {
                viewModel.setupStep += 1
                binding.vp2Su.currentItem = viewModel.setupStep

                binding.svSetup.go(viewModel.setupStep, true)
                if (binding.pvSetup.progress in 0 ..100) {
                    viewModel.setupProgress += 25
                    binding.pvSetup.progress = viewModel.setupProgress
                }
            }
        }

        // ------! 페이지 변경 call back 메소드 시작 !------
        binding.vp2Su.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 3) {
                    binding.btnSu.text = "완료"
                } else {
                    binding.btnSu.text = "다음으로"
                }
            }
        }) // -----! 페이지 변경 call back 메소드 끝 !-----

        // ------! 이전 버튼 시작 !------
        binding.btnBckSetup.setOnSingleClickListener {
            if (binding.vp2Su.currentItem == 0) {

            } else {
                viewModel.setupStep -= 1
                binding.vp2Su.currentItem = viewModel.setupStep
                binding.svSetup.go(viewModel.setupStep, true)
                if (binding.pvSetup.progress in 0 ..100) {
                    viewModel.setupProgress -= 25
                    binding.pvSetup.progress = viewModel.setupProgress
                }
            }
        }
        // ------! 이전 버튼 끝 !------

        // ------! 스킵 시작 !------
        binding.tvSetupSkip.setOnSingleClickListener {
            val intent = Intent(this@SetupActivity, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } // ------! 스킵 끝 !------
    }

    private fun initViewPager() {
        val viewPager = binding.vp2Su
        viewPager.isUserInputEnabled = false
        viewPager.adapter = SetupVPAdapter(this)
    }
    private fun View.setOnSingleClickListener(action: (v: View) -> Unit) {
        val listener = View.OnClickListener { action(it) }
        setOnClickListener(OnSingleClickListener(listener))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.vp2Su.currentItem == 0) {

        } else {
            viewModel.setupStep -= 1
            binding.vp2Su.currentItem = viewModel.setupStep
            binding.svSetup.go(viewModel.setupStep, true)
            if (binding.pvSetup.progress in 0 ..100) {
                viewModel.setupProgress -= 25
                binding.pvSetup.progress = viewModel.setupProgress
            }
        }
    }
}