package com.match_intel.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.match_intel.android.R
import kotlinx.coroutines.launch

@Composable
fun MainScaffold(onLogout: () -> Unit) {
    val pages = listOf(
        Pair("Clubs", Icons.Outlined.Place),
        Pair("Home", Icons.Outlined.Home),
        Pair("Tournaments", painterResource(id = R.drawable.ic_tournaments))
    )
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { pages.size }
    )
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                        .height(56.dp),
                    shape = CircleShape,
                    placeholder = { Text(text = "Search...") }
                )
                IconButton(
                    onClick = { /* TODO: Open profile/settings */ },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "My Profile",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar {
                pages.forEachIndexed { index, (label, icon) ->
                    NavigationBarItem(
                        icon = {
                            when (icon) {
                                is ImageVector -> Icon(
                                    imageVector = icon,
                                    contentDescription = label,
                                    modifier = Modifier.size(24.dp)
                                )
                                is Painter -> Icon(
                                    painter = icon,
                                    contentDescription = label,
                                    modifier = Modifier.size(24.dp)
                                )
                                else -> {}
                            }
                        },
                        label = { Text(label) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = { /* TODO: Add action */ },
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        HorizontalPager(
            pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = pages[page].first, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}
