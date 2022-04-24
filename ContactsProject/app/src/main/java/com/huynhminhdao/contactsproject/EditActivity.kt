package com.huynhminhdao.contactsproject

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.drawToBitmap

class EditActivity : AppCompatActivity() {
    private lateinit var plainText : EditText
    private lateinit var imgView : ImageView
    private var position = 0
    private var toDo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        plainText = findViewById(R.id.plainText)
        imgView = findViewById(R.id.imgView)



        position = intent.getIntExtra("position", -1)


        plainText.setText(contacts[position].name)
        imgView.setImageBitmap(contacts[position].avatar)

        plainText.requestFocus()

        imgView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select an option")
            builder.setMessage("Message...")
            builder.setNegativeButton("Take a photo") { _, _ ->
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    resultTakePicture.launch(takePictureIntent)
                } catch (e: ActivityNotFoundException) {
                   Log.d("Error", "myValue: $e")
                }
            }
            builder.setPositiveButton(
                "Select an image"
            ) { _, _ ->
                val selectImageIntent = Intent(Intent.ACTION_GET_CONTENT)
                selectImageIntent.type = "image/*"
                try {
                    resultSelectImage.launch(selectImageIntent)
                } catch (e: ActivityNotFoundException) {
                    Log.d("Error", "myValue: $e")
                }
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
    private var resultTakePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val imageBitmap = it.data?.extras?.get("data") as Bitmap
                imgView.setImageBitmap(imageBitmap)
            }
        }
    private var resultSelectImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                imgView.setImageURI(it.data?.data)
            }
        }
    fun buttonOnClick(view: View) {
        if (view.id == R.id.buttonOK) {
            if (plainText.text.toString() != "") {
                setResult(RESULT_OK)
                contactsShowed[position].name = plainText.text.toString()
                contactsShowed[position].avatar = imgView.drawToBitmap(Bitmap.Config.ARGB_8888)
                if (toDo == "CreateNew") {
                    contacts.add(contactsShowed[position])
                }
            } else {
                Toast.makeText(this, "Enter the name of contact", Toast.LENGTH_LONG).show()
                plainText.requestFocus()
                return
            }
        } else {
            if (toDo == "CreateNew") {
                contactsShowed.removeAt(position)
            }
        }
        finish()
    }
}


