# Typing Speed Test Application

## Overview
This Typing Speed Test Application is designed to help users practice and improve their typing skills. It features various typing tests in multiple languages, records results for accuracy and words per minute (WPM), and provides statistical feedback based on the user's performance over time.

## Features
- **Multiple Test Durations:** Choose from 15, 30, 60, or 120 seconds tests.
- **Language Options:** Supports typing tests in English, Czech, Russian, and Spanish.
- **Statistics Display:** View detailed charts showing progress over time in terms of WPM and accuracy over words and letters.
- **Result Management:** Users can save their results for later review and erase them if desired.


## Architecture

- `Main`: Main application class for a typing speed test program. Initializes the user interface and sets up the primary functionalities.

- `ResultsDisplayManager`: Manages the display of typing test results within the GUI.

- `ResultsRecorder`: Handles recording, saving, and retrieving of test results from a file.

- `StatisticsData`: Represents statistical data for typing tests (such as WPM, accuracy).

- `StatisticsDisplayManager`: Manages the creation and display of statistical charts and data visualizations.

- `TextDisplayManager`: Manages text display and formatting within the typing test interface.

- `TextLoader`: Loads test texts from files based on selected language.
