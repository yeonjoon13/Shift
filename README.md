# teamJ-Shift

We have created a Job Java class to facilitate working with the different types requirements for 
jobs, created a layout (*.xml) for the different types of jobs in home screen, updated the home 
page after a user adds or leaves a job, made a register page layout such that we can add user to 
our realtime database, an arrayAdapter for the collection of job objects, made a realtime database
so we can add usernames and passwords with stored data of jobs and certifications/qualifications, 
remember the user who accessed the app without logging out and retrieve the user’s home page when 
it’s relaunched, authenticate users to make sure only user’s with the correct passwords can log in, 
created a login screen layout with toasts for incorrect usernames and passwords, Implement favorite 
jobs feature where they like certain jobs and it would appear on their homepage, retrieve and 
populate previous job details when the screen is launched. Since the masterJob for each user is 
customized and unique, each user will have their job recyclerViews populated directly from the 
database and will therefore have their changes be preserved even when they log out and log back in. 

For now, the company logos are all hard-coded in to be the McDonald's Logo, but this will change
in the next sprint as we will use the database to fill in the company logos dynamically to fit each
company. Additionally, the layout for "previous Jobs" are the same as the other job cards in the 
application, but this will also have to be modified in the future since the user would not be 
able to apply to previous jobs. We will probably change the syntax of the bottom button to indicate
a different command in the future. 

On this sprint, there exists one notable bug. This bug occurs when a new user is created and 
the "masterJobs" are filled in during HomeActivity onCreate. The job cards do not populate
the homeFragment when the user first logs in, but this is easily solved by visiting another 
fragment and returning back, which populates the job cards as usual. This bug does  occur 
for returning users, but at a much infrequent rate. Regardless, further testing will be required to 
probe the source of this bug in the future. 

In the bottom navigation bar, we have implemented the library and home screen, but we still need
to implement the filter on the home screen and the search fragment. The training activities will
also need to be completed in future sprints. While we only have two jobs on the realtime database
for now, we aim to have 20+ jobs in the future so there are more choices the users can make and 
customize. 


Login Information: (You can create your own)
Username: test11@gmail.com
Password: 123456 

Username: test55@gmail.com
Password: 123456


Sprint 2:

Welcome to Sprint 2. Our main things we did this sprint was doing the tags, search, quiz and video 
watching sections of our app, as well as the necessary screens for each.

Our existing work was updated a lot during this sprint. We modified our Job Description Activity to 
include information about job requirements, since they were implemented in this sprint. We also
modified our browse section of the app, since the requirements of videos and quizzes were
implemented. Our job class in the code and firebase was also modified to contain the new quizzes and
videos. We also were able to modify the search tab in our bottom navigation to give it some
functionality.

For the videos, we had to implement a video watching service. We made a video class to store the
information about the videos, a video adapter to show how the videos would look in the training task
activity, and a video activity that would show when the videos would play. After this, we made the
xmls for the video cards as well as the start of the training task activity class. We finally got
the sample videos and used those for our app.

For the quizzes, we had to make a question class that would contain all the information for the
question banks, and add those question banks to the firebase. Then, we made the quiz activty, which
involved tracking the correct answers and storing information from button presses, as well as
sending updated statuses to the firebase and using that information to update the text and
containers for the jobs. We also made customized quiz thumbnails and debugged everything.

For the filters and tags, we added a lot of functionality to the job class and the home activity.
We added a menu that would allow the user to select which tags they wanted to see, which would be
updated in the recommended jobs tab. We also updated the firebase to reflect which jobs had which
tags.

For the search, we made a new activity to be able to view the items when searched, but most of the
search functionality was done in home activity. We made sure that users could search case
insensitive and could substring search. We then made sure that clicking on the searched items would
allow you to edit the job description normally.

We fixed most of our bugs, but we still have a few. Creating a new user does not immediately 
populate the jobs database, but that is changed once the screens are switched back and forth. The 
first tap on the quiz also sometimes does not work.

In our last sprint, we want to implement the settings activity, and improve some of the design
aesthetics we feel are lacking.

Login Information: (You can create your own)
Username: 2@gmail.com
Password: 123456 