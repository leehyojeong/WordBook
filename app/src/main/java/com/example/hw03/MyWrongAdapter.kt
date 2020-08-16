package com.example.hw03

import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MyWrongAdapter (val wrongitems:ArrayList<MyWrong>)
    :RecyclerView.Adapter<MyWrongAdapter.ViewHolder>()
{
    fun moveItem(pos1:Int,pos2:Int){
        val item1=wrongitems.get(pos1)
        wrongitems.removeAt(pos1)
        wrongitems.add(pos2,item1)
        notifyItemMoved(pos1,pos2)
    }
    fun removeItem(pos:Int){
        wrongitems.removeAt(pos)
        notifyItemRemoved(pos)
//        Log.i("어댑터에서 배열 길이",wrongitems.size.toString())
    }
    inner class ViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView)
    {
        var eng:TextView
        var kor:TextView
        init{
            eng=itemView.findViewById(R.id.eng)
            kor=itemView.findViewById(R.id.kor)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v=LayoutInflater.from(p0.context).inflate(R.layout.wrong_cview_layout,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return wrongitems.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        p0.eng.text=wrongitems.get(p1).eng.toString()
        p0.kor.text=wrongitems.get(p1).kor.toString()
    }
}