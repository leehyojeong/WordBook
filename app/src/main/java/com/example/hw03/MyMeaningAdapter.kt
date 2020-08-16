package com.example.hw03

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class MyMeaningAdapter(context: Context,val resource:Int,var list:ArrayList<MyMeaning>)
    :ArrayAdapter<MyMeaning>(context,resource,list)
{
    class ViewHolder{
        var meaning:TextView?=null
        var w_count:TextView?=null
        var fav_box:CheckBox?=null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v:View?=convertView
        var holder:ViewHolder

        if(v==null){
            val vi=context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v=vi.inflate(R.layout.meaning_row,null)

            holder= ViewHolder()
            holder.meaning=v!!.findViewById(R.id.meaning)
            holder.w_count=v!!.findViewById(R.id.w_count)
            holder.fav_box=v!!.findViewById(R.id.fav)

            v.tag=holder
        }
        else{
            holder=convertView?.tag as ViewHolder
            v=convertView
        }
        val p=list.get(position)
        v!!.findViewById<TextView>(R.id.meaning).text=p.meaning
        v!!.findViewById<TextView>(R.id.w_count).text=p.w_count.toString()
        holder.fav_box?.isChecked=p.fav

        val r=v!!.findViewById<CheckBox>(R.id.fav)
        r.setOnCheckedChangeListener { compoundButton, b ->
            if(v!!.findViewById<CheckBox>(R.id.fav).isChecked) {
                Toast.makeText(parent.context,"즐겨찾기 등록 완료!",Toast.LENGTH_SHORT).show()
                v!!.findViewById<CheckBox>(R.id.fav).isChecked=true
                p.fav=true
            }
            else{
                Toast.makeText(parent.context,"즐겨찾기 해제",Toast.LENGTH_SHORT).show()
                v!!.findViewById<CheckBox>(R.id.fav).isChecked=false
                p.fav=false
            }
        }
        return v
    }
}