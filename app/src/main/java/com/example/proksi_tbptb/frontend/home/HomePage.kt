package com.example.proksi_tbptb.frontend.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.ui.theme.BackgroundColor

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Column (modifier = modifier
        .fillMaxSize()
        .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Page")
        Button(
            onClick = {},
            modifier = Modifier.padding(top = 16.dp),
            content = {
                Text(text = "Logout")
            }
        )
        Button(
            onClick = {},
            modifier = Modifier.padding(top = 16.dp),
            content = {
                Text(text = "Go to Profile")
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomePage() {
    HomePage()
}