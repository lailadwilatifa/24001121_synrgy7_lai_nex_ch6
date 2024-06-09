package com.example.listingmovie.presentation.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.work.WorkInfo
import com.example.listingmovie.R
import com.example.listingmovie.common.Constants
import com.example.listingmovie.databinding.FragmentProfileBinding
import com.example.listingmovie.domain.model.User
import com.example.listingmovie.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val TAG = "cobablur"

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    private val REQUEST_CODE_PERMISSION = 100

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                Log.d(TAG, "result uri $result")
                viewModel.outputWorkInfos.observe(viewLifecycleOwner, workInfoObserver())
                viewModel.setImageUri(result)
                viewModel.applyBlur(3)
            } else {
                Log.e(TAG, "No image selected")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnGambar.setOnClickListener {
            checkingPermissions()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()

        binding.btnUpdate.setOnClickListener {
            val user = User(
                binding.usernameEditText.text.toString(),
                "",
                "",
                binding.namaLengkapEditText.text.toString(),
                binding.tanggalLahirEditText.text.toString(),
                binding.alamatEditText.text.toString()
            )

            viewModel.saveProfile(user)
            Toast.makeText(requireContext(), "Berhasil mengupdate profile", Toast.LENGTH_SHORT)
                .show()
        }

        binding.btnLogout.setOnClickListener {
            viewModel.outputUri?.let { currentUri ->
                Log.d(TAG, "currenturi $currentUri")
                binding.imgProfile.setImageURI(currentUri)
            }
//            viewModel.saveLogin(false)
//            findNavController().navigate(
//                R.id.action_profileFragment_to_loginFragment,
//                null,
//                NavOptions.Builder().setPopUpTo(R.id.profileFragment, true).build()
//            )
        }
    }

    private fun workInfoObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->

            Log.d(TAG, "ini $listOfWorkInfo")
            if (listOfWorkInfo.isEmpty()) {
                return@Observer
            }

            // We only care about the one output status.
            // Every continuation has only one worker tagged TAG_OUTPUT
            val workInfo = listOfWorkInfo[0]

            val outputImageUri = workInfo.outputData.getString(Constants.KEY_IMAGE_URI)

            Log.d(TAG, "work $outputImageUri")

            if (workInfo.state.isFinished) {
                progressBar(false)

                // Normally this processing, which is not directly related to drawing views on
                // screen would be in the ViewModel. For simplicity we are keeping it here.

                // If there is an output file show "See File" button
                if (!outputImageUri.isNullOrEmpty()) {
                    viewModel.setOutputUri(outputImageUri)
                    binding.imgProfile.setImageURI(Uri.parse(outputImageUri))
//                    binding.seeFileButton.visibility = View.VISIBLE
                } else {
                    Log.e(TAG, "Output image uri is empty")
                }
            } else {
                progressBar(true)
            }
        }
    }

    private fun progressBar(state: Boolean) {
        binding.progressBar.isVisible = state
    }

    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(), Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: FragmentActivity,
        permission: String,
        permissions: Array<String>,
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun openGallery() {
        galleryResult.launch("image/*")
    }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        binding.imgProfile.setImageBitmap(bitmap)
    }

    private fun setObserver() {

        viewModel.getUser().observe(viewLifecycleOwner) { result ->
            binding.usernameEditText.setText(result.username)
            binding.namaLengkapEditText.setText(result.namaLengkap)
            binding.tanggalLahirEditText.setText(result.tanggalLahir)
            binding.alamatEditText.setText(result.alamat)
        }
    }

}