package gg.dsepractice.chinese.frontend.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.ComponentActivity

class ScheduleScreen : ComponentActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_list)
        val scheduleList = intent.getStringArrayListExtra("schedule")
        val dateList = intent.getStringArrayListExtra("date")
        //val article = intent.getStringExtra("articleName")
        val list = arrayListOf<MutableMap<String, Any>>()
        for (i in scheduleList!!.indices) {
            val map: MutableMap<String, Any> = HashMap()
            map["date"] = "" + dateList?.get(i)
            map["toDo"] = scheduleList[i]
            list.add(map)
        }
        val adapter = SimpleAdapter(
            this,
            list,
            R.layout.schedule_list,
            arrayOf("date", "toDo"),
            intArrayOf(R.id.scheduleDay, R.id.toDo)
        )
        val list_view: ListView = findViewById(R.id.listView)
        list_view.adapter = adapter
    }
}

   /*     setContent(){
            Box(){
                for (i in 0..15) {
                    Card(
                        modifier = androidx.compose.ui.Modifier
                            .padding(all = 10.dp)
                            .height(150.dp)
                            .width(350.dp)
                            //.fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                    )
                    {
                        Column(modifier = androidx.compose.ui.Modifier.width(5.dp)) {
                            Text(
                                studentList[i],
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                modifier = androidx.compose.ui.Modifier.padding(10.dp)
                            );
                        };
                    }
                }

            }
        }
    }
}


*/