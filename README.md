# CC-RM
📘 Campus Course & Records Manager (CCRM)
The Campus Course & Records Manager (CCRM) is a console-based Java SE application for managing core academic data — students, courses, enrollments, grades, and transcripts — all within a single system.

This project demonstrates not only the functional requirements of such a system but also key Java programming concepts, making it both a practical management tool and an educational showcase.

🎯 Objectives
Build a menu-driven CLI application that allows easy interaction.

Manage students, courses, enrollments, and grading.

Implement file operations (CSV import/export, recursive backup with NIO.2).

Showcase Java SE OOP & advanced features:

Encapsulation, inheritance, abstraction, polymorphism

Interfaces, nested classes, lambdas, anonymous classes

Design patterns (Singleton, Builder)

Exception handling with custom exceptions

Streams API and Date/Time API

Recursion

⚡ Features
1. Student Management
Add, update, deactivate students
Each student has ID, registration number, name, email, status, enrolled courses
Print student profile and transcript
2. Course Management
Add, update, deactivate courses
Each course has code, title, credits, instructor, semester, department
Search/filter courses using Streams
3. Enrollment & Grading
Enroll/unenroll students with credit limits
Record grades (S, A, B, …, F)
Compute GPA and generate transcripts
4. File Operations
Import/export students and courses from CSV
Export enrollment data
Backup data into timestamped folders
Recursive utility to calculate backup folder size
5. CLI Workflow
Menu-driven operations using switch
Demonstrates loops (for, while, do-while), break, continue

🛠️ Technical Highlights
Design Patterns: Singleton (AppConfig), Builder (Course.Builder)
OOP: Abstract class Person, subclasses Student, Instructor
Immutability: Name class is immutable
Enums: Semester, Grade with constructors and grade points
Exceptions: Custom exceptions DuplicateEnrollmentException, MaxCreditLimitExceededException
Java SE APIs: NIO.2 for file handling, Streams API for filtering/searching, Date/Time API for timestamps
Recursion: Used in backup folder size calculation

📂 Package Structure
