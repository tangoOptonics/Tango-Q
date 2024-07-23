package com.tangoplus.tangoq.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.tangoplus.tangoq.R
import com.tangoplus.tangoq.data.FavoriteViewModel
import com.tangoplus.tangoq.data.FavoriteVO
import com.tangoplus.tangoq.databinding.FragmentFavoriteAddDialogBinding
import com.tangoplus.tangoq.fragment.FavoriteFragment
import com.tangoplus.tangoq.`object`.NetworkFavorite.insertFavoriteItemJson
import com.tangoplus.tangoq.`object`.Singleton_t_user
import org.json.JSONObject


class FavoriteAddDialogFragment : DialogFragment() {
    lateinit var binding : FragmentFavoriteAddDialogBinding
    val viewModel : FavoriteViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteAddDialogBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val t_userData = Singleton_t_user.getInstance(requireContext()).jsonObject
        val userSn = t_userData?.optString("user_sn")

        binding.etFDaName.setOnTouchListener{ v, event ->
            val DRAWABLE_RIHGT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.etFDaName.right - binding.etFDaName.compoundDrawables[DRAWABLE_RIHGT].bounds.width())) {

                    binding.etFDaName.text.clear()
                    return@setOnTouchListener true
                }
            }
            false
        }
//
        binding.tvFDaCancel.setOnClickListener { dismiss() }
        binding.tvFDaConfirm.setOnClickListener {
            val jsonObj = JSONObject()
            jsonObj.put("favorite_name", binding.etFDaName.text)
            jsonObj.put("user_sn", userSn)
            Log.v("즐겨찾기JSON", "$jsonObj")

            if (userSn != null) {
                insertFavoriteItemJson(getString(R.string.IP_ADDRESS_t_favorite), jsonObj.toString(), requireContext()) { responseJson ->
                    Log.v("response", "${responseJson}")
//                    val newFavoriteItem = FavoriteVO(
//                        favoriteSn = responseJson!!.getInt("favorite_sn"),
//                        favoriteName = responseJson.optString("favorite_name"),
//                        favoriteExplain = responseJson.optString("favorite_description"),
//                        exercises = mutableListOf(),
//                        imgThumbnails = mutableListOf(),)
//                    Log.v("newFavoriteItem", "${newFavoriteItem}")
//                    val data = responseJson.getJSONArray("seletedData").optJSONObject(0)?.optJSONObject("data")

//                    requireActivity().runOnUiThread {
//                        val updatedList = viewModel.favoriteList.value?.toMutableList() ?: mutableListOf()
//                        updatedList.add(newFavoriteItem)
//                        viewModel.favoriteList.value = updatedList
//                        Log.v("newFavoriteItem", "newFavoriteItem: $newFavoriteItem")
//                        dismiss()
//                    }
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flMain, FavoriteFragment())
                            .commit()
                    }
//                    Log.v("newFavoriteItem", "newFavoriteItem: $newFavoriteItem")
                    dismiss()
                }
            }
        }


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}