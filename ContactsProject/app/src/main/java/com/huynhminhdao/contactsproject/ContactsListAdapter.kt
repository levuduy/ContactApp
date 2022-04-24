package com.huynhminhdao.contactsproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ContactsListAdapter(context: Context, private val dataSource: ArrayList<Contact>) : BaseAdapter() {
    private val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return dataSource.size
    }
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent:
    ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item, parent,
                false)
        }

        view!!.findViewById<ImageView>(R.id.avatar).setImageBitmap(dataSource[position].avatar)
        view.findViewById<TextView>(R.id.name).text = dataSource[position].name
        return view
    }
}