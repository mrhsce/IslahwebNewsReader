# IslahwebNewsReader
============================

An android news reader application for islahweb.org that extensively covers all the contents of the web site

**********************************************
Things to learn ->
	- learn how to use tabs and view pager in android ✔
	- learn how to transfer data between activity and service
	- how to add elements to action bar

Things to do ->


	Second phase: proposed for Tuesday 02/09
	- Merge the three parsers for news in one ✔
	- Find a goot flat icon for filled and empty star for add to archive button ✔
	- Add archive button to the action bar and name it "Archive" ✔
	- Make a tab view for archive containing News,Announcements,interview and article ✔
	- When the tab is empty show the help to add a content to archive ✔
	- Add button to remove from the archive when content is removed archived becomes false and if ten other newer contents exist it is removed ✔

	- add function to clean the exceeding datas from the tables in the database handler ✔
	- when the program starts the service should remove the exceeding unarchived entries ✔

	- add the network check function and use it in the downloader loop
	- change the downloader service to continue the work after closing the app until the connection was lost or completed then destroy itself
	
	- Find a good icon for tag "New"
	- Add "Favourite" tag to the list views
	- In all list adaptors if the element was not seen the new tag should be adhered

	- add the sdcard handler class to the main activity to make sure that all the necessary directories are existing
	- Make the sdcard handler class to take a drawable and store it in the sdCard and vice versa and does the peripheral works
	- design the image downloader class that takes a link and returns drawable
	- in downloader service after initial download there should be a composition of 2 secondary download and 1 image download until one finishes
	- make a class for cropping the image  to have suitable aspect ratio for indexImg

	- design a priority list in downloader and all the downloads are inserted inside it and in each loop one is executed
		for example the priority of initial download is higher than secondary download and the priority of forced secondary download is highest

	- Work on static contents of the About jamaat use assets if necessary for the strings 


	Third phase(Polishing phase):
	- add necessary elements based on the activity to the action bar like back and refresh
	- add network detector and give user related Toasts when the network is not available
	- reform the downloader and add start/stop mechanism to it
	- add a mechanism when the user clicks on a content and it is not downloaded moves on and stops the main download and focus on the specific download and the same for an item without picture
	- find good fonts and use them for the texts
	- use flat colors for the application
	- make the activity appear and then fill the list views and use waiting animations if necessary
	- when user clicks on the image of a news enters a loading screen and download the image and resumes the main download thread the image should have the ability to be stored in the sdcard when users prompts and should be able to zoom and move around

	Fourth phase(Feedback taking):
	- view the comments in a view like Akharin khabar
	- take comment from the user in the same view
	- allow the user to rate the post
	- all these features are only available when the user is online

	

	First phase: ✔
	- write the code for the database making class and all its handlers ✔
	- use composition for adding handlers to the database making class ✔
	- write the service for downloading the web content including news,articles,interviews and announcements ✔
	- add priority system to the downloader service to download needed content first ✔
			finished at version 0.4.2  at 94/02/05

	
