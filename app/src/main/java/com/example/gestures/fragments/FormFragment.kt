package com.example.gestures.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gestures.ApiDataModel
import com.example.gestures.R
import com.example.gestures.SendFile
import com.example.gestures.activities.BaseActivity
import com.example.gestures.models.ApiFormData
import com.example.gestures.models.ApiResponseDataModel
import kotlinx.android.synthetic.main.data_input.*
import kotlinx.android.synthetic.main.data_input.view.*
import kotlinx.android.synthetic.main.form_fragment_dialog.view.*
import java.io.File


class FormFragment : DialogFragment() {

    val TAG: String = "FormFragment"

    companion object {
        val FILE_PATH: String = "filePath"
        val LAST_API: String = "lastApi"
        fun getNewInstance(filePath: String, apiModel: ApiResponseDataModel): FormFragment {
            val fragment = FormFragment()
            val bundle = Bundle()
            bundle.putString(FILE_PATH, filePath)
            bundle.putParcelable(LAST_API, apiModel)
            fragment.arguments = bundle
            return fragment
        }

        fun getNewInstance(filePath: String): FormFragment {
            val fragment = FormFragment()
            val bundle = Bundle()
            bundle.putString(FILE_PATH, filePath)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.form_fragment_dialog, container, false)

        rootView.text_filepath.text = this.requireArguments().getString(FILE_PATH)
        rootView.btn_submitData.setOnClickListener {
            try {
                val summary: String = edt_summary.text.toString()
                val description: String = edt_description.text.toString()

                val filePath: String = this.requireArguments().getString(FILE_PATH).toString()

                val apiFormData = ApiFormData(summary, description, File(filePath))

                (activity as BaseActivity).uploadErrorLog(apiFormData, this.arguments?.getParcelable(LAST_API))
                // SendFile.uploadText(apiFormData,apiData) // apiData -> ApiDataModel
                dismiss()

            } catch (e: Exception) {
                Log.d(TAG, getString(R.string.error_occured))
            }
        }
        rootView.btn_cancel.setOnClickListener {
            dismiss()
        }
        return rootView
    }


}