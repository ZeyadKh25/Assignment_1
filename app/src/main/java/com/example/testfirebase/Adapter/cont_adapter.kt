package com.example.testfirebase.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testfirebase.databinding.DesignListBinding
import com.example.testfirebase.data.contact
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class cont_adapter(var activity: Activity, var data: ArrayList<contact>) : BaseAdapter() {
    lateinit var db: FirebaseFirestore

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(p0: Int): Any {
        return data[p0]
    }

    override fun getItemId(p0: Int): Long {
        return data[p0].id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = DesignListBinding.inflate(LayoutInflater.from(p2!!.context), p2, false)

        db = Firebase.firestore

        val root = p1
        if (root == null) binding.txtName.text = data[p0].name
        binding.txtnum.text = data[p0].num
        binding.txtAdrress.text = data[p0].address

        binding.imageView4.setOnClickListener {
            val Bulder = AlertDialog.Builder(activity)
            Bulder.setTitle("Delete !!")
            Bulder.setMessage("Are You Want To Delete ?")
            Bulder.setPositiveButton("Yes") { _, _ ->
                delete()
                Toast.makeText(activity, "${data[p0].id}", Toast.LENGTH_SHORT).show()
                data.removeAt(p0)
                notifyDataSetChanged()
            }
            Bulder.setNegativeButton("No") { d, _ ->
                d.dismiss()
            }
            Bulder.create().show()


            Log.e("zxzx", "id")
        }



        return binding.root

    }


    fun delete() {
        db.collection("users").document("zzdW58SjuBntY8x2S4ek").delete().addOnSuccessListener {
            Log.d(
                "zizoooo", "DocumentSnapshot successfully deleted!"
            )
        }.addOnFailureListener { e ->
            Log.w("zizoooo", "Error deleting document", e)
        }
    }
}
