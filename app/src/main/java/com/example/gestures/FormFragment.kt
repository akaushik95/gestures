package com.example.gestures


import android.app.Activity.RESULT_OK
import android.content.Intent
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
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.data_input.*
import kotlinx.android.synthetic.main.data_input.view.*
import kotlinx.android.synthetic.main.form_fragment_dialog.view.*


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

                dataModelForForm.country = edt_country.text.toString()
                dataModelForForm.summary = edt_summary.text.toString()
                dataModelForForm.description = edt_description.text.toString()
                dataModelForForm.selectType = edt_selectType.text.toString()
                dataModelForForm.fixingPriority = edt_fixingPriority.text.toString()
                dataModelForForm.platform = edt_platform.text.toString()

                dataModelForForm.filePath = this.requireArguments().getString(FILE_PATH)
                realm!!.executeTransaction { realm -> realm.copyToRealm(dataModelForForm) }


                Log.d("Status","dataModelForForm in submit "+dataModelForForm.toString())

                val jsonData = gson.toJson(dataModelForForm)
                Toast.makeText(context,jsonData,Toast.LENGTH_LONG).show()


                Log.d("Status","Data Inserted !!!")
                clearFields()

            }catch (e:Exception){
                Log.d("Status","Something went Wrong !!!")
            }
        }
        rootView.btn_cancel.setOnClickListener{
            dismiss()
        }
//        rootView.btn_fetchHistory.setOnClickListener{
//            try {
//                Log.d("Status","Inside fetchData")
//                val dataModels: List<BugDataModel> =
//                    realm!!.where(BugDataModel::class.java).findAll()
//
//                var arrayList = ArrayList<Any>()
////            arrayList.add("History")
//
////            val bugsArray = arrayOf("bug1","bug2","bug3")
//                for (i in dataModels.size-1 downTo 0) {
//                    Log.d("Status",dataModels[i]
//                        .toString())
//                    arrayList.add(dataModels[i])     //gson.toJson(item)
//
//                }
//                val arrayAdapter : ArrayAdapter<*>
//
//                val bugsHistory = rootView.listview_history
//
//                arrayAdapter = ArrayAdapter(rootView.context,
//                    android.R.layout.simple_list_item_1, arrayList)
//                bugsHistory.adapter = arrayAdapter
//
//                Log.d("Status","Data Fetched !!!")
//
//            } catch (e: Exception) {
//                Log.d("Status","Something went Wrong !!!")
//            }
//
//        }

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



//package com.example.gestures
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.DialogFragment
//import io.realm.Realm
//import kotlinx.android.synthetic.main.data_input.*
//import kotlinx.android.synthetic.main.form_fragment_dialog.view.*
//
//class FormFragment: DialogFragment(){
//    var realm: Realm? = null
//    val dataModel = BugDataModel()
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        var rootView: View = inflater.inflate(R.layout.form_fragment_dialog,container,false)
//        Realm.init(context)
//        realm = Realm.getDefaultInstance()
//        rootView.btn_submitData.setOnClickListener{
//            try {
//
//                dataModel.country = edt_country.text.toString()
//                dataModel.summary = edt_summary.text.toString()
//                dataModel.description = edt_description.text.toString()
//
//                realm!!.executeTransaction { realm -> realm.copyToRealm(dataModel) }
//
//                clearFields()
//                dismiss()
//                Toast.makeText(context,"SENDING...", Toast.LENGTH_LONG).show()
//                Log.d("Status","Data Inserted !!!")
//
//            }catch (e:Exception){
//                Log.d("Status","Something went Wrong !!!")
//            }
//        }
//        rootView.btn_cancel.setOnClickListener{
//            dismiss()
//        }
//
//        return rootView
//    }
//
//    fun clearFields(){
//
//        edt_country.setText("")
//        edt_summary.setText("")
//        edt_description.setText("")
//    }
//}