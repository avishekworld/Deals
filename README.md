# Android Case Study

You have been given control over an Android project that was originally a proof-of-concept project.
The original developer of the project has since moved on to a new team, and Target has asked you to
turn the project into an application that the company could release to the public.

The goal of the app is to display a list of deals currently offered by Target, and to provide
information on those deals. As a POC, the app has a few deals hardcoded in and the code is
pretty rough. It is your job to turn this app into something useful!

**Requirements:**
1. Fix up the deals list to match the mockups shown in mockups/DealsList.png.
  Fonts, specific colors, and specific margins are not important, but you should aim to to get close.

2. Present a new view that displays deal details when a deal is clicked on the list screen.
  Use the mockups shown in mockups/DealsDetails.png. Again, specific sizing and other design details
  are not important.

3. The deals are currently hardcoded. Use the API at https://api.target.com/mobile_case_study_deals/v1
  to grab the real deals to display in the app.

4. Update PaymentDialogFragment to perform credit card number validation:
     - See Validators.kt under the `data` package for instructions on performing the validation,
       including  help on getting fake credit card numbers. You do *not* need to enter real credit
       card data.

       You are welcome to make any changes to `Validators.kt` as you see fit within the guidelines
       noted in the documentation in that file.
     - When a valid number has been entered, the "submit" button should be enabled. When the number
       is invalid, the "submit" button should be disabled.
     - You do *not* need to make any changes to the dialog layout, but you are welcome to do so if
       you wish.

5. ***Optional:*** Do something interesting! Add something to the app you think can really make it
  fun to use or showcases a particular skill you have. Maybe you love creating animations and want
  to show that off, or you are really excited about a new library and you just want to experiment
  with it. You will not be evaluated on whether or not you add extra functionality, but it
  can be a good way to start a discussion and highlight something you are passionate about.

Some guidelines:
- Feel free to use any open source software you wish. Be prepared to answer questions about *why*
  you chose any libraries that you add to the project.
- Treat this app as something you control technically. The way you chose to architect your
  solution will be a key aspect of  the case study review.
- Do your best to follow modern Android conventions and write clean, performant, and extensible code.
  Imagine that this app will continue to grow after you are done- how would another developer
  add additional screens or other functionality?
- While writing tests is not a requirement, be prepared to discuss how you would approach testing
  this project during the review. Having tests is one way to kickstart that discussion and showcase
  your approach.
- Be sure the app looks great on a variety of screen sizes!