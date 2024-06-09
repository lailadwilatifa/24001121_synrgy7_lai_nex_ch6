package com.example.listingmovie.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.listingmovie.common.Constants
import com.example.listingmovie.data.local.UserDataStoreManager
import com.example.listingmovie.domain.model.User
import com.example.listingmovie.workers.BlurWorker
import com.example.listingmovie.workers.CleanupWorker
import com.example.listingmovie.workers.SaveImageToFileWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val pref: UserDataStoreManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var imageUri: Uri? = null
    internal var outputUri: Uri? = null
    private val workManager = WorkManager.getInstance(context)
    internal val outputWorkInfos: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData(Constants.TAG_OUTPUT)

    internal fun cancelWork() {
        workManager.cancelUniqueWork(Constants.IMAGE_MANIPULATION_WORK_NAME)
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            Log.d("cobablur", "result uri 2 $it")
            builder.putString(Constants.KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    internal fun applyBlur(blurLevel: Int) {
        var continuation = workManager
            .beginUniqueWork(
                Constants.IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )

        // Add WorkRequests to blur the image the number of times requested
        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }

            continuation = continuation.then(blurBuilder.build())
        }

        // Create charging constraint
        val constraints = Constraints.Builder()
            .build()

        // Add WorkRequest to save the image to the filesystem
        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .setConstraints(constraints)
            .addTag(Constants.TAG_OUTPUT)
            .build()
        continuation = continuation.then(save)

        // Actually start the work
        continuation.enqueue()
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

    fun setImageUri(uri: Uri?){
        imageUri = uri
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }

    fun saveProfile(user: User){
        viewModelScope.launch {
            pref.setProfile(user)
        }
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun saveLogin(isLogged: Boolean){
        viewModelScope.launch {
            pref.setIsLogged(isLogged)
        }
    }

}