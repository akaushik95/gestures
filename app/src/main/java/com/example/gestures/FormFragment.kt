package com.example.gestures


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import io.realm.Realm
import kotlinx.android.synthetic.main.data_input.*
import kotlinx.android.synthetic.main.data_input.view.*
import kotlinx.android.synthetic.main.form_fragment_dialog.view.*
import java.io.File


class FormFragment: DialogFragment(){
    var realm: Realm? = null
    val dataModelForForm = BugDataModel()
    val gson = Gson()


    companion object{
        val FILE_PATH: String = "filePath"
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
        var rootView: View = inflater.inflate(R.layout.form_fragment_dialog,container,false)
        realm = Realm.getDefaultInstance()
        rootView.text_filepath.text = this.requireArguments().getString(FILE_PATH)
        rootView.btn_submitData.setOnClickListener{
            try {

                val country: String = edt_country.text.toString()
                val summary: String = edt_summary.text.toString()
                val description: String = edt_description.text.toString()
                val selectType: String = edt_selectType.text.toString()
                val fixingPriority: String = edt_fixingPriority.text.toString()
                val platform: String = edt_platform.text.toString()

                val filePath: String = this.requireArguments().getString(FILE_PATH).toString()
                realm!!.executeTransaction { realm -> realm.copyToRealm(dataModelForForm) }


                Log.d("Status","dataModelForForm in submit "+dataModelForForm.toString())

                val jsonData = gson.toJson(dataModelForForm)
                Toast.makeText(context,jsonData,Toast.LENGTH_LONG).show()
                //////////////////// SLACK API TEST ///////////////////

                val formdata = arrayOf(country,summary,description,
                                        selectType,fixingPriority,platform)

                sendfile.uploadtext(formdata,File(filePath))


                ///////////////////////////////////////////////////////
                clearFields()

            }catch (e:Exception){
                Log.d("Status","Something went Wrong !!!")
            }
        }
        rootView.btn_cancel.setOnClickListener{
            dismiss()
        }

        return rootView
    }


    fun clearFields(){

        edt_country.setText("")
        edt_summary.setText("")
        edt_description.setText("")
        edt_selectType.setText("")
        edt_fixingPriority.setText("")
        edt_platform.setText("")
    }


}