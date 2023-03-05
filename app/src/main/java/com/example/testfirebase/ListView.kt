package com.example.testfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.testfirebase.Adapter.cont_adapter
import com.example.testfirebase.databinding.ActivityListViewBinding
import com.example.testfirebase.data.contact
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ListView : AppCompatActivity() {
    lateinit var db: FirebaseFirestore

    companion object {
        lateinit var data: ArrayList<contact>
    }

    lateinit var tel_adapter: cont_adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = ArrayList<contact>()

        data.clear()

        db = Firebase.firestore


        tel_adapter = cont_adapter(this, data)
        binding.listV.adapter = tel_adapter
        fetchData()

        binding.imageView2.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

    }


    fun fetchData() {
        var id = 0
        db.collection("users").get().addOnSuccessListener { result ->
            for (document in result) {
                val name = document.data.get("name").toString()
                val number = "+970 " + document.data.get("number").toString()
                val adrress = document.data.get("address").toString()

                val contactObj = contact(id, name, number, adrress)
                data.add(contactObj)
                id++

                Log.d(
                    "TAGGGGGG", "$id $name , $number , $adrress    .... ${data.size}"
                )
                tel_adapter.notifyDataSetChanged()

            }
        }.addOnFailureListener { exception ->
            Log.w("TAGGGGGG", "Error getting documents.", exception)
        }
    }


}

