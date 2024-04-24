@file:OptIn(ExperimentalMaterial3Api::class)

package tw.edu.pu.s1114859.composefirestore

import android.os.Bundle
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

        Text("您輸入的姓名是：$userName\n您的出生體重為:$userWeight 公克")

    }
}