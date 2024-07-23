package com.tangoplus.tangoq.adapter

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tangoplus.tangoq.R
import com.tangoplus.tangoq.data.ExerciseVO
import com.tangoplus.tangoq.databinding.RvExerciseMainCateogoryItemBinding
import com.tangoplus.tangoq.databinding.RvExerciseSubCategoryItemBinding
import com.tangoplus.tangoq.fragment.ExerciseDetailFragment
import com.tangoplus.tangoq.listener.OnCategoryClickListener

class ExerciseCategoryRVAdapter(private val mainCategorys: MutableList<Pair<Int, String>>,
                                private val subCategorys: List<String>,
                                private val fragment: Fragment,
                                private val onCategoryClickListener: OnCategoryClickListener,
                                private val sn : Int,
//                                private val mainCategoryIndex: Int,
//                                private val onCategoryScrollListener: OnCategoryScrollListener,
                                private var xmlname: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    inner class MainCategoryViewHolder(view:View): RecyclerView.ViewHolder(view) {
//        val tvMCName : TextView = view.findViewById(R.id.tvMCName)
        val ivMCThumbnail : ImageView = view.findViewById(R.id.ivMCThumbnail)
//        val rvMC : RecyclerView = view.findViewById(R.id.rvMC)
//        val mcvMC : MaterialCardView = view.findViewById(R.id.mcvMC)
    }
    inner class SubCategoryViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val tvSCName : TextView = view.findViewById(R.id.tvSCName)

    }


    override fun getItemViewType(position: Int): Int {
        return when (xmlname) {
            "mainCategory" -> 0
            "subCategory" -> 1
            else -> throw IllegalArgumentException("invalid View Type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> {
                val binding = RvExerciseMainCateogoryItemBinding.inflate(inflater, parent, false)
                MainCategoryViewHolder(binding.root)
            }
            1 -> {
                val binding = RvExerciseSubCategoryItemBinding.inflate(inflater, parent, false)
                SubCategoryViewHolder(binding.root)
            }
            else -> throw IllegalArgumentException("invalid view type binding")
        }

    }

    override fun getItemCount(): Int {
        return when (xmlname) {
            "mainCategory" -> {
                mainCategorys.size
            }
            "subCategory" -> {
                subCategorys.size
            }
            else -> throw IllegalArgumentException("invalid Item Count")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            // ------! 대분류 item 시작 !------
            is MainCategoryViewHolder -> {
                val currentItemMain = mainCategorys[position]
//                holder.mcvMC.visibility = View.GONE
//                holder.tvMCName.text = currentItemMain.second
                Glide.with(fragment)
                    .load(fragment.resources.getIdentifier("drawable_main_category_${position}", "drawable", fragment.requireActivity().packageName))
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 캐싱 무시
                    .skipMemoryCache(true) // 메모리 캐시 무시

                    .into(holder.ivMCThumbnail)
                // -----! 이미지 클릭 시 서브 카테고리 시작 !------
//                val adapter = ExerciseCategoryRVAdapter(mainCategorys, subCategorys, fragment, position ,onCategoryScrollListener,"subCategory" )
//                holder.rvMC.adapter = adapter
//                val linearLayoutManager = LinearLayoutManager(fragment.requireContext(), LinearLayoutManager.VERTICAL, false)
//                holder.rvMC.layoutManager = linearLayoutManager

                holder.ivMCThumbnail.setOnClickListener{
                    goExerciseDetail(currentItemMain)
//                    if (holder.mcvMC.visibility == View.GONE) {
//                        holder.mcvMC.visibility = View.VISIBLE
//                        holder.mcvMC.alpha = 0f
//                        holder.mcvMC.animate().apply {
//                            duration = 100
//                            alpha(1f)
//                        }
//                        Handler(Looper.getMainLooper()).postDelayed({
//                            onCategoryScrollListener.categoryScroll(holder.ivMCThumbnail)
//                        }, 150)
//
//                    } else {
//                        holder.mcvMC.animate().apply {
//                            duration = 100
//                            alpha(0f)
//                            withEndAction {
//                                holder.mcvMC.visibility = View.GONE
//                            }
//                        }
//                    }
                }
            }
            is SubCategoryViewHolder -> {
                val currentItem = subCategorys[position]
                holder.tvSCName.text = currentItem
                val adapterPosition = holder.adapterPosition

                if (adapterPosition == selectedPosition) {
//                    holder.tvSCName.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(fragment.requireContext(), R.color.subColor200))
                    holder.tvSCName.setBackgroundResource(R.drawable.background_stroke_1dp_main_color_28dp)
                    holder.tvSCName.setTextColor(ContextCompat.getColor(fragment.requireContext(), R.color.mainColor))
                } else {
                    holder.tvSCName.setTextColor(ContextCompat.getColor(fragment.requireContext(), R.color.subColor800))
                    holder.tvSCName.setBackgroundResource(R.drawable.background_stroke_1dp_sub_color_28dp)
                }

                holder.tvSCName.setOnClickListener {
                    onCategoryClickListener.onCategoryClick(sn, currentItem)
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previousPosition) // 이전 선택된 아이템 갱신
                    notifyItemChanged(selectedPosition) // 새로 선택된 아이템 갱신
                    Log.v("subCategoryIndex", "$selectedPosition")
                }
            }
        }
    }

    private fun goExerciseDetail(category : Pair<Int, String>) {
        Log.v("ClickIndex", "category: $category")
        Log.v("EDsn", "$sn")
        fragment.requireActivity().supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right)
            add(R.id.flMain, ExerciseDetailFragment.newInstance(category, sn))
//            addToBackStack(null)
            commit()
        }
    }
}