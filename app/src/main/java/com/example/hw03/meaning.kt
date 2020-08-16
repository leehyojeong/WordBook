package com.example.hw03

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_meaning.*
import kotlinx.android.synthetic.main.engwords_row.*
import kotlinx.android.synthetic.main.meaning_row.*
import java.util.*
import java.util.function.BinaryOperator
import kotlin.collections.ArrayList

class meaning : AppCompatActivity() {
    //영어 단어가 문제로 나오고 한글 뜻 맞추기
    lateinit var dict1:MutableMap<String,MyMeaning>
    lateinit var keySet:ArrayList<String>
    lateinit var quiz:ArrayList<MyMeaning>
    lateinit var adapter:MyMeaningAdapter

    var index:Int = 0

    lateinit var popup:PopupMenu
    //넘겨줄 틀린 배열
    lateinit var wrongitem_meaning:ArrayList<MyWrong>
    //넘어온 선택 배열
    lateinit var chosen_meaning:ArrayList<MyEngwords>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaning)
        init()
    }
    fun init(){

        dict1= mutableMapOf()
        keySet= ArrayList()
        quiz= ArrayList()
        readFile()
        keySet=ArrayList(dict1.keys)
        wrongitem_meaning=ArrayList()
        chosen_meaning=ArrayList()
        adapter=MyMeaningAdapter(this,R.layout.meaning_row,quiz)

        makeQuiz()
        addListener()
        showmenu()
    }
    fun showmenu() : Unit{
        val intent2= Intent(this,word::class.java)
        val intent3= Intent(this,wronglist::class.java)
        val intent4= Intent(this,favorite::class.java)
        popup= PopupMenu(this,topmenu)
        popup.setOnMenuItemClickListener(){
            if(it.itemId==2){
                startActivity(intent2)
            }
            else if(it.itemId==3){
                intent3.putParcelableArrayListExtra("wrong_meaning",wrongitem_meaning)
                startActivity(intent3)
            }
            else{
                startActivity(intent4)
            }
            return@setOnMenuItemClickListener false
        }

        var menu=popup.menu
        menu.add(0,2,0,"단어 맞추기")
        menu.add(0,3,0,"오답리스트")
        menu.add(0,4,0,"문제 선택하기")

        topmenu.setOnClickListener {
            popup.show()
        }
    }
    fun addListener(){
        answer_kor.setOnItemClickListener { adapterView, view, i, l ->
//            val intent3= Intent(this,wronglist::class.java)
            val show_eng=keySet[index]
            val engsAns= dict1.get(show_eng)!!

            val w=adapterView.getItemAtPosition(i) as MyMeaning
            val w2=dict1.get(quiz_eng.text)
            if(w.meaning==w2!!.meaning){
                Toast.makeText(this,"정답!",Toast.LENGTH_SHORT).show()
            }
            else{
                //틀린횟수 증가 시키기
                engsAns.w_count+=1
                Toast.makeText(this,"오답!",Toast.LENGTH_SHORT).show()
                //배열 데이터 넘겨주기 wronglsit 화면으로
                wrongitem_meaning.add(MyWrong(show_eng,engsAns.meaning))
            }
            makeQuiz()
            makeQuiz()
        }
    }
    fun readFile(){
        val scan= Scanner(resources.openRawResource(R.raw.words))

        while(scan.hasNextLine()){
            val eng=scan.nextLine()
            val kor=scan.nextLine()

            dict1.put(eng, MyMeaning(kor,0,false))
        }
        scan.close()
    }
    fun makeQuiz(){
        lateinit var strWord:String
        var strAnswer:MyMeaning?

        var intent: Intent=getIntent()
        val intentdata:String?=intent.getStringExtra("intentdata")

        if(intentdata=="fromfav"){
            chosen_meaning = intent.getParcelableArrayListExtra("chosenformeaning")
            index=Random().nextInt(chosen_meaning.size)
            strWord= chosen_meaning[index].engwords
            strAnswer=dict1.get(chosen_meaning[index].engwords)
            Log.v("strAnswer",strAnswer.toString())
        }else{
            index=Random().nextInt(dict1.size)
            strWord=keySet[index]
            strAnswer= dict1.get(strWord)!!
            Log.v("strAnswer2",strAnswer.toString())
        }
        quiz=ArrayList(dict1.values)
        quiz.shuffle()
        quiz=ArrayList(quiz.subList(0,4))
        quiz.add(strAnswer!!)
        quiz.shuffle()
        quiz_eng.text=strWord
        adapter=MyMeaningAdapter(this,R.layout.meaning_row,quiz)
        answer_kor.adapter=adapter
    }
}
