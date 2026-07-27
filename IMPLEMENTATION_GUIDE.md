# EntryRank Android - Complete Implementation Guide

This guide provides step-by-step instructions for completing the EntryRank Android app.

## ✅ What's Already Done (Foundation - 30%)

1. **Database Layer** - Complete Room setup with 10 entities and DAOs
2. **Project Structure** - Clean Architecture folders
3. **Build Configuration** - Gradle with Kotlin DSL
4. **CI/CD** - GitHub Actions for automated APK building
5. **Dependencies** - All libraries configured

---

## 🚀 Implementation Roadmap

### Phase 1: Dependency Injection (Day 1)

**File**: `app/src/main/java/com/entryrank/di/DatabaseModule.kt`
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EntryRankDatabase {
        return Room.databaseBuilder(
            context,
            EntryRankDatabase::class.java,
            EntryRankDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }
    
    @Provides
    fun provideUserDao(database: EntryRankDatabase) = database.userDao()
    
    @Provides
    fun provideQuestionDao(database: EntryRankDatabase) = database.questionDao()
    
    // ... provide all other DAOs
}
```

**File**: `app/src/main/java/com/entryrank/di/NetworkModule.kt`
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) 
                    HttpLoggingInterceptor.Level.BODY 
                else 
                    HttpLoggingInterceptor.Level.NONE
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}
```

---

### Phase 2: API Layer (Days 2-3)

**File**: `app/src/main/java/com/entryrank/data/remote/api/AuthApi.kt`
```kotlin
interface AuthApi {
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<AuthResponse>
    
    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): ApiResponse<AuthResponse>
    
    @POST("auth/logout")
    suspend fun logout(): ApiResponse<Unit>
    
    @GET("auth/me")
    suspend fun getCurrentUser(): ApiResponse<UserDto>
}
```

**File**: `app/src/main/java/com/entryrank/data/remote/dto/AuthResponse.kt`
```kotlin
data class AuthResponse(
    val token: String,
    val user: UserDto
)

data class UserDto(
    val id: String,
    val email: String,
    val name: String,
    val phone: String?,
    val university: String,
    val group: String,
    val targetUniversity: String,
    val isPremium: Boolean,
    val subscriptionType: String?,
    val subscriptionExpiresAt: Long?
)
```

Create similar API interfaces for:
- `QuestionApi.kt` - GET questions, pagination
- `BookApi.kt` - GET books, chapters
- `SubscriptionApi.kt` - GET/POST subscription
- `ProgressApi.kt` - POST progress sync

---

### Phase 3: Repository Layer (Days 4-5)

**File**: `app/src/main/java/com/entryrank/data/repository/AuthRepositoryImpl.kt`
```kotlin
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val userDao: UserDao,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {
    
    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = authApi.login(LoginRequest(email, password))
            // Save token to DataStore
            saveToken(response.data.token)
            // Save user to Room
            userDao.insertUser(response.data.user.toEntity())
            Result.success(response.data.user.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }
}
```

Implement repositories for:
- `QuestionRepository` - Offline-first question data
- `BookRepository` - Books and chapters
- `UserRepository` - User profile
- `PracticeRepository` - Practice sessions

---

### Phase 4: Domain Layer (Days 6-7)

**File**: `app/src/main/java/com/entryrank/domain/usecase/LoginUseCase.kt`
```kotlin
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        // Validate input
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(InvalidCredentialsException())
        }
        
        return authRepository.login(email, password)
    }
}
```

Create use cases for all major operations:
- `GetQuestionsUseCase`
- `SubmitPracticeSessionUseCase`
- `DownloadBookUseCase`
- `GetUserProgressUseCase`

---

### Phase 5: Theme & UI Foundation (Day 8)

**File**: `app/src/main/java/com/entryrank/presentation/theme/Color.kt`
```kotlin
val Green600 = Color(0xFF16a34a)
val Green700 = Color(0xFF15803d)
val Green500 = Color(0xFF22c55e)

val md_theme_light_primary = Green600
val md_theme_light_onPrimary = Color.White
// ... define all Material 3 colors
```

**File**: `app/src/main/java/com/entryrank/presentation/theme/Theme.kt`
```kotlin
@Composable
fun EntryRankTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

---

### Phase 6: ViewModels (Days 9-11)

**File**: `app/src/main/java/com/entryrank/presentation/auth/LoginViewModel.kt`
```kotlin
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            
            loginUseCase(email, password).fold(
                onSuccess = { user ->
                    _uiState.value = LoginUiState.Success(user)
                },
                onFailure = { error ->
                    _uiState.value = LoginUiState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: User) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
```

Create ViewModels for:
- `SignupViewModel`
- `DashboardViewModel`
- `PracticeViewModel`
- `BooksViewModel`
- `ProfileViewModel`

---

### Phase 7: Compose Screens (Days 12-18)

**File**: `app/src/main/java/com/entryrank/presentation/auth/LoginScreen.kt`
```kotlin
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            onLoginSuccess()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "EntryRank",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is LoginUiState.Loading
        ) {
            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Login")
            }
        }
        
        if (uiState is LoginUiState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (uiState as LoginUiState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
```

Implement all screens following same pattern.

---

### Phase 8: Navigation (Day 19)

**File**: `app/src/main/java/com/entryrank/presentation/navigation/NavGraph.kt`
```kotlin
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToSubject = { subject ->
                    navController.navigate(Screen.Practice.createRoute(subject))
                }
            )
        }
        
        // Add all other screens
    }
}
```

---

### Phase 9: Testing (Days 20-22)

**Unit Test Example**:
```kotlin
@ExperimentalCoroutinesApi
class LoginViewModelTest {
    
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    private lateinit var viewModel: LoginViewModel
    private val loginUseCase: LoginUseCase = mockk()
    
    @Before
    fun setup() {
        viewModel = LoginViewModel(loginUseCase)
    }
    
    @Test
    fun `login success updates state correctly`() = runTest {
        // Given
        val user = User(id = "1", name = "Test")
        coEvery { loginUseCase(any(), any()) } returns Result.success(user)
        
        // When
        viewModel.login("test@test.com", "password")
        
        // Then
        assert(viewModel.uiState.value is LoginUiState.Success)
    }
}
```

---

## 📱 Testing the App

### Run on Emulator:
```bash
./gradlew installDebug
adb logcat -s "EntryRank"
```

### Build Release APK:
```bash
./gradlew assembleRelease
```

### Run Tests:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

---

## 🔧 Common Issues & Solutions

### 1. API Connection Issues
- Check `BuildConfig.API_BASE_URL`
- Verify internet permission in manifest
- Test API endpoints with Postman first

### 2. Database Migration
- Use `fallbackToDestructiveMigration()` in development
- Create proper migrations for production

### 3. Memory Issues with Large Datasets
- Always use Paging 3 for lists
- Never load all questions at once
- Use `lazyColumn` in Compose

---

## 📦 Building APK with GitHub Actions

1. Generate keystore:
```bash
keytool -genkey -v -keystore keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias entryrank
```

2. Convert to base64:
```bash
base64 -w 0 keystore.jks > keystore.txt
```

3. Add to GitHub Secrets:
- `KEYSTORE_BASE64` - Contents of keystore.txt
- `KEYSTORE_PASSWORD` - Your password
- `KEY_ALIAS` - Your alias
- `KEY_PASSWORD` - Your key password

4. Push to main branch - APK builds automatically!

---

## 🎯 Priority Features for MVP

1. **Authentication** (Login/Signup)
2. **Dashboard** (Subject selection)
3. **Practice Questions** (MCQ with timer)
4. **Results** (Score and analytics)
5. **Profile** (Basic user info)

**Skip for MVP**:
- Books feature
- Offline downloads
- Premium subscriptions
- Advanced analytics

---

## ⏱️ Time Estimates

| Component | Junior Dev | Senior Dev |
|-----------|------------|------------|
| DI Setup | 2 days | 0.5 day |
| API Layer | 5 days | 2 days |
| Repositories | 5 days | 2 days |
| ViewModels | 7 days | 3 days |
| UI Screens | 14 days | 7 days |
| Testing | 7 days | 3 days |
| **Total** | **40 days** | **17.5 days** |

---

## 📞 Need Help?

Check these files for reference:
- `PROJECT_STATUS.md` - Overall status
- Entity files - Database schema
- DAO files - Query examples
- `build.gradle.kts` - Dependencies

**Good luck building EntryRank! 🚀**
