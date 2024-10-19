package mobiledev.unb.ca.composelistlab

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mobiledev.unb.ca.composelistlab.models.Course
import mobiledev.unb.ca.composelistlab.models.dummyData
import mobiledev.unb.ca.composelistlab.ui.theme.ComposeListLabSkeletonTheme
import mobiledev.unb.ca.composelistlab.utils.JsonUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeListLabSkeletonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent()
                }
            }
        }
    }

//    fun goToDetailActivity(course: Course) {
//        val intent = Intent(this, DetailActivity::class.java).apply {
//            putExtra(Constants.INTENT_EXTRA_COURSE_TITLE, course.title)
//            putExtra(Constants.INTENT_EXTRA_COURSE_DESCRIPTION, course.description)
//        }
//        try {
//            startActivity(intent)
//        } catch (e: ActivityNotFoundException) {
//            Log.e(TAG, "Unable to start activity", e)
//        }
//    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}

// Creating a composable function to display the Top Bar
//@Composable
//fun AppTopBar() {
//    val context = LocalContext.current
//    TopAppBar(
//        title = {
//            Text(
//                context.getString(R.string.app_name),
//                color = Color.White
//            )
//        },
//        backgroundColor = Color.Blue
//    )
//}

@Composable
fun MainContent() {
    val context = LocalContext.current
//    Scaffold(modifier = Modifier.fillMaxSize()) {
//        AppTopBar()
//        MainActivityContent(context)
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        context.getString(R.string.app_name),
                        color = Color.White
                    )
                },
                backgroundColor = MaterialTheme.colors.primary
                        // Color.Blue
            )
//            (0xff0f9d58)
        },
        content = { MainActivityContent(context) }
    )
}

@Composable
fun MainActivityContent(context: Context) {
    // val courseListing = dummyData()
    val courseListing = JsonUtils(context).courses
    RecyclerView(courseListing)
}

@Composable
fun RecyclerView(courseListing: List<Course>) {
    LazyColumn {
        items(items = courseListing) {
            CourseItem(it)
        }
    }
}

//return   ClickableText(onClick = {
//    goNext(course)
//
//},
//text = AnnotatedString(course.title),
//modifier = Modifier.padding(Dp(15F))
//)

//@Composable
private fun goToDetailActivity(course: Course, context: Context) {
//    val context = LocalContext.current
    val intent = Intent(context, DetailActivity::class.java).apply {
        putExtra(Constants.INTENT_EXTRA_COURSE_TITLE, course.title)
        putExtra(Constants.INTENT_EXTRA_COURSE_DESCRIPTION, course.description)
    }
    try {
       context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Log.e("MainActivity", "Unable to start activity", e)
    }
}

@Composable
fun CourseItem(course: Course) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = 4.dp
    ) {
        // Start with the row; add in the card and image
        Row(modifier = Modifier.padding(5.dp)) {
            // Step 1 - Show the text
            // Text(text = course.title, modifier = Modifier.padding(5.dp))
            // Step 2 - Make the text area clickable
            ClickableText(
                text = AnnotatedString(course.title),
                modifier = Modifier.padding(5.dp),
                ) {
                    goToDetailActivity(course, context)
//                    val intent = Intent(context, DetailActivity::class.java).apply {
//                        putExtra(Constants.INTENT_EXTRA_COURSE_TITLE, course.title)
//                        putExtra(Constants.INTENT_EXTRA_COURSE_DESCRIPTION, course.description)
//                    }
//                    try {
//                        context.startActivity(intent)
//                    } catch (e: ActivityNotFoundException) {
//                        Log.e("MainActivity", "Unable to start activity", e)
//                    }
            }
        }

//        return   ClickableText(onClick = {
//            goNext(course)
//
//        },
//            text = AnnotatedString(course.title),
//            modifier = Modifier.padding(Dp(15F))
//        )
//        Row(modifier = Modifier.padding(5.dp)) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_image),
//                contentDescription = "image",
//                modifier = Modifier
//                    .padding(8.dp)
//                    .size(60.dp)
//                    .clip(RoundedCornerShape(CornerSize(6.dp)))
//                    .align(alignment = Alignment.CenterVertically)
//            )
//
//            Text(text = userDetail, modifier = Modifier.padding(10.dp, 20.dp))
//        }
    }
}

//@Composable
//fun MyContent() {
//    // ..
//    Button(
//        text = "Go Home",
//        onClick = actionStartActivity<MyActivity>()
//    )
//}
//
//fun T actionStartActivity<T>() {
//
//}

//@Composable
//fun CourseItem(course: Course, modifier: Modifier = Modifier) {
//    Row(modifier = Modifier.padding(5.dp)) {
//        Text(text = course.title, modifier = Modifier.padding(5.dp))
//    }
//}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    ComposeListLabSkeletonTheme {
        MainContent()
    }
}