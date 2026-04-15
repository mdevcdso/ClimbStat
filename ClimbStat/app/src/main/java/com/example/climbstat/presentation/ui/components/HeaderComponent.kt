package com.example.climbstat.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderComponent(title: String, iconScreen: Int, iconButton: Int?, modifier: Modifier, onclick: () -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Icon(
                painter = painterResource(id = iconScreen),
                contentDescription = "Icon Screen",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            modifier = Modifier
                .weight(5f)
                .padding(8.dp),
            textAlign = TextAlign.Center,
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
             .weight(1f)
        ){
        }
//        IconButton(
//            modifier = Modifier
//                .weight(1f)
//                .padding(8.dp),
//            onClick = {
//                onclick()
//            }) {
//            iconButton?.let { it ->
//                Icon(
//                    painter = painterResource(id = it),
//                    contentDescription = "Icon Button",
//                    modifier = Modifier.size(50.dp),
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//        }
    }
}