***Overview***

GolfSwingApplication is an Android application designed to help users improve their golf swing by providing detailed analysis and feedback on their training sessions. The app leverages the device's sensors (accelerometer and gyroscope) to track swing data, which is then used to offer insights and tips for improvement.

**Features**
- Real-time Sensor Data Collection: Uses the device's accelerometer and gyroscope to collect detailed swing data.
- Training Sessions: Supports various training sessions including direction training and acceleration training.
- Data Recording: Records sensor data during training sessions and saves it to CSV files.
- Session Analysis: Provides detailed analysis of the recorded data, including maximum acceleration and swing direction.
- Personalized Tips: Offers personalized tips based on the user's performance during training sessions.
- UI Navigation: Multiple screens for different training modes and data analysis, with intuitive navigation.

***Setup***

**Prerequisites**
Android Studio
Android device or emulator with accelerometer and gyroscope sensors

**Installation**
1. Clone the repository
2. Open the project in Android Studio.
3. Build the project to install the necessary dependencies.

**Running the App**
Connect your Android device or start an emulator.
Run the application from Android Studio.

**Permissions**
The app requires the following permissions:

WAKE_LOCK: To keep the screen on during training sessions.
BODY_SENSORS: To access the accelerometer and gyroscope data.
WRITE_EXTERNAL_STORAGE: To save session data to CSV files.

***Code Structure***
*MainActivity*
The MainActivity class handles sensor events and manages the UI states. It initializes the sensors, registers listeners, and controls the navigation between different screens based on user interactions and state variables.

**Sensor Event Handling**
The onSensorChanged method processes data from the accelerometer and gyroscope, calculates derived metrics, and updates the UI with real-time data. It also handles data recording and fusion of sensor data.

**UI Components**
The app uses Jetpack Compose for its UI. Each screen is implemented as a composable function, providing a modular and declarative approach to building the user interface.

