# Vidhyalay
Overview
Vidhyalay is an Android application designed to provide educational resources and study materials for students and teachers. The app offers two modes of access: one for students and one for teachers. Each user type has distinct permissions, with teachers able to upload lecture videos and create test series, while students can only view these resources.

The application includes a registration and login system where users must verify their email address using a randomly generated token before they can access the app. The project uses Java for Android development and is structured to offer a secure and user-friendly experience.

Features
1. Two-User Mode
Teacher Mode:
Teachers can create test series and upload their lecture videos.
They have full control over the resources they upload, including edit and delete permissions.
Student Mode:
Students can only view the test series and videos uploaded by the teachers.
They do not have write permissions, ensuring that educational resources remain unchanged and secure.
2. Registration and Email Verification
Users register using their email address.
A verification token is generated using Java's random token generator and sent to the user's email.
The user must enter this token to verify their email and activate their account.
3. Login System
After successful email verification, users can log in as either a teacher or student.
Each login mode presents a different interface and functionality based on the user's role.
4. Educational Resources by Domain
The application provides study materials for various domains, making it easier for students preparing for different exams to access the right resources.

GATE:
Subdomains include Mechanical Engineering, Computer Science Engineering (CSE), Civil Engineering, and more.
UPSC:
Study materials and resources for Civil Services aspirants.
CAT:
Resources for students preparing for MBA entrance exams like CAT.
This categorization of study materials ensures that students can find relevant content specific to their domain of study, further streamlining the learning experience.

5. Firebase Integration
The application uses Firebase as the backend database.
Firebase is utilized for user authentication, storing user profiles, managing resources (like test series and lecture videos), and maintaining real-time updates.
The cloud-based architecture allows for seamless scalability and synchronization across devices.
6. Educational Resources
The app provides a platform for teachers to upload and manage educational content, such as lecture videos.
Teachers can also create test series to help students assess their knowledge.
Students can view all available resources and test series.
Technologies and Tools Used
Java: The primary language used for Android development.
Android SDK: The development kit for building Android applications.
Firebase Database: For managing authentication, real-time data storage, and retrieval.
Java Random Token Generator: Used for generating verification tokens for email-based registration.
Version Control (Git & GitHub): The project is hosted on GitHub for collaboration and version control.

#Installation

#Prerequisites
Android Studio: Ensure that Android Studio is installed to run the application.
Java 8 or higher: The application is developed using Java, so a suitable Java environment is required.
Firebase Account: You'll need a Firebase project configured for authentication and database services.
Steps to Install

1.Clone the Repository:

git clone https://github.com/hkranshi/Vidhyalay.git
cd Vidhyalay

2.Open in Android Studio:
Open Android Studio and select File > Open.
Navigate to the cloned repository and open the project.

3.Configure Firebase:
Create a Firebase project in your Firebase Console.
Download the google-services.json file from your Firebase project and place it in the app/ directory.
Ensure that Firebase Authentication and Firestore Database are enabled.

4.Build and Run:
Connect your Android device or start an Android emulator.
Click Run to install the app on your device or emulator.

#Usage

Teacher Login

Register as a teacher using your email.

Verify your email with the token sent to your inbox.

Login as a teacher.

Access the interface to upload lecture videos, create test series, and categorize resources by domain (e.g., GATE, UPSC, CAT).

Manage the uploaded resources (edit or delete).

Student Login

Register as a student using your email.

Verify your email with the token sent to your inbox.

Login as a student.

Access the available educational resources and test series.

Browse through resources specific to your domain (e.g., Mechanical Engineering, Civil Services, MBA).

Folder Structure

Vidhyalay/

│
├── app/                    # Android application source code

│   ├── src/

│   ├── res/                # Resources (layout files, drawable assets)

│   ├── manifests/

│   └── java/               # Java source code (activities, adapters, etc.)

│
├── README.md               # Project documentation

└── build.gradle            # Gradle build configuration



#Future Enhancements

Real-time Notifications: Notify students when new content is uploaded.

Discussion Forums: Allow students and teachers to discuss and collaborate.

Progress Tracking: Students can track their progress in the test series.

Domain-based Study Plans: Provide structured study plans for each domain (GATE, UPSC, CAT) to help students prepare effectively.

#License

This project is licensed under the MIT License. See the LICENSE file for more details.

#Contributions

Feel free to open issues or submit pull requests to contribute to this project. Contributions are welcome!  
