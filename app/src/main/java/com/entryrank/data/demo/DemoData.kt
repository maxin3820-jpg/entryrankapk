package com.entryrank.data.demo

import com.entryrank.data.local.entity.*

/**
 * Demo data for testing mode - No backend required!
 */
object DemoData {
    
    // Demo User
    val demoUser = UserEntity(
        id = "demo-user-001",
        email = "guest@entryrank.com",
        name = "Guest User",
        isPremium = true,
        createdAt = "2025-01-01T00:00:00Z"
    )
    
    // Demo Subjects
    val demoSubjects = listOf(
        SubjectEntity(
            id = "physics",
            name = "Physics",
            description = "General Physics for Entry Tests",
            questionCount = 5240,
            icon = "school"
        ),
        SubjectEntity(
            id = "chemistry",
            name = "Chemistry",
            description = "General Chemistry for Entry Tests",
            questionCount = 4890,
            icon = "science"
        ),
        SubjectEntity(
            id = "mathematics",
            name = "Mathematics",
            description = "Quantitative Mathematics",
            questionCount = 6120,
            icon = "calculate"
        ),
        SubjectEntity(
            id = "biology",
            name = "Biology",
            description = "General Biology for Medical Tests",
            questionCount = 3850,
            icon = "biotech"
        )
    )
    
    // Demo Books
    val demoBooks = listOf(
        BookEntity(
            id = "book-kips-physics-fy",
            title = "KIPS Physics First Year",
            author = "KIPS Publications",
            subjectId = "physics",
            description = "Complete First Year Physics",
            coverUrl = null,
            chapterCount = 11,
            isPremium = true
        ),
        BookEntity(
            id = "book-kips-chemistry",
            title = "KIPS Chemistry",
            author = "KIPS Publications",
            subjectId = "chemistry",
            description = "Complete Chemistry for Entry Tests",
            coverUrl = null,
            chapterCount = 11,
            isPremium = true
        ),
        BookEntity(
            id = "book-kips-math-fy",
            title = "KIPS Mathematics FY",
            author = "KIPS Publications",
            subjectId = "mathematics",
            description = "First Year Mathematics",
            coverUrl = null,
            chapterCount = 12,
            isPremium = false
        ),
        BookEntity(
            id = "book-steps-math",
            title = "Steps Mathematics",
            author = "Steps Publishers",
            subjectId = "mathematics",
            description = "Complete Mathematics Guide",
            coverUrl = null,
            chapterCount = 10,
            isPremium = true
        )
    )
    
    // Demo Questions - Physics
    val demoPhysicsQuestions = listOf(
        QuestionEntity(
            id = "phy-q1",
            question = "What is the SI unit of force?",
            optionA = "Joule",
            optionB = "Newton",
            optionC = "Watt",
            optionD = "Pascal",
            correctAnswer = "B",
            explanation = "Newton (N) is the SI unit of force. It is defined as kg⋅m/s².",
            subjectId = "physics",
            topicId = null,
            bookId = "book-kips-physics-fy",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "phy-q2",
            question = "Which of the following is a scalar quantity?",
            optionA = "Force",
            optionB = "Velocity",
            optionC = "Speed",
            optionD = "Acceleration",
            correctAnswer = "C",
            explanation = "Speed is a scalar quantity as it has only magnitude and no direction. Force, velocity, and acceleration are vector quantities.",
            subjectId = "physics",
            topicId = null,
            bookId = "book-kips-physics-fy",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "phy-q3",
            question = "The dimensional formula of energy is:",
            optionA = "[ML²T⁻²]",
            optionB = "[MLT⁻²]",
            optionC = "[ML²T⁻¹]",
            optionD = "[MLT⁻¹]",
            correctAnswer = "A",
            explanation = "Energy has the dimensional formula [ML²T⁻²], same as work.",
            subjectId = "physics",
            topicId = null,
            bookId = "book-kips-physics-fy",
            chapterId = null,
            universityId = null,
            difficulty = 2,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "phy-q4",
            question = "What is the acceleration due to gravity on Earth?",
            optionA = "8.9 m/s²",
            optionB = "9.8 m/s²",
            optionC = "10.8 m/s²",
            optionD = "11.8 m/s²",
            correctAnswer = "B",
            explanation = "The acceleration due to gravity on Earth is approximately 9.8 m/s².",
            subjectId = "physics",
            topicId = null,
            bookId = "book-kips-physics-fy",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "phy-q5",
            question = "First law of motion is also known as:",
            optionA = "Law of gravity",
            optionB = "Law of inertia",
            optionC = "Law of momentum",
            optionD = "Law of energy",
            correctAnswer = "B",
            explanation = "Newton's first law of motion is also called the law of inertia.",
            subjectId = "physics",
            topicId = null,
            bookId = "book-kips-physics-fy",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        )
    )
    
    // Demo Questions - Chemistry
    val demoChemistryQuestions = listOf(
        QuestionEntity(
            id = "chem-q1",
            question = "What is the atomic number of Carbon?",
            optionA = "4",
            optionB = "6",
            optionC = "8",
            optionD = "12",
            correctAnswer = "B",
            explanation = "Carbon has atomic number 6, meaning it has 6 protons in its nucleus.",
            subjectId = "chemistry",
            topicId = null,
            bookId = "book-kips-chemistry",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "chem-q2",
            question = "The pH of pure water at 25°C is:",
            optionA = "6",
            optionB = "7",
            optionC = "8",
            optionD = "9",
            correctAnswer = "B",
            explanation = "Pure water has a pH of 7 at 25°C, which is neutral.",
            subjectId = "chemistry",
            topicId = null,
            bookId = "book-kips-chemistry",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "chem-q3",
            question = "Which element has the symbol 'Fe'?",
            optionA = "Fluorine",
            optionB = "Iron",
            optionC = "Lead",
            optionD = "Silver",
            correctAnswer = "B",
            explanation = "Fe is the chemical symbol for Iron (from Latin: Ferrum).",
            subjectId = "chemistry",
            topicId = null,
            bookId = "book-kips-chemistry",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "chem-q4",
            question = "What is the molecular formula of water?",
            optionA = "HO",
            optionB = "H2O",
            optionC = "H2O2",
            optionD = "HO2",
            correctAnswer = "B",
            explanation = "Water has the molecular formula H₂O, consisting of 2 hydrogen atoms and 1 oxygen atom.",
            subjectId = "chemistry",
            topicId = null,
            bookId = "book-kips-chemistry",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "chem-q5",
            question = "Which gas is produced when metals react with acids?",
            optionA = "Oxygen",
            optionB = "Nitrogen",
            optionC = "Hydrogen",
            optionD = "Carbon dioxide",
            correctAnswer = "C",
            explanation = "When metals react with acids, hydrogen gas (H₂) is produced.",
            subjectId = "chemistry",
            topicId = null,
            bookId = "book-kips-chemistry",
            chapterId = null,
            universityId = null,
            difficulty = 2,
            createdAt = "2025-01-01T00:00:00Z"
        )
    )
    
    // Demo Questions - Mathematics
    val demoMathQuestions = listOf(
        QuestionEntity(
            id = "math-q1",
            question = "What is the value of π (pi) approximately?",
            optionA = "2.14",
            optionB = "3.14",
            optionC = "4.14",
            optionD = "5.14",
            correctAnswer = "B",
            explanation = "The value of π (pi) is approximately 3.14159..., commonly rounded to 3.14.",
            subjectId = "mathematics",
            topicId = null,
            bookId = "book-kips-math-fy",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "math-q2",
            question = "What is 15% of 200?",
            optionA = "20",
            optionB = "25",
            optionC = "30",
            optionD = "35",
            correctAnswer = "C",
            explanation = "15% of 200 = (15/100) × 200 = 30.",
            subjectId = "mathematics",
            topicId = null,
            bookId = "book-kips-math-fy",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "math-q3",
            question = "Solve: 2x + 5 = 15",
            optionA = "x = 3",
            optionB = "x = 5",
            optionC = "x = 7",
            optionD = "x = 10",
            correctAnswer = "B",
            explanation = "2x + 5 = 15\n2x = 10\nx = 5",
            subjectId = "mathematics",
            topicId = null,
            bookId = "book-kips-math-fy",
            chapterId = null,
            universityId = null,
            difficulty = 2,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "math-q4",
            question = "What is the square root of 144?",
            optionA = "10",
            optionB = "11",
            optionC = "12",
            optionD = "13",
            correctAnswer = "C",
            explanation = "√144 = 12, because 12 × 12 = 144.",
            subjectId = "mathematics",
            topicId = null,
            bookId = "book-kips-math-fy",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        ),
        QuestionEntity(
            id = "math-q5",
            question = "Sum of angles in a triangle is:",
            optionA = "90°",
            optionB = "180°",
            optionC = "270°",
            optionD = "360°",
            correctAnswer = "B",
            explanation = "The sum of all three angles in any triangle is always 180°.",
            subjectId = "mathematics",
            topicId = null,
            bookId = "book-kips-math-fy",
            chapterId = null,
            universityId = null,
            difficulty = 1,
            createdAt = "2025-01-01T00:00:00Z"
        )
    )
    
    // All demo questions combined
    val allDemoQuestions = demoPhysicsQuestions + demoChemistryQuestions + demoMathQuestions
    
    // Generate more questions to reach 50 per subject
    fun generateDemoQuestions(subjectId: String, count: Int): List<QuestionEntity> {
        val baseQuestions = when (subjectId) {
            "physics" -> demoPhysicsQuestions
            "chemistry" -> demoChemistryQuestions
            "mathematics" -> demoMathQuestions
            else -> demoPhysicsQuestions
        }
        
        val result = mutableListOf<QuestionEntity>()
        
        // Add base questions
        result.addAll(baseQuestions)
        
        // Generate additional questions by modifying base questions
        var questionNumber = baseQuestions.size + 1
        while (result.size < count) {
            val baseQuestion = baseQuestions[questionNumber % baseQuestions.size]
            result.add(
                baseQuestion.copy(
                    id = "${subjectId}-q${questionNumber}",
                    question = "${baseQuestion.question} (Question $questionNumber)"
                )
            )
            questionNumber++
        }
        
        return result.take(count)
    }
    
    // Demo Progress
    val demoProgress = listOf(
        ProgressEntity(
            id = "progress-physics",
            userId = "demo-user-001",
            subjectId = "physics",
            totalAttempted = 850,
            correctAnswers = 697,
            wrongAnswers = 153,
            accuracy = 82.0f,
            timeSpent = 45600, // 12h 40m in seconds
            lastPracticed = "2025-01-15T14:30:00Z"
        ),
        ProgressEntity(
            id = "progress-chemistry",
            userId = "demo-user-001",
            subjectId = "chemistry",
            totalAttempted = 720,
            correctAnswers = 540,
            wrongAnswers = 180,
            accuracy = 75.0f,
            timeSpent = 38400, // 10h 40m
            lastPracticed = "2025-01-14T10:20:00Z"
        ),
        ProgressEntity(
            id = "progress-math",
            userId = "demo-user-001",
            subjectId = "mathematics",
            totalAttempted = 880,
            correctAnswers = 669,
            wrongAnswers = 211,
            accuracy = 76.0f,
            timeSpent = 42000, // 11h 40m
            lastPracticed = "2025-01-13T16:45:00Z"
        )
    )
    
    // Demo Practice Sessions
    val demoPracticeSessions = listOf(
        PracticeSessionEntity(
            id = "session-1",
            userId = "demo-user-001",
            subjectId = "physics",
            topicId = null,
            bookId = "book-kips-physics-fy",
            chapterId = null,
            totalQuestions = 50,
            correctAnswers = 43,
            wrongAnswers = 5,
            skippedQuestions = 2,
            score = 86.0f,
            timeSpent = 754, // 12m 34s
            completedAt = "2025-01-15T12:30:00Z"
        ),
        PracticeSessionEntity(
            id = "session-2",
            userId = "demo-user-001",
            subjectId = "chemistry",
            topicId = null,
            bookId = "book-kips-chemistry",
            chapterId = null,
            totalQuestions = 50,
            correctAnswers = 36,
            wrongAnswers = 11,
            skippedQuestions = 3,
            score = 72.0f,
            timeSpent = 922, // 15m 22s
            completedAt = "2025-01-14T15:45:00Z"
        ),
        PracticeSessionEntity(
            id = "session-3",
            userId = "demo-user-001",
            subjectId = "mathematics",
            topicId = null,
            bookId = "book-kips-math-fy",
            chapterId = null,
            totalQuestions = 50,
            correctAnswers = 24,
            wrongAnswers = 20,
            skippedQuestions = 6,
            score = 48.0f,
            timeSpent = 1125, // 18m 45s
            completedAt = "2025-01-13T10:20:00Z"
        ),
        PracticeSessionEntity(
            id = "session-4",
            userId = "demo-user-001",
            subjectId = "physics",
            topicId = null,
            bookId = "book-kips-physics-fy",
            chapterId = null,
            totalQuestions = 50,
            correctAnswers = 47,
            wrongAnswers = 3,
            skippedQuestions = 0,
            score = 94.0f,
            timeSpent = 670, // 11m 10s
            completedAt = "2025-01-12T14:15:00Z"
        )
    )
}
