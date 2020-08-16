package com.example.hw03

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_meaning.*
import kotlinx.android.synthetic.main.activity_word.*
import java.util.*
import kotlin.collections.ArrayList

class word : AppCompatActivity() {
    lateinit var dict2:MutableMap<String,MyEngwords>
    lateinit var keySet:ArrayList<String>
    lateinit var quiz:ArrayList<MyEngwords>
    lateinit var adapter:MyEngwordsAdapter

    var index:Int=0
    lateinit var popup:PopupMenu
    lateinit var wrongitem_word:ArrayList<MyWrong>
    lateinit var chosen_word:ArrayList<MyMeaning>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)
        init()
    }
    fun init(){
        dict2= mutableMapOf()
        keySet=ArrayList()
        quiz=ArrayList()
        readFile()
        keySet=ArrayList(dict2.keys)
        wrongitem_word=ArrayList()
        chosen_word= ArrayList()

        adapter= MyEngwordsAdapter(this,R.layout.engwords_row,quiz)
        showmenu()
        makeQuiz()
        addListener()
    }
    fun showmenu() : Unit{
        val intent2= Intent(this,meaning::class.java)
        val intent3= Intent(this,wronglist::class.java)
        val intent4= Intent(this,favorite::class.java)

        popup= PopupMenu(this,topmenu_word)
        popup.setOnMenuItemClickListener(){
            if(it.itemId==2){
                startActivity(intent2)
            }
            else if(it.itemId==3){
                intent3.putParcelableArrayListExtra("wrong_meaning",wrongitem_word)
                startActivity(intent3)
            }
            else{
                startActivity(intent4)
            }
            return@setOnMenuItemClickListener false
        }

        var menu=popup.menu
        menu.add(0,2,0,"뜻 맞추기")
        menu.add(0,3,0,"오답리스트")
        menu.add(0,4,0,"문제 선택하기")

        topmenu_word.setOnClickListener {
            popup.show()
        }
    }
    fun readFile(){
        var scan=Scanner(resources.openRawResource(R.raw.words))

        while(scan.hasNextLine()){
            val eng=scan.nextLine()
            val kor=scan.nextLine()

            dict2.put(kor, MyEngwords(eng,0,false))
        }
        scan.close()
    }
    fun makeQuiz(){
        lateinit var strword:String
        var strAnswer:MyEngwords?

        var intent: Intent=getIntent()
        val intentdata:String?=intent.getStringExtra("intentdata")

        if(intentdata=="fromfav"){
            chosen_word = intent.getParcelableArrayListExtra("chosenforengword")
            index=Random().nextInt(chosen_word.size)
            strword= chosen_word[index].meaning
            strAnswer=dict2.get(chosen_word[index].meaning)
            Log.v("strAnswer_inword",strAnswer.toString())
        }else{
            index=Random().nextInt(dict2.size)
            strword=keySet[index]
            strAnswer= dict2.get(strword)!!
            Log.v("strAnswer2_inword",strAnswer.toString())
        }
        quiz=ArrayList(dict2.values)
        quiz.shuffle()
        quiz=ArrayList(quiz.subList(0,4))
        quiz.add(strAnswer!!)
        quiz.shuffle()
        quiz_kor.text=strword
        adapter= MyEngwordsAdapter(this,R.layout.engwords_row,quiz)
        answer_eng.adapter=adapter
    }
    fun addListener(){
        answer_eng.setOnItemClickListener { adapterView, view, i, l ->
            val show_kor=keySet[index]
            val korsAns= dict2.get(show_kor)!!

            val w=adapterView.getItemAtPosition(i) as MyEngwords
            val w2=dict2.get(quiz_kor.text)
            if(w.engwords==w2!!.engwords){
                Toast.makeText(this,"정답!",Toast.LENGTH_SHORT).show()
            }
            else{
                korsAns.w_count+=1
                Toast.makeText(this,"오답!",Toast.LENGTH_SHORT).show()
                wrongitem_word.add(MyWrong(korsAns.engwords,show_kor))
            }
            makeQuiz()
        }
    }
}
