# Galen-TestNG-Appium-Maven

Galen Test integrated with Appium Android, Appium IOS and Webdriver.

<h1>prerequisite</h2>

1. Appium installed through node.
2. XCode installed.
3. Android SDK 
4. Chrome Browser should be installed on the real device.

<h2>Run test</h2>

    mvn clean test 
    
    
Once the test are completed, you will find the reports under test-output/galen-html-reports

TestDump.java is test example to get the entire page dump which helps to the get elements for spec file.

once the test are completed, navigate to src/test/resources/contactsPage-dump/page.html

PageDump:
https://www.youtube.com/watch?v=QyDL3jJyCwA&feature=youtu.be


Note: For some reason checkLayout fails on android emulator.




