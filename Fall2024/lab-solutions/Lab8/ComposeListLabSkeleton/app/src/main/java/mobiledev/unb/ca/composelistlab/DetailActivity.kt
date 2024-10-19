package mobiledev.unb.ca.composelistlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mobiledev.unb.ca.composelistlab.ui.theme.ComposeListLabSkeletonTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currIntent = intent
        val courseTitle = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_TITLE)
        val courseDesc = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_DESCRIPTION)

        setContent {
            ComposeListLabSkeletonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DetailContent(title = courseTitle, description = courseDesc, navigateBack = { finish() })
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    title: String?,
    description: String?,
    navigateBack: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    title?.let { TextTitle(title = title, color = Color.White) }
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
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            description?.let {
                TextTitle(title = description)
            }
        }
    }
}


@Composable
fun TextTitle(title: String, color: Color = Color.Unspecified) {
    Text(
        title,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
fun DescriptionActivityPreview() {
    ComposeListLabSkeletonTheme {
        DetailContent(
            title = "Test title",
            description = "This is a test description",
            navigateBack = {})
    }
}
