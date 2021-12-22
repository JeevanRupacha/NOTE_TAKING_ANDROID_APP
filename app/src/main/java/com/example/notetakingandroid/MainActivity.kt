package com.example.notetakingandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notetakingandroid.ui.theme.NoteTakingAndroidTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTakingAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}

@Preview()
@Composable
fun previewCard(
    @PreviewParameter(PreviewCardParameterProvider::class) user: User
)
{
    UserCard(name = user.name, age = user.age)
}

class PreviewCardParameterProvider : PreviewParameterProvider<User>
{
    override val values  = sequenceOf(
        User("John Miller", 45)
    )
}

data class User(val name:String, val age :Int)


@Composable
fun UserCard(name: String , age: Int)
{
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        Text(
            text = "The name is ${name}",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = TextStyle(color = Color.Red, fontSize = 16.sp),
        )
        Text(
            text = "Age is ${age}",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = TextStyle(color = Color.Red, fontSize = 16.sp),
        )

    }
}

