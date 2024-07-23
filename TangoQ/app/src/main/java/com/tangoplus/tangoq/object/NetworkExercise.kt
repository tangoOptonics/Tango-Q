package com.tangoplus.tangoq.`object`

import com.tangoplus.tangoq.data.ExerciseVO
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

object NetworkExercise {

    // TODO 카테고리 가져오기 - 추후에 내가 전체 운동에서 중복없이 카테고리만 가져오는게 아니라, 카테고리만 반환하는 api있으면 속도 향상
    suspend fun fetchExerciseAll(myUrl: String) : MutableList<Pair<Int, String>> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${myUrl}read.php")
            .get()
            .build()
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                Log.w("http>EcCategory", "Success to execute request!: $responseBody")
                val jsonArr = responseBody?.let { JSONObject(it) }?.optJSONArray("data")
                val categorySet = mutableSetOf<Pair<Int, String>>()
                if (jsonArr != null) {
                    for (i in 0 until jsonArr.length()) {
                        val jsonObject = jsonArr.getJSONObject(i)
                        categorySet.add(Pair(jsonObject.optString("exercise_category_id").toInt(), jsonObject.optString("exercise_category_name")))
                    }
                }
                val categoryList = categorySet.toMutableList()
                categoryList
            }
        }
    }

    // ------! exercise1개 가져오기 !------
    suspend fun fetchExerciseById(myUrl: String, exerciseId : String) : ExerciseVO {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${myUrl}read.php?exercise_id=$exerciseId")
            .get()
            .build()
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                Log.w("http>exercise", "Success to execute request!: $responseBody")
                val jo = responseBody.let { JSONObject(it.toString()) }.optJSONObject("data")

                val exerciseInstance = ExerciseVO(
                        exerciseId = jo?.optString("exercise_id"),
                        exerciseName = jo?.optString("exercise_name"),
                        exerciseTypeId = jo?.getString("exercise_type_id"),
                        exerciseTypeName = jo?.getString("exercise_type_name"),
                        exerciseCategoryId = jo?.getString("exercise_category_id"),
                        exerciseCategoryName = jo?.getString("exercise_category_name"),
                        relatedJoint = jo?.getString("related_joint"),
                        relatedMuscle = jo?.getString("related_muscle"),
                        relatedSymptom = jo?.getString("related_symptom"),
                        exerciseStage = jo?.getString("exercise_stage"),
                        exerciseFrequency = jo?.getString("exercise_frequency"),
                        exerciseIntensity = jo?.getString("exercise_intensity"),
                        exerciseInitialPosture = jo?.getString("exercise_initial_posture"),
                        exerciseMethod = jo?.getString("exercise_method"),
                        exerciseCaution = jo?.getString("exercise_caution"),
                        videoActualName = jo?.getString("video_actual_name"),
                        videoFilepath = jo?.getString("video_filepath"),
                        videoDuration = (jo?.optString("video_duration")?.toIntOrNull() ?: 0).toString(),
                        imageFilePathReal = jo?.getString("image_filepath_real")
                    )
                exerciseInstance
            }
        }
    }

    suspend fun fetchCategoryAndSearch(myUrl: String, categoryId: Int, search: Int) : MutableList<ExerciseVO> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${myUrl}read.php?exercise_category_id=$categoryId&exercise_search=$search")
            .get()
            .build()
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                Log.e("http>Cate>Search", "Success to execute request!: $responseBody")

                val exerciseDataList = mutableListOf<ExerciseVO>()
                val jsonArr = responseBody?.let { JSONObject(it) }?.optJSONArray("data")
                Log.v("fetchExerciseJson", "$jsonArr")
                if (jsonArr != null) {
                    for (i in 0 until jsonArr.length()) {
                        val jsonObject = jsonArr.getJSONObject(i)
                        val exerciseData = ExerciseVO(
                            exerciseId = jsonObject.optString("exercise_id"),
                            exerciseName = jsonObject.optString("exercise_name"),
                            exerciseTypeId = jsonObject.getString("exercise_type_id"),
                            exerciseTypeName = jsonObject.getString("exercise_type_name"),
                            exerciseCategoryId = jsonObject.getString("exercise_category_id"),
                            exerciseCategoryName = jsonObject.getString("exercise_category_name"),
                            relatedJoint = jsonObject.getString("related_joint"),
                            relatedMuscle = jsonObject.getString("related_muscle"),
                            relatedSymptom = jsonObject.getString("related_symptom"),
                            exerciseStage = jsonObject.getString("exercise_stage"),
                            exerciseFrequency = jsonObject.getString("exercise_frequency"),
                            exerciseIntensity = jsonObject.getString("exercise_intensity"),
                            exerciseInitialPosture = jsonObject.getString("exercise_initial_posture"),
                            exerciseMethod = jsonObject.getString("exercise_method"),
                            exerciseCaution = jsonObject.getString("exercise_caution"),
                            videoActualName = jsonObject.getString("video_actual_name"),
                            videoFilepath = jsonObject.getString("video_filepath"),
                            videoDuration = (jsonObject.optString("video_duration").toIntOrNull() ?: 0).toString(),
                            imageFilePathReal = jsonObject.getString("image_filepath_real"),
                            )
                        exerciseDataList.add(exerciseData)
                        }
                    }
                    exerciseDataList
                }
            }
    }
    suspend fun fetchExerciseByCategory(myUrl: String, categoryId: Int) : MutableList<ExerciseVO> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${myUrl}read.php?exercise_category_id=$categoryId")
            .get()
            .build()
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                Log.e("http>Cate>Search", "Success to execute request!: $responseBody")

                val exerciseDataList = mutableListOf<ExerciseVO>()
                val jsonArr = responseBody?.let { JSONObject(it) }?.optJSONArray("data")
                Log.v("fetchExerciseJson", "$jsonArr")
                if (jsonArr != null) {
                    for (i in 0 until jsonArr.length()) {
                        val jsonObject = jsonArr.getJSONObject(i)
                        val exerciseData = ExerciseVO(
                            exerciseId = jsonObject.optString("exercise_id"),
                            exerciseName = jsonObject.optString("exercise_name"),
                            exerciseTypeId = jsonObject.getString("exercise_type_id"),
                            exerciseTypeName = jsonObject.getString("exercise_type_name"),
                            exerciseCategoryId = jsonObject.getString("exercise_category_id"),
                            exerciseCategoryName = jsonObject.getString("exercise_category_name"),
                            relatedJoint = jsonObject.getString("related_joint"),
                            relatedMuscle = jsonObject.getString("related_muscle"),
                            relatedSymptom = jsonObject.getString("related_symptom"),
                            exerciseStage = jsonObject.getString("exercise_stage"),
                            exerciseFrequency = jsonObject.getString("exercise_frequency"),
                            exerciseIntensity = jsonObject.getString("exercise_intensity"),
                            exerciseInitialPosture = jsonObject.getString("exercise_initial_posture"),
                            exerciseMethod = jsonObject.getString("exercise_method"),
                            exerciseCaution = jsonObject.getString("exercise_caution"),
                            videoActualName = jsonObject.getString("video_actual_name"),
                            videoFilepath = jsonObject.getString("video_filepath"),
                            videoDuration = (jsonObject.optString("video_duration").toIntOrNull() ?: 0).toString(),
                            imageFilePathReal = jsonObject.getString("image_filepath_real"),
                        )
                        exerciseDataList.add(exerciseData)
                    }
                }
                exerciseDataList
            }
        }
    }
    // 20개만 조회
    suspend fun fetchExerciseJson(myUrl: String): List<ExerciseVO> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${myUrl}read.php")
            .get()
            .build()

        return withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                Log.e("OKHTTP3/Exercise", "Success to execute request!: $responseBody")

                val exerciseDataList = mutableListOf<ExerciseVO>()
                val jsonArr = responseBody?.let { JSONObject(it) }?.optJSONArray("data")
                Log.v("fetchExerciseJson", "$jsonArr")
                if (jsonArr != null) {
                    for (i in 0 until jsonArr.length()) {
                        val jsonObject = jsonArr.getJSONObject(i)
                        val exerciseData = ExerciseVO(
                            exerciseId = jsonObject.optString("exercise_id"),
                            exerciseName = jsonObject.optString("exercise_name"),
                            exerciseTypeId = jsonObject.getString("exercise_type_id"),
                            exerciseTypeName = jsonObject.getString("exercise_type_name"),
                            exerciseCategoryId = jsonObject.getString("exercise_category_id"),
                            exerciseCategoryName = jsonObject.getString("exercise_category_name"),
                            relatedJoint = jsonObject.getString("related_joint"),
                            relatedMuscle = jsonObject.getString("related_muscle"),
                            relatedSymptom = jsonObject.getString("related_symptom"),
                            exerciseStage = jsonObject.getString("exercise_stage"),
                            exerciseFrequency = jsonObject.getString("exercise_frequency"),
                            exerciseIntensity = jsonObject.getString("exercise_intensity"),
                            exerciseInitialPosture = jsonObject.getString("exercise_initial_posture"),
                            exerciseMethod = jsonObject.getString("exercise_method"),
                            exerciseCaution = jsonObject.getString("exercise_caution"),
                            videoActualName = jsonObject.getString("video_actual_name"),
                            videoFilepath = jsonObject.getString("video_filepath"),
                            videoDuration = (jsonObject.optString("video_duration").toIntOrNull() ?: 0).toString(),
                            imageFilePathReal = jsonObject.getString("image_filepath_real"),
                        )
                        exerciseDataList.add(exerciseData)
                    }
                }
                exerciseDataList
            }
        }
    }
    // category + type 두 가지로 조회

    fun jsonToExerciseVO(json: JSONObject): ExerciseVO {
        return ExerciseVO(
            exerciseId = json.optString("exercise_id"),
            exerciseName = json.optString("exercise_name"),
            exerciseTypeId = json.getString("exercise_type_id"),
            exerciseTypeName = json.getString("exercise_type_name"),
            exerciseCategoryId = json.getString("exercise_category_id"),
            exerciseCategoryName = json.getString("exercise_category_name"),
            relatedJoint = json.getString("related_joint"),
            relatedMuscle = json.getString("related_muscle"),
            relatedSymptom = json.getString("related_symptom"),
            exerciseStage = json.getString("exercise_stage"),
            exerciseFrequency = json.getString("exercise_frequency"),
            exerciseIntensity = json.getString("exercise_intensity"),
            exerciseInitialPosture = json.getString("exercise_initial_posture"),
            exerciseMethod = json.getString("exercise_method"),
            exerciseCaution = json.getString("exercise_caution"),
            videoActualName = json.getString("video_actual_name"),
            videoFilepath = json.getString("video_filepath"),
            videoDuration = (json.optString("video_duration").toIntOrNull() ?: 0).toString(),
            imageFilePathReal = json.getString("image_filepath_real"),

        )
    }


}