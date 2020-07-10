package com.example.covidct

import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.test_covid.Classifier
import kotlin.coroutines.coroutineContext

class VersionsAdapter(val versionList: List<Versions>) :

    RecyclerView.Adapter<VersionsAdapter.VersionVH>() {
    class VersionVH(itemView: View) : RecyclerView.ViewHolder(itemView){
            var codeNameTxt : TextView = itemView.findViewById(R.id.textView1)
            var secondHeadTxt : TextView = itemView.findViewById(R.id.textView2)
            var buttonTxt : Button = itemView.findViewById(R.id.buttonView)
            var linearLayout : LinearLayout = itemView.findViewById(R.id.lineLayout)
            var expandLayout : RelativeLayout = itemView.findViewById(R.id.expandable)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VersionVH {
            val view : View = LayoutInflater.from(p0.context).inflate(R.layout.row,p0, false)
            return  VersionVH(view)
    }

    override fun getItemCount(): Int {
            return  versionList.size
    }



    override fun onBindViewHolder(p0: VersionVH, p1: Int) {
            val versions : Versions = versionList[p1]
            p0.codeNameTxt.text = versions.code
            p0.secondHeadTxt.text = versions.subText
            p0.buttonTxt.text = versions.button_Text

            val isExpandable : Boolean = versionList[p1].expandable
            p0.expandLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
            p0.linearLayout.setOnClickListener{
                val versions = versionList[p1]
                versions.expandable = !versions.expandable
                notifyItemChanged(p1)
                if(p1 == 0){
                    Log.d("First Portion","Clicked")
                }
            }
            p0.buttonTxt.setOnClickListener {
                if(p1 == 0){
                    Log.d("Button","Button 1 Clicked")
                    val i = Intent(ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/prabharoop-c-c-809868144/"))
                    startActivity(p0.buttonTxt.context, i, i.extras)
                    }

                if(p1 == 1){
                    Log.d("Button","Button 2 Clicked")
                    val j = Intent()
                    j.setType("image/*")
                    j.setAction(ACTION_GET_CONTENT)
                    startActivity(p0.buttonTxt.context,j,j.extras)
                    }
                }
                if(p1 == 2){
                    Log.d("Button","Button 3 Clicked")
                    
                }
            }
    }
