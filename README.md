# VitalSense

VitalSense is an Android application that leverages smartphone sensors to monitor various health metrics such as SpO2, heart rate, and respiration rate. Additionally, it includes a Health Assistant module that provide personalized health recommendations.

## Features

### SpO2 Module
- Calculates blood oxygen levels using the smartphone's camera and flash.
- Utilizes RGB color intensities and a random forest classifier to provide accurate SpO2 readings.
- Offers real-time insights into oxygen saturation without the need for external hardware.

### Heart Rate Monitoring Module
- Tracks heart rate using the smartphone's camera and flash.
- Provides heart rate in beats per minute (BPM), enabling heart rate monitoring.

### Respiration Rate Measurement Module
- Measures respiration rate using the smartphone's accelerometer when place on chest.
- Captures z-axis movements to monitor respiratory health.
- Calculates breaths per minute by analyzing accelerometer data peaks.

### Health Assistant Module
- Analyzes health metrics such as SpO2, heart rate, and respiration rate.
- Uses OpenAI API to provide personalized health recommendations.
- Offers suggestions on lifestyle adjustments, activity goals, and reminders for medical consultations.

## Installation

To get started with the VitalSense App, follow these steps:

1. **Clone the repository:**
    ```sh
    git clone https://github.com/vikisaintgits/vitalsense.git
    ```

2. **Open the project in Android Studio:**
    - Launch Android Studio.
    - Select "Open an existing Android Studio project."
    - Navigate to the cloned repository and select the project directory.

3. **Install dependencies:**
    - Ensure that all required dependencies are installed as specified in the `build.gradle` files.

4. **Build and run the project:**
    - Connect your Android device.
    - Click the "Run" button in Android Studio to build and install the app on your device.

## Screenshots
<p align="left">
<img src="https://raw.githubusercontent.com/vikisaintgits/vitalsense/main/screenshots/dash.jpeg" alt="Image" width="200"/>&nbsp&nbsp&nbsp&nbsp
<img src="https://raw.githubusercontent.com/vikisaintgits/vitalsense/main/screenshots/image_catch.jpeg" alt="Image" width="200"/>&nbsp&nbsp&nbsp&nbsp
<img src="https://raw.githubusercontent.com/vikisaintgits/vitalsense/main/screenshots/timer.jpeg" alt="Image" width="200"/>&nbsp&nbsp&nbsp&nbsp
<img src="https://raw.githubusercontent.com/vikisaintgits/vitalsense/main/screenshots/result.jpeg" alt="Image" width="200"/>&nbsp&nbsp&nbsp&nbsp
</p>


