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