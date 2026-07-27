package com.entryrank.presentation.practice

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.entryrank.data.local.entity.PracticeSessionEntity
import com.entryrank.data.repository.PracticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    onBack: () -> Unit,
    onPracticeAgain: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Practice Results") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is ResultUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is ResultUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Score card
                    item {
                        ScoreCard(
                            score = state.session.score,
                            correctAnswers = state.session.correctAnswers,
                            totalQuestions = state.session.totalQuestions,
                            timeSpent = state.session.timeSpent
                        )
                    }
                    
                    // Stats card
                    item {
                        StatsCard(
                            correctAnswers = state.session.correctAnswers,
                            wrongAnswers = state.session.wrongAnswers,
                            skippedQuestions = state.session.skippedQuestions,
                            totalQuestions = state.session.totalQuestions
                        )
                    }
                    
                    // Action buttons
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = onBack,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Home, "Home", modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Home")
                            }
                            
                            Button(
                                onClick = onPracticeAgain,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Refresh, "Practice Again", modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Practice Again")
                            }
                        }
                    }
                    
                    // Performance message
                    item {
                        PerformanceCard(accuracy = state.session.score)
                    }
                }
            }
            
            is ResultUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBack) {
                            Text("Go Back")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreCard(
    score: Float,
    correctAnswers: Int,
    totalQuestions: Int,
    timeSpent: Int
) {
    val animatedScore by animateFloatAsState(
        targetValue = score,
        label = "score"
    )
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                score >= 80 -> Color(0xFF10B981)
                score >= 60 -> Color(0xFFF59E0B)
                else -> Color(0xFFEF4444)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = when {
                    score >= 80 -> Icons.Default.EmojiEvents
                    score >= 60 -> Icons.Default.Star
                    else -> Icons.Default.TrendingDown
                },
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "${animatedScore.toInt()}%",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Text(
                text = "Your Score",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "$correctAnswers out of $totalQuestions correct",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            
            Text(
                text = "Time spent: ${formatTime(timeSpent)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
fun StatsCard(
    correctAnswers: Int,
    wrongAnswers: Int,
    skippedQuestions: Int,
    totalQuestions: Int
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Breakdown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            StatRow(
                icon = Icons.Default.CheckCircle,
                label = "Correct",
                value = correctAnswers.toString(),
                color = Color(0xFF10B981)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            StatRow(
                icon = Icons.Default.Cancel,
                label = "Wrong",
                value = wrongAnswers.toString(),
                color = Color(0xFFEF4444)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            StatRow(
                icon = Icons.Default.RemoveCircle,
                label = "Skipped",
                value = skippedQuestions.toString(),
                color = Color(0xFF6B7280)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            HorizontalDivider()
            
            Spacer(modifier = Modifier.height(12.dp))
            
            StatRow(
                icon = Icons.Default.QuestionMark,
                label = "Total Questions",
                value = totalQuestions.toString(),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun StatRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun PerformanceCard(accuracy: Float) {
    val (message, color) = when {
        accuracy >= 90 -> "Excellent! Outstanding performance!" to Color(0xFF10B981)
        accuracy >= 80 -> "Great job! Keep it up!" to Color(0xFF22C55E)
        accuracy >= 70 -> "Good work! You're doing well." to Color(0xFF3B82F6)
        accuracy >= 60 -> "Fair performance. Keep practicing!" to Color(0xFFF59E0B)
        accuracy >= 50 -> "Needs improvement. Don't give up!" to Color(0xFFEF4444)
        else -> "Keep practicing! You'll get better." to Color(0xFFDC2626)
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Info,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return if (minutes > 0) {
        "${minutes}m ${remainingSeconds}s"
    } else {
        "${remainingSeconds}s"
    }
}

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val practiceRepository: PracticeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val sessionId: String? = savedStateHandle["sessionId"]
    
    private val _uiState = MutableStateFlow<ResultUiState>(ResultUiState.Loading)
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()
    
    init {
        loadResults()
    }
    
    private fun loadResults() {
        androidx.lifecycle.viewModelScope.launch {
            if (sessionId == null) {
                _uiState.value = ResultUiState.Error("Session not found")
                return@launch
            }
            
            practiceRepository.getPracticeSessionById(sessionId).fold(
                onSuccess = { session ->
                    _uiState.value = ResultUiState.Success(session)
                },
                onFailure = { error ->
                    _uiState.value = ResultUiState.Error(
                        error.message ?: "Failed to load results"
                    )
                }
            )
        }
    }
}

sealed class ResultUiState {
    object Loading : ResultUiState()
    data class Success(val session: PracticeSessionEntity) : ResultUiState()
    data class Error(val message: String) : ResultUiState()
}
