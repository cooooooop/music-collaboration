Hi XXXX,

Thanks for maintaining interest in helping me with this project. If you are not able to connect to the internet with your laptop at work you will probably want to download all the files you need first at home, then try to setup everything. I imagine you are interested in some specifics about how the application will work but I think that is getting several steps ahead of ourselves. As you are coming into this blind I will first need to introduce you to the different technologies that we will be using, to program and to work together.



The most important technology you will be using is Eclipse - http://eclipse.org/
When you go to download Eclipse you might be put off by how many different versions there are, I think if you download the first one (Java EE) you should be fine. The good thing about Eclipse is that it can be easily modified to do what you want, so no matter what version you get you can always "upgrade" it to do something new.



Since I suggest you get the Java version, you can probably guess that much of the work we will do will be in the Java language. This is somewhat irrelevant. You will likely be using many languages. Some will be simple, like XML and HTML and others will be more complex like Java. The easiest way to learn these is to look at examples. You might find that you spend more time finding the right search in Google to find code that is already made for you, than you spend actually writing code down. There are many helpful tools available, and tricks to make things easier, but since I am not there to point them out in person you will likely have to do things the hard way. Expect that things will not be easy at first and ask all the questions you need to.



You will need to download and install the Java Development Kit (JDK). I think you want "JDK 6 Update 27 with Java EE", search for that on this page: http://www.oracle.com/technetwork/java/javase/downloads/index.html. By the way, EE stands for Enterprise Edition. I don't know what the difference is between this version and the standard version.



At this point I should probably mention that setting up your programming environment and getting the project to compile and run without errors often takes several hours. Don't get discouraged when you find that something is not working and the solution is not obvious. This will happen and it will likely be my fault for not communicating properly. Again, ask questions.



OK. The second most important technology you will be using is Subversion (SVN) - http://subversion.apache.org/
SVN is the technology that allows both of us to work on the same project. Google is nice enough to give free space to open source projects like ours where we can use SVN to collaborate. I will define a few terms for you:



repository (repo) - this is where the code is stored. For our project it is https://music-collaboration.googlecode.com/svn/ (you can put this url in an internet browser to see I have already created two Java projects in this repository, in the trunk folder).
checkout - this is the action you take when you want a fresh version of the project from the repository.
commit - this is the action you take when you want to save your edited code to the repository.
update - this is the action you take when you want to get the latest code from the repository (after you have already checked out the project).
revert - if you make changes to code, but you want to erase them and start from the last update you can use the revert action



To take some of the mystery out of SVN I should tell you that it creates hidden folders in the project that stores information about past code and what you have edited etc. These folders are ".svn". If you have not already you should go to the folder options area of Windows and "show hidden files/folders" and "show file extensions".



There are 3 different ways in which I use SVN and I don't know which you would prefer. You can
1. just download a binary from the URL above and use the command console.
2. download TortoiseSVN, which is a plugin to windows that interprets the ".svn" folders and gives you nice icons and right click menu options
3. upgrade Eclipse with the subclipse plugin, which gives you the ability to use SVN within Eclipse (right click on a project -> Team menu)



I would recommend a mixture of #2 and #3, but be warned that you need to get the same version number for both (1.6.something).



At the end of this email I will write out a concise list of things you should do to set up your environment, there will be more specific information about SVN and how to use it. I added a new file and then committed it, called HELLO in the ServiceProject folder. You should try to commit your own changes to that file as practice. Oh, important part: you need a username and since we are using google code it needs to be tied to your gmail account. I have added xxxxxx@gmail.com, go to https://code.google.com/p/music-collaboration/ and login, then go to Source, there should be a link to see the password you need to use for this. It might be at this address: https://code.google.com/hosting/settings



Eclipse, JDK, SVN. Those are the important parts of basically any project.



For our project we will also be using Google App Engine. This is something Google has recently dreamed up and I have never used it in a large project before (not that our project will be large necessarily). There are some common issues that developers need to overcome which is what Google is hoping to help with here;



1. Need to store data so that when the user leaves the application they are able to save things
2. Need a server application that does computation and analyzes user input, this application needs to be hosted online so the user does not need to install anything
3. Need a client application which acts as the interface between the user and the server (takes user input, shows results, etc). This is typically a web page, and in our application it will be HTML.



So Google App Engine (GAE - maybe pronounced gay?) gives us (for free); server space online and the ability to store data and host webpages. This is free until we hit a quota (storing too much data, or too much bandwidth from server to users computers), at which point someone will have to pay to continue the service (hopefully not us). It is perfect for development.



You will need to download and install GAE - http://code.google.com/appengine/downloads.html
Get the SDK (Software Development Kit) for Java (not Python or Go) and the Eclipse plugin.




---

That was the technology overview. Now I will try my best to give you concise instructions to setup your environment and compile and run the project.
1. Install the JDK
2. Install Eclipse, open it and create a new workspace folder (it gives you a suggestion, this is where all the project code will go), close the Welcome tab if it comes up
3. Install TortoiseSVN (optional), then install Subclipse (http://subclipse.tigris.org/servlets/ProjectProcess?pageID=p4wYuA, follow the guided instructions on this page, it is the same process to upgrade Eclipse with other plugins, you just need the "update site URL"), remember - use same version of SVN for TortoiseSVN and Subclipse
4. Install GAE, with plugin. This page may help: http://code.google.com/appengine/docs/java/gettingstarted/
5. In Eclipse, right click on the left hand side (Package Explorer), select Import..., in the new window select "Checkout Projects from SVN" under SVN folder. Use the repo URL: https://music-collaboration.googlecode.com/svn/ as a new repository location and select one of the projects in the trunk folder (I have attached an image of what this should look like at this point "CheckoutSVN.png", make sure your Eclipse also has the same icons in the toolbar on the left (blue circle with a g, red toolbox, etc, these are from GAE plugin).
6. Program the rest of the project so it works the way I want. Thanks.



You can test the application locally (meaning the server runs on your computer), or you can deploy the application to the internet, both with only a couple clicks.
- to test locally, right click on music-collab project, select Debug as->Web Application. Then go to http://localhost:8888/ in your internet browser.
- to deploy, right click on music-collab project, select Google->Deploy to App Engine (this takes a while on my computer). Then go to http://music-collab.appspot.com/
I sent you an invitation to collaborate on the Google App Engine project. You might need to enter in user info before you can deploy the project.




---

The current project is nothing special, you can browse to it at http://music-collab.appspot.com/
The user is able to upload a file to the server (but the server does not store the file anywhere). When the upload finishes, it checks that the file was an mp3 file and then tells the user the name of the file and spits out every tenth byte. So essentially it does nothing.



A short list of features I would like to get done pretty quickly:
Allow user to upload files and store them under their username (authenticate with a google id)
Allow user to download other files from other users
Allow user to mark which files to share with who



That should be pretty easy and I have a few ideas over and above this, but going into those now is probably unnecessary.



Good luck, and let me know if you have any trouble. When you have everything setup I will give you a short list of tasks to do. In the meantime I will be working on getting those features above working in some fashion.



Curtis