package com.chandana.shaalevikas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chandana.shaalevikas.data.MockDatabase
import com.chandana.shaalevikas.data.MicroNeed
import com.chandana.shaalevikas.ui.theme.ShaaleVikasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShaaleVikasTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainNavigationLayer()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationLayer() {
    var currentScreen by remember { mutableStateOf("RoleSelection") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shaale-Vikas Bridge", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B5E20)),
                navigationIcon = {
                    if (currentScreen != "RoleSelection") {
                        IconButton(onClick = { currentScreen = "RoleSelection" }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                "RoleSelection" -> RoleSelectionScreen(
                    onAdminClick = { currentScreen = "Admin" },
                    onAlumniClick = { currentScreen = "Alumni" }
                )
                "Admin" -> HeadmasterAdminScreen()
                "Alumni" -> AlumniTabController()
            }
        }
    }
}

@Composable
fun RoleSelectionScreen(onAdminClick: () -> Unit, onAlumniClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Shaale-Vikas", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20), modifier = Modifier.padding(bottom = 8.dp))
        Text("Connecting Rural Schools with Alumni Networks", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 32.dp))

        Button(onClick = onAdminClick, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)), shape = RoundedCornerShape(12.dp)) {
            Text("Login as School Headmaster (Admin)", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAlumniClick, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)), shape = RoundedCornerShape(12.dp)) {
            Text("Enter as Alumni Network Community", fontSize = 16.sp)
        }
    }
}

@Composable
fun HeadmasterAdminScreen() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("High") }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Post a School Micro-Need", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Need Title") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Detailed Description") }, modifier = Modifier.fillMaxWidth(), maxLines = 3)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = cost, onValueChange = { cost = it }, label = { Text("Estimated Cost (INR)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Text("Select Priority Level:", fontWeight = FontWeight.SemiBold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("High", "Medium", "Low").forEach { level ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = (priority == level), onClick = { priority = level })
                    Text(level)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (title.isNotBlank() && cost.isNotBlank()) {
                    MockDatabase.activeNeeds.add(MicroNeed(id = System.currentTimeMillis().toString(), title = title, description = description, category = "Repair", costEstimate = cost.toDoubleOrNull() ?: 0.0, priority = priority))
                    Toast.makeText(context, "Micro-Need Posted successfully!", Toast.LENGTH_SHORT).show()
                    title = ""; description = ""; cost = ""
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Publish to Alumni Dashboard")
        }
    }
}

// Controls shifting between the 3 sub-screens for Alumni
@Composable
fun AlumniTabController() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("Dashboard") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Star, contentDescription = null) },
                    label = { Text("Impact Cards") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Hall of Fame") }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> AlumniDashboardScreen()
                1 -> ImpactPhotosScreen()
                2 -> DonorHallOfFameScreen()
            }
        }
    }
}

@Composable
fun AlumniDashboardScreen() {
    val needsList = remember { MockDatabase.activeNeeds }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("✨ Gemini AI Top Priority Recommendation", fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                Spacer(modifier = Modifier.height(4.dp))
                Text("Based on learning impact and current structural vulnerability, 'Repairing Leaking Roof' requires immediate intervention ahead of the coming monsoon seasons.", fontSize = 13.sp, color = Color.DarkGray)
            }
        }
        Text("Active Infrastructure Requirements", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(needsList) { need -> NeedDisplayCard(need = need) }
        }
    }
}

@Composable
fun NeedDisplayCard(need: MicroNeed) {
    var localPledgedByMe by remember { mutableStateOf(need.pledgedAmount) }
    val progressFraction = if (need.costEstimate > 0) (localPledgedByMe / need.costEstimate).toFloat().coerceIn(0f, 1f) else 0f

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(need.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.weight(1f))
                Surface(color = if (need.priority == "High") Color(0xFFFFEBEE) else Color(0xFFFFF3E0), shape = RoundedCornerShape(6.dp)) {
                    Text(need.priority, color = if (need.priority == "High") Color.Red else Color(0xFFE65100), fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(need.description, fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Target: ₹${need.costEstimate.toInt()}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text("Funded: ₹${localPledgedByMe.toInt()}", fontSize = 14.sp, color = Color(0xFF2E7D32))
            }
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(progress = progressFraction, modifier = Modifier.fillMaxWidth().height(8.dp), color = Color(0xFF2E7D32), trackColor = Color.LightGray)
            Spacer(modifier = Modifier.height(12.dp))
            if (localPledgedByMe >= need.costEstimate) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.End)) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("100% Fully Met", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                }
            } else {
                Button(onClick = { localPledgedByMe += 500.0; need.pledgedAmount = localPledgedByMe }, modifier = Modifier.align(Alignment.End), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))) {
                    Text("Pledge ₹500")
                }
            }
        }
    }
}

// Matches Report Screen 5: Before/After side-by-side display with AI narrative
@Composable
fun ImpactPhotosScreen() {
    val completedList = MockDatabase.completedStories
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Transparency Cornerstone: Impact Gallery", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20), modifier = Modifier.padding(bottom = 12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(completedList) { story ->
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(story.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
                        Row(modifier = Modifier.fillMaxWidth().height(120.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.weight(1f).background(Color.DarkGray)) {
                                AsyncImage(model = story.beforeUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                                Text("BEFORE", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomStart).padding(4.dp).background(Color.Black.copy(alpha = 0.6f)).padding(2.dp))
                            }
                            Box(modifier = Modifier.weight(1f).background(Color.DarkGray)) {
                                AsyncImage(model = story.afterUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                                Text("AFTER PROOF", color = Color.Green, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomStart).padding(4.dp).background(Color.Black.copy(alpha = 0.6f)).padding(2.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("🤖 AI Verification Narrative:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                        Text(story.aiNarrative, fontSize = 12.sp, color = Color.DarkGray)
                    }
                }
            }
        }
    }
}

// Matches Report Screen 6: Public community acknowledgements
@Composable
fun DonorHallOfFameScreen() {
    val donors = MockDatabase.donorsList
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xE8E0F2F1))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("School Support Summary Banner", fontWeight = FontWeight.Bold, color = Color(0xFF004D40))
                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column { Text("Total Raised", fontSize = 12.sp); Text("₹23,500", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1B5E20)) }
                    Column { Text("Needs Met", fontSize = 12.sp); Text("3 Projects", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1B5E20)) }
                }
            }
        }
        Text("Donor Hall of Fame", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(donors) { donor ->
                ListItem(
                    headlineContent = { Text(donor.name, fontWeight = FontWeight.SemiBold) },
                    supportingContent = { Text("Supported: ${donor.supportNeed}") },
                    trailingContent = { Text("₹${donor.amount}", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold) }
                )
                Divider()
            }
        }
    }
}