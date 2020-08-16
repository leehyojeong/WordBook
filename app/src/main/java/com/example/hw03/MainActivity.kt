package com.example.hw03

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var visited=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent1= Intent(this,meaning::class.java)
        val intent2= Intent(this,word::class.java)
        val intent3= Intent(this,wronglist::class.java)
        val intent4= Intent(this,favorite::class.java)


        main_btn1.setOnClickListener {
            startActivity(intent1)
            visited+=1
        }
        main_btn2.setOnClickListener {
            startActivity(intent2)
            visited+=1
        }
        //아직 위의 메뉴를 한 번도 실행시키지 않은 경우 오답리스트 메뉴를 막아둔다.
        main_btn3.setOnClickListener {
            if(visited==0){
                Toast.makeText(this,"아직 등록된 오답리스트가 없습니다 :)",Toast.LENGTH_SHORT).show()
                main_btn3.isEnabled=false;
            }else{
                main_btn3.isEnabled=true;
                startActivity(intent3)
            }
        }
        main_btn4.setOnClickListener {
            startActivity(intent4)
        }
    }
}
