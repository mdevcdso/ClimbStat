package com.example.climbstat.presentation.ui.components.toposComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun NewTopAlertDialogComponent(
    isFlash: Boolean,
    nbAttemptInputText: String,
    commentInputText: String,
    onNbAttemptChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Valider le bloc") },
        text = {
            Column {
                if(!isFlash) {
                    OutlinedTextField(
                        value = nbAttemptInputText,
                        onValueChange = onNbAttemptChange,
                        placeholder = { Text("Nombres d'essaie") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Text("Ajoute un commentaire (optionnel)")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = commentInputText,
                    onValueChange = onCommentChange,
                    placeholder = { Text("Ton commentaire...") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )

}