package com.example.hw03

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MyFavAdapter(context: Context,val resources:Int, var favitmes:ArrayList<MyFav>)
    :ArrayAdapter<MyFav>(context,resources,favitmes)
{
    class ViewHolder{
        var eng:TextView?=null
        var kor:TextView?=null
        var bluebox:CheckBox?=null
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v=convertView
        var holder:ViewHolder
        if(v==null){
            val vi=context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v=vi.inflate(R.layout.fav_row,null)

            holder= ViewHolder()
            holder.eng=v!!.findViewById(R.id.eng)
            holder.kor=v!!.findViewById(R.id.kor)
            holder.bluebox=v!!.findViewById(R.id.fav)

            v.tag=holder
        }else{
            holder=convertView?.tag as ViewHolder
            v=convertView
        }

        val p=favitmes.get(position)
        v!!.findViewById<TextView>(R.id.eng).text=p.eng
        v.findViewById<TextView>(R.id.kor).text=p.kor
        holder.bluebox?.isChecked=p.blueCheck

        val r= v.findViewById<CheckBox>(R.id.fav)
        r.setOnCheckedChangeListener { compoundButton, b ->
            p.blueCheck = p.blueCheck != true
        }
        return v
    }
}