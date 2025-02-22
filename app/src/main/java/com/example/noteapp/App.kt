package com.example.noteapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App(modifier: Modifier = Modifier) {
    var showTextField by rememberSaveable { mutableStateOf(false) }
    var textFieldValue by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf(listOf<String>()) }
    var editIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            //Top Column Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFF4791fb))
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "Notes App",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }

            //Displaying Notes
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp)
                    .background(Color.White)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(notes.size) { index ->
                        NoteItem(
                            notes[index],
                            onDelete = {
                                notes = notes.toMutableList().apply {
                                    removeAt(index)
                                }
                            },
                            onClick = {
                                editIndex = index
                                textFieldValue = notes[index]
                                showTextField = true
                            }
                        )
                    }
                }
            }

            //Add Button UI
            FloatingActionButton(
                onClick = { showTextField = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                )
            }

            //Add Button Logic to open text field
            if (showTextField) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                        .padding(top = 200.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFF4791fb))
                        .padding(top = 7.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Add Note",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color(0xFF4791fb))
                            .padding(20.dp),
                        shape = RoundedCornerShape(15.dp),
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        placeholder = { Text(text = "Enter Note Here...") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                textFieldValue = ""
                                showTextField = false
                                editIndex = null
                            }
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                if (textFieldValue.isNotBlank()) {
                                    notes = if (editIndex != null) {
                                        notes.toMutableList().apply {
                                            this[editIndex!!] = textFieldValue
                                        }
                                    } else {
                                        notes + textFieldValue
                                    }
                                }
                                textFieldValue = ""
                                showTextField = false
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = "Save",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(index: String, onClick: () -> Unit, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp)
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.5.dp, Color(0xFF4791fb), RoundedCornerShape(15.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(250.dp),
            text = index,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            onClick = { onDelete() }
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Red
            )
        }
    }
}
