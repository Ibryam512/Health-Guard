package com.example.healthguard.presentation.contacts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.healthguard.R
import com.example.healthguard.data.contact.Contact

@Composable
fun AddContactCard(
    modifier: Modifier = Modifier,
    onAddContact: (Contact) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }

    Card(modifier = modifier.padding(16.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { input: String -> name = input },
                label = { Text("Name") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp),
                        painter = painterResource(R.drawable.ic_person),
                        contentDescription = "Person"
                    )
                }
            )

            TextField(
                value = mobileNumber,
                onValueChange = { input: String -> mobileNumber = input },
                label = { Text("Mobile Number") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp),
                        painter = painterResource(R.drawable.ic_phone),
                        contentDescription = "Phone"
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )

            Button(
                onClick = {
                    val newContact = Contact(
                        id = 0,
                        name = name,
                        mobileNumber = mobileNumber
                    )
                    onAddContact(newContact)
                    name = ""
                    mobileNumber = ""
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Add Contact"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Contact")
            }
        }
    }
}