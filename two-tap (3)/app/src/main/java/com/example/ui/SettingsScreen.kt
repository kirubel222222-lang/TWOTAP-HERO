package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AtmosphericBackground
import com.example.ui.components.GlassmorphicCard
import com.example.ui.components.ElectricBlue
import com.example.ui.components.NeonPink
import com.example.ui.components.NeonPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        AtmosphericBackground(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                
                SettingsSectionTitle("Account")
                GlassmorphicCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), shape = RoundedCornerShape(20.dp)) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        SettingsItem(Icons.Default.Person, "Account Info", onClick = { /* TODO */ })
                        SettingsItem(Icons.Default.WorkspacePremium, "Subscription & Billing", iconTint = NeonPurple, trailingContent = {
                            Box(modifier = Modifier.background(NeonPurple.copy(alpha = 0.2f), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                                Text("PRO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NeonPurple)
                            }
                        }, onClick = { /* TODO */ })
                        SettingsItem(Icons.Default.CloudSync, "Cloud Sync Status", trailingContent = {
                            Text("Synced Just Now", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }, onClick = { /* TODO */ })
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                SettingsSectionTitle("Preferences")
                GlassmorphicCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), shape = RoundedCornerShape(20.dp)) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        var isDarkTheme by remember { mutableStateOf(true) }
                        SettingsItem(
                            icon = Icons.Default.DarkMode, 
                            title = "Dark Theme", 
                            trailingContent = {
                                Switch(
                                    checked = isDarkTheme,
                                    onCheckedChange = { isDarkTheme = it },
                                    colors = SwitchDefaults.colors(checkedThumbColor = ElectricBlue, checkedTrackColor = ElectricBlue.copy(alpha = 0.5f))
                                )
                            },
                            onClick = { isDarkTheme = !isDarkTheme }
                        )
                        SettingsItem(Icons.Default.Language, "Language", trailingContent = {
                            Text("English", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }, onClick = { /* TODO */ })
                        SettingsItem(Icons.Default.Notifications, "Smart Notifications", onClick = { /* TODO */ })
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                SettingsSectionTitle("Data & Privacy")
                GlassmorphicCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), shape = RoundedCornerShape(20.dp)) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        SettingsItem(Icons.Default.Backup, "Backup to Google Drive", onClick = { /* TODO */ })
                        SettingsItem(Icons.Default.SettingsBackupRestore, "Restore Data", onClick = { /* TODO */ })
                        SettingsItem(Icons.Default.Security, "Privacy Settings", onClick = { /* TODO */ })
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    TextButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = NeonPink)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Log Out", color = NeonPink, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = { /* TODO Delete Account */ }) {
                        Text("Delete Account", color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("TwoTap v1.0.0", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
    )
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector, 
    title: String, 
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
        if (trailingContent != null) {
            trailingContent()
        } else {
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
        }
    }
}
