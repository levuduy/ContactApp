

package com.huynhminhdao.contactsproject

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts

val contacts = ArrayList<Contact>()
val contactsShowed = ArrayList<Contact>()

class MainActivity : AppCompatActivity() {
    private lateinit var contactsList: ListView
    private lateinit var contactsListAdapter: ContactsListAdapter
    private lateinit var search: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sampleContacts()
        search = findViewById(R.id.search)
        contactsList = findViewById(R.id.contactsList)
        contactsListAdapter = ContactsListAdapter(this, contactsShowed)
        contactsList.adapter = contactsListAdapter
        contactsList.setOnItemClickListener { parent, view, position, id ->
            val intentEdit = Intent(this, EditActivity::class.java).apply {
                putExtra("position", position)

            }
            startActivity(intentEdit)
        }


        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val contactFiltered = contacts.filter {
                        it.name.contains(newText, true)
                    }
                    contactsShowed.clear()
                    for (c in contactFiltered) {
                        contactsShowed.add(c)
                    }
                    contactsListAdapter.notifyDataSetChanged()
                }
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if (menu != null) {
            menuInflater.inflate(R.menu.menu_main, menu)
            true
        } else {
            super.onCreateOptionsMenu(menu)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newContact -> {
                contactsShowed.add(Contact(BitmapFactory.decodeResource(this.resources, R.drawable.avatar_n
            ),"name"))
                contactsListAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sampleContacts() {
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar0), "Dang Van Lam"))
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar1), "Doan Van Hau"))
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar2), "Nguyen Trong Hoang"))
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar3), "Nguyen Tien Linh"))
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar4), "Nguyen Quang Hai"))
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar5), "Vu Van Thanh"))
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar6), "Nguyen Cong Phuong"))
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar7), "Nguyen Van Toan"))
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar8), "Que Ngoc Hai"))
        contacts.add(Contact( BitmapFactory.decodeResource(this.resources, R.drawable.avatar9), "Do Hung Dung"))
        for (contact in contacts) {
            contactsShowed.add(contact)
        }

    }
}


