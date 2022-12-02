package gg.dsepractice.chinese.frontend.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject



@AndroidEntryPoint
class ScheduleActivity : ComponentActivity() {

    private var add: Button? = null
    private var scheduleDate: EditText? = null
    private var scheduleData: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_schedule)

        add = findViewById(R.id.add)
        scheduleDate=findViewById<EditText>(R.id.scheduleDate)
        scheduleData=findViewById<EditText>(R.id.scheduleData)

        add!!.setOnClickListener {
            val date:String = scheduleDate!!.text.toString()
            val data:String = scheduleData!!.text.toString()
            sendMessage(date,data)
            //Toast.makeText(getApplicationContext(),"${data}+${date}",Toast.LENGTH_SHORT).show();
        }

/*        setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    addSchedule()
                }
            }*/

    }

    @Composable
    private fun addSchedule() {
        var date by remember { mutableStateOf("") }
        var data by remember { mutableStateOf("") }

        Column {
            TextField(
                value = date,
                modifier = Modifier.padding(10.dp),
                onValueChange = { date = it }
            )
            TextField(
                value = data,
                modifier = Modifier.padding(10.dp),
                onValueChange = { data = it },
            )
            Button(onClick = {sendMessage(date,data)}) {
                Text("Submit")
            }
        }

        //Toast.makeText(this, date, Toast.LENGTH_LONG).show()
    }




    private fun sendMessage(date:String,data:String) {
        val url:String = "http://127.0.0.1:8888/scheduleadd?date=${date}&&data=${data}"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                switchActivity(response)
            },
            { error ->
                Log.e("ScheduleActivity",error.toString())
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    private fun switchActivity(jsonObj: JSONObject){
        val schedule: JSONArray = jsonObj.get("Schedule") as JSONArray
        val date: JSONArray = jsonObj.get("Date") as JSONArray
        val scheduleList = arrayListOf<String>()
        val dateList = arrayListOf<String>()

        for (i in 0 until schedule.length()-1){
            scheduleList.add(schedule.get(i) as String)
            dateList.add(date.get(i) as String)
        }

        val intent = Intent(this, ScheduleScreen::class.java).apply {
            putStringArrayListExtra("schedule", scheduleList)
            putStringArrayListExtra("date", dateList)
            //putExtra("articleName", name)
        }

        startActivity(intent)
    }


/*    @Composable
    fun AddScheduleScreen(
        navigateToMode: () -> Unit,
    ){
        val intent = Intent(this, ScheduleActivity::class.java).apply {
        }
        startActivity(intent)

    }*/

}





