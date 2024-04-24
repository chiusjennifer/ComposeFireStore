@file:OptIn(ExperimentalMaterial3Api::class)

package tw.edu.pu.s1114859.composefirestore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tw.edu.pu.s1114859.composefirestore.ui.theme.ComposeFireStoreTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.material3.TextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.Button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.input.KeyboardType
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFireStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    Birth()
                }
            }
        }
    }
}

@Composable
fun Birth(){
    var userName by remember { mutableStateOf("邱家妤")}
    var userWeight by remember { mutableStateOf(3800) }
    var userPassword by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("訊息") }
    val db = Firebase.firestore
    Column {
        TextField(
            value = userName,
            onValueChange = { newText ->
                userName = newText
            },
            label = {Text("姓名")},
            placeholder = {Text("請輸入您的名字")}

        )
        TextField(
            value =userWeight.toString() ,
            onValueChange = {newText ->
                if(newText==""){
                    userWeight=0
                }else{
                    userWeight=newText.toInt()
                }

            },
            label = {Text("體重")},
        keyboardOptions = KeyboardOptions
            (keyboardType = KeyboardType.Number)
        )
        TextField(
            value = userPassword,
            onValueChange = { newText ->
                userPassword = newText
            },
            label = { Text("密碼") },
            placeholder = { Text(text = "請輸入您的密碼") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions
                (keyboardType = KeyboardType.Password)
        )



        Text("您輸入的姓名是：$userName\n您的出生體重為:$userWeight 公克\n您輸入的密碼是:$userPassword")
        Row{
            Button(onClick = {
                val user=Person(userName,userWeight,userPassword)
                db.collection("DB")
                    //.add(user)
                    .document(userName)
                    .set(user)
                    .addOnSuccessListener { documentReference ->
                        msg="新增/異動資料成功"
                    }
                    .addOnFailureListener { e ->
                        msg="新增/異動資料失敗:"+e.toString()
                    }
            }) {
                Text("新增/修改資料")
            }
            Button(onClick = {
                db.collection("DB")
                    .whereEqualTo("userName",userName)
                    .get()
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            msg=""
                            for(document in task.result!!){
                                msg+="文件id:"+document.id+"\n名字:"+document.data["userName"]+
                                        "\n出生體重：" + document.data["userWeight"].toString() + "\n\n"
                            }
                            if (msg == "") {
                                msg = "查無資料"
                            }
                        }
                    }
            }){
                Text("查詢資料")
            }
            Button(onClick = {}) {
                Text("刪除資料")
            }
        }
        Text(text=msg)
    }
}
data class Person(
    var userName:String,
    var userWeight:Int,
    var userPassword:String
)