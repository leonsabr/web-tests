### Requirements
* Google Chrome in the default installation location.
* JRE (JDK) 8
* Internet connection

### Start local Selenium server
On http://localhost:4444/wd/hub
```
npm install -g webdriver-manager
webdriver-manager update
webdriver-manager start
```

### Run tests locally
Mac OS and Linux:
```
./gradlew clean test
```
Windows:
```
gradlew.bat clean test
```

### View test report
Allure test report is located at `build/reports/allure-report/index.html`.