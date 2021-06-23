package com.example.gestures

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.data_input.*
import kotlinx.android.synthetic.main.form_fragment_dialog.view.*

class FormFragment: DialogFragment(){
    var realm: Realm? = null
    val dataModel = BugDataModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.form_fragment_dialog,container,false)
        Realm.init(context)
        realm = Realm.getDefaultInstance()
        rootView.btn_submitData.setOnClickListener{
            try {

                dataModel.country = edt_country.text.toString()
                dataModel.summary = edt_summary.text.toString()
                dataModel.description = edt_description.text.toString()

                realm!!.executeTransaction { realm -> realm.copyToRealm(dataModel) }

                clearFields()
                dismiss()
                Toast.makeText(context,"SENDING...", Toast.LENGTH_LONG).show()
                Log.d("Status","Data Inserted !!!")

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
    }
}