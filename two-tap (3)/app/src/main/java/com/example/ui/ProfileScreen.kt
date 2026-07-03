package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.AuthHelper
import com.example.ui.components.AtmosphericBackground
import com.example.ui.components.GlassmorphicCard
import com.example.ui.components.GlassmorphicButton
import com.example.ui.components.ElectricBlue
import com.example.ui.components.NeonPink
import com.example.ui.components.NeonPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: AppViewModel,
    onNavigateHome: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToAiAssistant: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit
) {
    val allContent by viewModel.allContent.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateHome,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
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
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = ElectricBlue.copy(alpha = 0.2f))
                )
            }
        }
    ) { padding ->
        AtmosphericBackground(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileHeaderSection(onNavigateToSettings)
                Spacer(modifier = Modifier.height(24.dp))
                LearningOverviewCard(allContent = allContent)
                Spacer(modifier = Modifier.height(24.dp))
                PremiumStatusCard()
                Spacer(modifier = Modifier.height(24.dp))
                KnowledgeScoreSection(allContent = allContent)
                Spacer(modifier = Modifier.height(24.dp))
                AchievementsSection(allContent = allContent)
                Spacer(modifier = Modifier.height(24.dp))
                InterestsSection(allContent = allContent)
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun ProfileHeaderSection(onNavigateToSettings: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val user = AuthHelper.getAuth(context)?.currentUser
    
    val displayName = user?.displayName?.takeIf { it.isNotBlank() } ?: "Learner"
    val email = user?.email ?: "No email linked"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), 
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onNavigateToSettings) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onBackground)
            }
        }
        
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(ElectricBlue.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile Picture",
                tint = ElectricBlue,
                modifier = Modifier.size(60.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(displayName, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Text(email, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            GlassmorphicButton(
                onClick = { /* TODO Edit Profile */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp), tint = ElectricBlue)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit Profile", color = ElectricBlue, fontWeight = FontWeight.Bold)
            }
            GlassmorphicButton(
                onClick = { /* TODO Share Profile */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp), tint = NeonPink)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share", color = NeonPink, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LearningOverviewCard(allContent: List<com.example.data.SavedContent>) {
    val savedCount = allContent.size
    val completedCount = allContent.count { it.status == "Completed" }
    val totalMinutes = allContent.filter { it.status == "Completed" }.sumOf { it.estimatedTime }
    val hoursStr = if (totalMinutes < 60) "$totalMinutes Min" else String.format("%.1f Hr", totalMinutes / 60.0)
    
    // Calculate streaks based on distinct saved days
    val distinctDays = allContent.map {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        sdf.format(java.util.Date(it.timestamp))
    }.distinct().size
    val streakStr = "$distinctDays Days"
    val longestStreakStr = "${distinctDays + if (distinctDays > 0) 1 else 0} Days"

    GlassmorphicCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Learning Overview", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatItem("Saved", savedCount.toString(), ElectricBlue)
                StatItem("Completed", completedCount.toString(), SuccessGreen)
                StatItem("Hours", hoursStr, NeonPurple)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatItem("Current Streak", streakStr, NeonPink)
                StatItem("Longest Streak", longestStreakStr, NeonPink)
            }
        }
    }
}

val SuccessGreen = Color(0xFF10B981)

@Composable
fun StatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = color)
        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun PremiumStatusCard() {
    GlassmorphicCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.WorkspacePremium, contentDescription = "Premium", tint = NeonPurple)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("TwoTap Pro", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Unlimited AI & Offline Sync", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(
                onClick = { /* TODO Launch Billing */ },
                colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Upgrade")
            }
        }
    }
}

@Composable
fun KnowledgeScoreSection(allContent: List<com.example.data.SavedContent>) {
    val savedCount = allContent.size
    val completedCount = allContent.count { it.status == "Completed" }
    val xp = (savedCount * 20) + (completedCount * 100)
    
    val level = (xp / 500) + 1
    val nextLevelXp = level * 500
    val currentLevelBaseXp = (level - 1) * 500
    val progress = if (nextLevelXp == currentLevelBaseXp) 0f else {
        ((xp - currentLevelBaseXp).toFloat() / (nextLevelXp - currentLevelBaseXp).toFloat()).coerceIn(0f, 1f)
    }
    
    val levelTitle = when {
        level < 3 -> "Novice Learner"
        level < 6 -> "Knowledge Seeker"
        level < 10 -> "Intellectual Explorer"
        else -> "Master Scholar"
    }

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Knowledge Score", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text("%,d XP".format(xp), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ElectricBlue)
        }
        Spacer(modifier = Modifier.height(12.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp)),
            color = ElectricBlue,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Level $level", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
            Text(levelTitle, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun AchievementsSection(allContent: List<com.example.data.SavedContent>) {
    val savedCount = allContent.size
    val completedCount = allContent.count { it.status == "Completed" }

    val achievements = listOf(
        Triple("First Save", "Save 1 item", savedCount >= 1),
        Triple("Explorer", "Save 10 items", savedCount >= 10),
        Triple("Completer", "Complete 1 item", completedCount >= 1),
        Triple("Finisher", "Complete 5 items", completedCount >= 5),
        Triple("Polymath", "3+ categories", allContent.map { it.category }.distinct().size >= 3)
    )

    Column {
        Text(
            "Achievements",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(achievements) { (title, desc, isUnlocked) ->
                GlassmorphicCard(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.width(130.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp).fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(if (isUnlocked) NeonPink.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = title,
                                tint = if (isUnlocked) NeonPink else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = desc,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InterestsSection(allContent: List<com.example.data.SavedContent>) {
    val savedCategories = allContent.map { it.category }.filter { it.isNotBlank() && it != "Uncategorized" }.distinct()
    val interests = if (savedCategories.isEmpty()) listOf("AI", "Business", "Programming", "Startups", "Design") else savedCategories

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Interests", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            TextButton(onClick = { /* TODO */ }) { Text("Edit", color = ElectricBlue) }
        }
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            interests.forEach { interest ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(interest, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}