package com.example.gallery2.features.bottomdialog

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.gallery2.BuildConfig
import com.example.gallery2.R
import com.example.gallery2.databinding.FragmentBottomDialogBinding
import com.example.gallery2.utils.Constants.PHOTO_URI
import com.example.gallery2.utils.Constants.TOAST_PERMISSION_CAMERA
import com.example.gallery2.utils.Constants.TOAST_PERMISSION_STORAGE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import getFileName
import org.apache.commons.io.IOUtils
import toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class BottomFragment : BottomSheetDialogFragment() {

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {isGranted ->
            if (isGranted){
                createImageFile().let { tempFile ->
                    FileProvider.getUriForFile(
                        requireContext(), "${BuildConfig.APPLICATION_ID}${".provider"}", tempFile
                    ).let {
                        getCameraImage.launch(it)
                    }
                }
            } else {
                requireContext().toast(TOAST_PERMISSION_CAMERA)
            }
        }

    private val requestStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {isGranted ->
            if (isGranted){
                getGalleryImage.launch("image/*")
            } else {
                requireContext().toast(TOAST_PERMISSION_STORAGE)
            }
        }

    private val getCameraImage =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                bundle.putString(PHOTO_URI, tempFilePath)
                findNavController().navigate(
                    R.id.navigate_bottomFragment_to_addPhotoFragment, bundle
                )
            }
        }

    private val getGalleryImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val parcelFileDescriptor = requireContext()
                .contentResolver
                .openFileDescriptor(uri, "r", null)

            parcelFileDescriptor?.let {
                val inputStream = FileInputStream(it.fileDescriptor)
                val file = File(
                    requireContext().cacheDir,
                    requireContext().contentResolver.getFileName(uri)
                )
                val outputStream = FileOutputStream(file)
                IOUtils.copy(inputStream, outputStream)

                bundle.putString(PHOTO_URI, file.absolutePath)
            }

            findNavController().navigate(
                R.id.navigate_bottomFragment_to_addPhotoFragment, bundle
            )
        }

    private var _binding: FragmentBottomDialogBinding? = null
    private val binding get() = _binding!!
    private var tempFilePath = ""
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomDialogBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun createImageFile(): File {
        val storageDir = requireContext().externalCacheDir

        return File.createTempFile(
            "${System.currentTimeMillis()}_",
            ".jpg",
            storageDir
        ).apply {
            tempFilePath = absolutePath
        }
    }

    private fun initViews() {
        binding.ivCam.setOnClickListener {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }

        binding.ivFolder.setOnClickListener {
            requestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}