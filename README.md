# Amazon Price Watch Android

Android WebView client for a separately hosted PHP/SQLite price tracker.

## Build
GitHub Actions builds a debug APK on push to main. Download the `AmazonPriceWatch-debug-apk` artifact from Actions and extract `app-debug.apk`.

## Setup
At launch enter the HTTPS URL of your deployed Price Watch backend. The Android app cannot run PHP locally. The backend must have authentication and HTTPS before internet exposure. Price checks require a server cron job and a supported price data API such as Keepa.

## Notes
This is a debug build for testing, not a signed production release. No API keys belong in this repository.
