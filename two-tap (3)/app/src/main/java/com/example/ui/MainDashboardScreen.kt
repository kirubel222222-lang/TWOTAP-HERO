package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.testTag
import com.example.data.SavedContent
import com.example.ui.theme.ElectricBlue
import com.example.ui.components.AtmosphericBackground
import com.example.ui.components.GlassmorphicCard
import com.example.ui.components.GlassmorphicButton
import com.example.ui.components.NeonPurple
import com.example.ui.components.NeonPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboardScreen(
    viewModel: AppViewModel,
    onNavigateToAdd: (String?) -> Unit,
    onNavigateToDetail: (SavedContent) -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToAiAssistant: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSmartLearning: (String) -> Unit
) {
    val allContent by viewModel.allContent.collectAsState()
    val continueLearning by viewModel.continueLearning.collectAsState()
    val recentContent by viewModel.recentContent.collectAsState()
    var showGuideDialog by remember { mutableStateOf(false) }
    
    if (showGuideDialog) {
        // [Existing guide dialog code]
        AlertDialog(
            onDismissRequest = { showGuideDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = "No Content Saved Yet",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "To start learning, you need to save some content first. TWO TAP is designed to save directly from other apps with zero effort!",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    val steps = listOf(
                        "1. Open YouTube, TikTok, or another app.",
                        "2. Tap the \"Share\" button on any video/post.",
                        "3. Choose \"TWO TAP\" from the sharing list.",
                        "4. Our AI instantly summarizes and saves it!"
                    )
                    steps.forEach { step ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = step,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showGuideDialog = false },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Got It!")
                }
            }
        )
    }
    
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = ElectricBlue.copy(alpha = 0.2f))
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToLibrary,
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Library") },
                    label = { Text("Library") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToAiAssistant,
                    icon = { Icon(Icons.Default.Search, contentDescription = "AI Chat") },
                    label = { Text("AI Chat") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToProfile,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { padding ->
        AtmosphericBackground(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                HeaderSection(pendingCount = allContent.size, onNavigateToProfile = onNavigateToProfile)
                Spacer(modifier = Modifier.height(24.dp))
                
                // Analytics row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GoalCard(modifier = Modifier.weight(1f), icon = Icons.Default.LocalFireDepartment, title = "3 Day", subtitle = "Streak", color = NeonPink)
                    GoalCard(modifier = Modifier.weight(1f), icon = Icons.Default.EmojiEvents, title = "Level 4", subtitle = "Learner", color = NeonPurple)
                    GoalCard(modifier = Modifier.weight(1f), icon = Icons.Default.BarChart, title = "24m", subtitle = "Today", color = ElectricBlue)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                if (continueLearning.isNotEmpty()) {
                    Text(
                        text = "Continue Learning",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(continueLearning.take(3)) { content ->
                            ContinueLearningCard(content, onNavigateToDetail)
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }

                ReadyToLearnSection(
                    allContent = allContent,
                    onNavigateToSmartLearning = onNavigateToSmartLearning,
                    onShowGuide = { showGuideDialog = true }
                )
                Spacer(modifier = Modifier.height(32.dp))
                RecentSavesSection(recentContent, onNavigateToDetail)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun GoalCard(modifier: Modifier = Modifier, icon: ImageVector, title: String, subtitle: String, color: Color) {
    GlassmorphicCard(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ContinueLearningCard(content: SavedContent, onNavigateToDetail: (SavedContent) -> Unit) {
    GlassmorphicCard(
        modifier = Modifier
            .width(260.dp)
            .clickable { onNavigateToDetail(content) },
        shape = RoundedCornerShape(24.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(content.category, fontSize = 10.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                Icon(
                    imageVector = when(content.type) {
                        "Video" -> Icons.Default.PlayArrow
                        "Audio", "Podcast" -> Icons.Default.Headphones
                        else -> Icons.Default.MenuBook
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = content.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = { content.progressPercentage / 100f },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = ElectricBlue,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("${content.progressPercentage.toInt()}% Completed", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun HeaderSection(pendingCount: Int, onNavigateToProfile: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val user = com.example.network.AuthHelper.getAuth(context)?.currentUser
    val displayName = user?.displayName?.takeIf { it.isNotBlank() }?.substringBefore(" ") ?: "Learner"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good morning, $displayName",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 16.sp
            )
            Text(
                text = "Ready to learn?",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(ElectricBlue.copy(alpha = 0.2f))
                .border(1.dp, ElectricBlue.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .clickable { onNavigateToProfile() },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = "Profile", tint = ElectricBlue)
        }
    }
}

@Composable
fun ReadyToLearnSection(
    allContent: List<SavedContent>, 
    onNavigateToSmartLearning: (String) -> Unit,
    onShowGuide: () -> Unit
) {
    var selectedMode by remember { mutableStateOf<String?>(null) }
    
    val readCount = allContent.count { it.type == "Article" || it.contentType == "Article" || it.type == "Link" }
    val watchCount = allContent.count { it.type == "Video" || it.contentType == "Video" }
    val listenCount = allContent.count { it.type == "Podcast" || it.contentType == "Podcast" || it.type == "Audio" }

    Column {
        Text(
            text = "AI Smart Sessions",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Let AI pick the best content for you",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        LearningModeCard(
            title = "Read",
            icon = Icons.Default.MenuBook,
            savedCount = readCount,
            description = "Perfect for focused learning.",
            isSelected = selectedMode == "Read",
            onClick = {
                if (allContent.isEmpty()) onShowGuide() else selectedMode = "Read"
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        LearningModeCard(
            title = "Watch",
            icon = Icons.Default.PlayArrow,
            savedCount = watchCount,
            description = "Best for visual learning.",
            isSelected = selectedMode == "Watch",
            onClick = {
                if (allContent.isEmpty()) onShowGuide() else selectedMode = "Watch"
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        LearningModeCard(
            title = "Listen",
            icon = Icons.Default.Headphones,
            savedCount = listenCount,
            description = "Learn without looking at your screen.",
            isSelected = selectedMode == "Listen",
            onClick = {
                if (allContent.isEmpty()) onShowGuide() else selectedMode = "Listen"
            }
        )

        AnimatedVisibility(visible = selectedMode != null) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                GlassmorphicButton(
                    onClick = { selectedMode?.let { onNavigateToSmartLearning(it) } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Start Smart Session", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningModeCard(
    title: String,
    icon: ImageVector,
    savedCount: Int,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(if (isSelected) 1.02f else 1f, animationSpec = tween(200), label = "")
    val alpha = if (isSelected) 1f else 0.5f

    GlassmorphicCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        shape = RoundedCornerShape(20.dp),
        borderWidth = if (isSelected) 2.dp else 1.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isSelected) NeonPurple.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (isSelected) NeonPurple else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$savedCount pending",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) NeonPurple else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentSavesSection(contentList: List<SavedContent>, onNavigateToDetail: (SavedContent) -> Unit) {
    Column {
        Text(
            text = "Recent Activity",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        if (contentList.isEmpty()) {
            GlassmorphicCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No recent activity found. Save content to build your knowledge base.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            contentList.take(5).forEach { content ->
                GlassmorphicCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    onClick = { onNavigateToDetail(content) }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = content.category,
                            fontSize = 12.sp,
                            color = ElectricBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${content.estimatedTime} min read",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = content.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = content.url,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
