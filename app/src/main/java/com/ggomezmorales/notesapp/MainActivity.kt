package com.ggomezmorales.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    private val sharedPreferences by lazy {
        getSharedPreferences("notes", MODE_PRIVATE)
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initSharePreferences(sharedPreferences)
        viewModel.loadData()

        setContent {
            val titleState by viewModel.titleState.collectAsState()
            val descriptionState by viewModel.descriptionState.collectAsState()
            val enableButton = titleState.isNotBlank() && descriptionState.isNotBlank()

            CardNote(
                titleValue = titleState,
                descriptionValue = descriptionState,
                onTitleChange = viewModel::updateTitle,
                onDescriptionChange = viewModel::updateDescription,
                enableSaveButton = enableButton,
                onSaveClicked = viewModel::saveData
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CardNote(
    modifier: Modifier = Modifier,
    titleValue: String = "This is a title",
    descriptionValue: String = "This is a description",
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    enableSaveButton: Boolean = false,
    onSaveClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Title", fontSize = 24.sp)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = titleValue,
            onValueChange = onTitleChange
        )
        Text(text = "Description", fontSize = 24.sp)
        InputTextArea(
            modifier = Modifier.height(200.dp),
            textValue = descriptionValue,
            onTextChange = onDescriptionChange
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSaveClicked,
            enabled = enableSaveButton
        ) {
            Text(text = "Save")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(heightDp = 300)
@Composable
fun InputTextArea(
    modifier: Modifier = Modifier,
    textValue: String = "This is a description",
    onTextChange: (String) -> Unit = {}
) {
    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            value = textValue,
            onValueChange = onTextChange
        )
    }
}
