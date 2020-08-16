package com.example.hw03

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_meaning.*
import kotlinx.android.synthetic.main.activity_wronglist.*
import kotlinx.android.synthetic.main.wrong_cview_layout.view.*

class wronglist : AppCompatActivity() {

    //틀린 단어를 담을 배열
    //화면 간 공유를 해야한다.
    var wrongitems:ArrayList<MyWrong> = ArrayList()
    lateinit var adapter:MyWrongAdapter
    lateinit var popup:PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wronglist)
        init()
    }
    fun init(){
        initlayout()
        initSwipe()
        showmenu()
    }
    fun initlayout(){
        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        wrong_cview.layoutManager=layoutManager
//        adapter= MyWrongAdapter(wrongitems)
        val intent = getIntent()
        //아래 배열은 잘 들어감.
            wrongitems = intent.getParcelableArrayListExtra("wrong_meaning")
            adapter = MyWrongAdapter(wrongitems)
            adapter.notifyDataSetChanged()
            wrong_cview.adapter = adapter
    }
    fun initSwipe(){
        val simpleItemTouchCallback=object:ItemTouchHelper.SimpleCallback(UP or DOWN,RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                adapter.moveItem(p1.adapterPosition,p2.adapterPosition)
                return true
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Log.v("**어댑터포지션",p0.adapterPosition.toString())
                adapter.removeItem(p0.adapterPosition)
            }
        }
        val itemTouchHelper=ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(wrong_cview)
    }
    fun showmenu(){
        val intent2= Intent(this,meaning::class.java)
        val intent3= Intent(this,word::class.java)
        val intent4= Intent(this,favorite::class.java)

        popup= PopupMenu(this,topmenu_wrong)
        popup.setOnMenuItemClickListener(){
            if(it.itemId==2){
                startActivity(intent2)
            }
            else if(it.itemId==3){
                startActivity(intent3)
            }
            else{
                startActivity(intent4)
            }
            return@setOnMenuItemClickListener false
        }

        var menu=popup.menu
        menu.add(0,2,0,"뜻 맞추기")
        menu.add(0,3,0,"단어 맞추기")
        menu.add(0,4,0,"문제 선택하기")

        topmenu_wrong.setOnClickListener {
            popup.show()
        }
    }
}
