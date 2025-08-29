package com.match_intel.android.ui.components

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.match_intel.android.data.dto.UserDto
import com.match_intel.android.viewmodel.HomeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LikesCommentsDialog
            (matchId: String, onDismiss: () -> Unit,
             viewModel: HomeViewModel
) {
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val context = LocalContext.current

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Likes", "Comments")
    var likesList by remember { mutableStateOf<List<UserDto>>(emptyList()) }
    val commentsList by viewModel.comments.collectAsState()
    var newComment by remember { mutableStateOf("") }

    LaunchedEffect(matchId) {
        likesList = viewModel.getLikesList(matchId)
        viewModel.loadComments(matchId)
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .heightIn(max = screenHeightDp - 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .windowInsetsPadding(WindowInsets.ime.only(WindowInsetsSides.Bottom))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { onDismiss() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }
                }

                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                when (selectedTab) {
                    0 -> {
                        // Likes list
                        if (likesList.isEmpty()) {
                            Text("No likes yet")
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {

                                items(likesList) { user ->
                                    Text(
                                        text = "${user.firstName} ${user.lastName} (@${user.username})",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    1 -> {
                        // Comments list
                        if (commentsList.isEmpty()) {
                            Text("No comments yet")
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentPadding = PaddingValues(bottom = 8.dp)
                            ) {
                                items(commentsList) { comment ->
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = "${comment.user.firstName} ${comment.user.lastName} (@${comment.user.username})",
                                            style = MaterialTheme.typography.titleSmall,
                                            modifier = Modifier.padding(start = 16.dp)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = comment.comment,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }

                            // New comment input
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .imePadding()
                                    .imeNestedScroll(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextField(
                                    value = newComment,
                                    onValueChange = { newComment = it },
                                    placeholder = { Text("Write a comment...") },
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        if (newComment.isNotBlank()) {
                                            viewModel.commentMatch(matchId, newComment)
                                            newComment = ""
                                            onDismiss()
                                            Toast.makeText(context, "Sent!", Toast.LENGTH_SHORT)
                                                .show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Comment cannot be empty",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.Send,
                                        contentDescription = "Send"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
