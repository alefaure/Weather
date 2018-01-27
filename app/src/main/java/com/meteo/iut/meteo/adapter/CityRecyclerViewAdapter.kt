package com.meteo.iut.meteo.adapter

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.meteo.iut.meteo.R
import com.meteo.iut.meteo.data.City
import com.meteo.iut.meteo.database.CityContract
import com.meteo.iut.meteo.database.CityContract.CityEntry
import com.meteo.iut.meteo.database.CityCursorWrapper


class CityRecyclerViewAdapter(
        private val cityListener: CityItemListener) : RecyclerViewCursorAdapter<CityRecyclerViewAdapter.ViewHolder>(), View.OnClickListener {

    interface CityItemListener {
        fun onCitySelected(uriCity: Uri)
        fun onCityDeleted(cursor: Cursor)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.card_view)!!
        val cityNameView = itemView.findViewById<TextView>(R.id.name)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent?.context).inflate(R.layout.item_city, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, cursor: Cursor) {
        val cityValues = CityCursorWrapper(cursor).getCityContentValues()

        with(holder) {
            cardView.tag = ContentUris.withAppendedId(CityContract.CONTENT_URI, cityValues.getAsLong(CityEntry.CITY_KEY_ID))
            cardView.setOnClickListener(this@CityRecyclerViewAdapter)
            cityNameView.text = cityValues.getAsString(CityEntry.CITY_KEY_NAME)
        }
    }

    /*override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ville = villes[position]
        with(holder) {
            cardView.tag = ville
            cardView.setOnClickListener(this@CityRecyclerViewAdapter)
            cityNameView.text = ville.name
        }
    }*/

    override fun onClick(view: View) {
        when(view.id) {
            R.id.card_view -> cityListener.onCitySelected(view.tag as Uri)
        }
    }
}