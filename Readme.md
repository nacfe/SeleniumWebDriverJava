# Selenium webdriver java demo

## Project Dependencies:


 component | version
 --- | ---
chrome | 80.0.3987.106 
chromedriver (linux) | [80.0.3987.16](https://chromedriver.storage.googleapis.com/index.html?path=80.0.3987.16/)

* There are other dependencies but they are managed by Maven.

* If you are not using Linux, you will need to download a chromedriver that works on your OS.

* Tested on Linux and macOSX.

___
## Setup:

On the project's base folder, run on a terminal: <br><br>
export AUTO_TEST_DOMAIN="\<insert your test domain\>" && mvn test
<br><br>
The test domain should be inserted without the "https://" header (e.g.: "https://www.example.com/" --> "example.com")


___
## Notes:

* Don't forget to read the comments and ToDos on the code.

* All input data for this test is on the file "src/test/resources/input/demoTest.json".

* The logs of the test can be seen on the console and on the files in "resources/logs".

* One step of the test needs an email address and, unfortunately, the same email can't be used twice. To go around this limitation, the given test email is appended with a random 6 digit number. The resulting email used on the test can be seen on the log files. <br>E.g.: if the test email is "mail@example.com" the used email will be something like "mail482173@example.com". <br><br>*This will polute the database so please, create and run a script to delete all the emails created by this test.*

* Since there was a lack of elements' ids on the website's source code, some elements are located by css/xpath selectors. This is not ideal. <br> The classes' names were usually not used because although they seemed unique, I suspected they were randomly generated (perhaps when compiling the source code).

* Due to lack of better knowledge about the business and minimum requirements, and also shortage of time, there is a lack of asserts on the test.

* All texts are hardcoded. They should have been labels instead, that would be later translated to a given language.

* A Jenkinsfile was added as an example. It was not possible to test it, so although it seems correct, it was not tested at all.

* This code was only tested on linux (debian) and macOSX. Results may vary, especially for different chromedrivers.

---
## ToDo:

* There are some ToDos on the code. On a real life project they should be "cards" instead.

* Use dependency injection.

* Add some test-ids to the pages in order to remove all searches being made with css / xpath locators.