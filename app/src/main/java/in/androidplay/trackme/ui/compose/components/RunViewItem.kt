package `in`.androidplay.trackme.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.room.Run

@Composable
fun RunViewItem(run: List<Run>?, position: Int) {
    /* TODO: use coil image loading
    val painter =
        rememberAsyncImagePainter("https://lh3.googleusercontent.com/64GWPJbpSJKB2hejLK02GLHjflv2B8cCr7SJUQI7cHXO0Qakc28U-ZRw7IRL3WadD8Stugb1HB4GgpqEkRydsEaR9AC4SqrTeRlCDlo=w1064-v0")*/
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.bg_placeholder_default),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier.padding(16.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "ML Kit Subject Segmentation...",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                    Text(
                        text = "${run?.get(position)}}"
                    )
                }
            }
        }

    }
}