package com.example.hw03

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_meaning.*
import java.util.*
import kotlin.collections.ArrayList

class favorite : AppCompatActivity() {
    //모든 단어 저장
    lateinit var allwords:ArrayList<MyFav>
    lateinit var adapter:MyFavAdapter
    lateinit var chosenlist:ArrayList<MyFav>
    lateinit var popup:PopupMenu

    //넘겨줄 배열 2개
    lateinit var chosenformeaning:ArrayList<MyEngwords>
    lateinit var chosenforengword:ArrayList<MyMeaning>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        init()
    }
    fun init(){
        showmenu()
        allwords= ArrayList()
        chosenlist= ArrayList()
        chosenformeaning= ArrayList()
        chosenforengword= ArrayList()
        readFile()
        adapter= MyFavAdapter(this,R.layout.fav_row,allwords)
        favlistview.adapter=adapter
        choosing()
    }
    fun choosing(){
        val intent1= Intent(this,meaning::class.java)
        val intent2= Intent(this,word::class.java)

        //뜻 문제 출제
        del_btn1.setOnClickListener {
            //전체 돌면서 check 되어 있으면 chosenlist에 집어넣기
            for(i in 0..allwords.size-1){
                if(allwords[i].blueCheck==true){
//                    chosenlist.add(allwords[i])
                    chosenformeaning.add(MyEngwords(allwords[i].eng,0,false))
                }
            }
            Toast.makeText(this, "문제가 선택되었습니다 :)", Toast.LENGTH_SHORT).show()
            intent1.putExtra("intentdata","fromfav")
            intent1.putParcelableArrayListExtra("chosenformeaning",chosenformeaning)
            startActivity(intent1)
        }
        //단어 문제 출제
        del_btn2.setOnClickListener {
            //전체 돌면서 check 되어 있으면 chosenlist에 집어넣기
            for(i in 0..allwords.size-1){
                if(allwords[i].blueCheck==true){
//                    chosenlist.add(allwords[i])
                    chosenforengword.add(MyMeaning(allwords[i].kor,0,false))
                }
            }
            Toast.makeText(this, "문제가 선택되었습니다 :)", Toast.LENGTH_SHORT).show()
            intent2.putExtra("intentdata","fromfav")
            intent2.putParcelableArrayListExtra("chosenforengword",chosenforengword)
            startActivity(intent2)
        }
    }
    fun readFile(){
        val scan= Scanner(resources.openRawResource(R.raw.words))
        while(scan.hasNextLine()){
            val eng=scan.nextLine()
            val kor=scan.nextLine()
            allwords.add(MyFav(eng,kor,false))
        }
        scan.close()
    }
    fun showmenu() : Unit{
        val intent2= Intent(this,meaning::class.java)
        val intent3= Intent(this,word::class.java)
        val intent4= Intent(this,wronglist::class.java)
        popup= PopupMenu(this,topmenu_fav)
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
        menu.add(0,4,0,"오답리스트")

        topmenu_fav.setOnClickListener {
            popup.show()
        }
    }
}
