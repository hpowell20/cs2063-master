package mobiledev.unb.ca.composelistlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mobiledev.unb.ca.composelistlab.ui.theme.ComposeListLabSkeletonTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val currIntent = intent
//        val courseTitle = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_TITLE)
//        val courseDesc = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_DESCRIPTION)
        // val courseDesc = currIntent.extras?.getString(Constants.INTENT_EXTRA_COURSE_DESCRIPTION)

        setContent {
            ComposeListLabSkeletonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TopActionBar(currIntent = intent, navigateBack = { finish() })
                    // TopActionBar(currIntent = intent)
//                    courseTitle?.let { TopActionBar(courseTitle) }
//                    courseDesc?.let { DescriptionTextView(description = courseDesc) }
                }
            }
        }
    }
}

@Composable
fun TopActionBar(
    currIntent: Intent,
    navigateBack: () -> Unit,
) {
    val courseTitle = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_TITLE)
    val courseDesc = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_DESCRIPTION)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    courseTitle?.let { TextTitle(title = courseTitle, color = Color.White) }
//                    Text(
//                        courseTitle,
//                        color = Color.White
//                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 15.dp)
                .fillMaxWidth(),
        ) {
            courseDesc?.let {
                TextTitle(title = courseDesc)
//            Text(courseDesc,
//                modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

//@Composable
//fun TopActionBar(currIntent: Intent) {
//    val context = LocalContext.current
//    val courseTitle = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_TITLE)
//    val courseDesc = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_DESCRIPTION)
////    courseTitle?.let { TopActionBar(courseTitle) }
//    // courseDesc?.let { DescriptionTextView(description = courseDesc) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                navigationIcon = {
//                    IconButton(onClick = {
//                        /* do something */
//
//                    }) {
//                        Icon(
//                            imageVector = Icons.Rounded.ArrowBack,
//                            contentDescription = "Go Back"
//                        )
//                    }
//                },
//                title = {
//                    courseTitle?.let { TextTitle(title = courseTitle) }
////                    Text(
////                        courseTitle,
////                        color = Color.White
////                    )
//                },
//                backgroundColor = MaterialTheme.colors.primary
//            )
////            (0xff0f9d58)
//        },
//    ) { innerPadding ->
//        courseDesc?.let {
//            Text(courseDesc,
//            modifier = Modifier.padding(innerPadding))
//        }
//    }
//}

@Composable
fun TextTitle(title: String, color: Color = Color.Unspecified) {
    Text(
        title,
        color = color
    )
}
//@Composable
//fun Greeting() {
//    Text(text = "Hello")
//}

@Composable
fun SmallTopAppBar(courseName: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                },
                title = {
                    Text(
                        courseName,
                        color = Color.White
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
//            (0xff0f9d58)
        },
//        topBar = {
//            TopAppBar(
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
//                ),
//                title = {
//                    Text("Small Top App Bar")
//                }
//            )
//        },
    ) { // innerPadding ->
        // ScrollContent(innerPadding)
    }
}

@Composable
fun DescriptionTextView(description: String) {
    Text(text = description)
}

@Preview(showBackground = true)
@Composable
fun DescriptionActivityPreview() {
    ComposeListLabSkeletonTheme {
        DescriptionTextView("This is a test description")
    }
}
