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

	- add the network check function and use it in the downloader loop ✔
	- change the downloader service to continue the work after closing the app until the connection was lost or completed then destroy itself ✔
	
	- Find a good icon for tag "New" ✔
	- Add "Favourite" tag to the list views ✔

	- add the sdcard handler class to the service to make sure that all the necessary directories are existing ✔
	- Make the sdcard handler class to take a drawable and store it in the sdCard and vice versa and does the peripheral works ✔
	- design the image downloader class that takes a link and returns drawable ✔
	- in downloader service after initial download there should be a composition of 2 secondary download and 1 image download until one finishes✔
	- make a class for cropping the image  to have suitable aspect ratio for indexImg ✔

	- move the dateview to the bottom of the element and group it with titleView ✔
	
	- make all the scraper functions return boolean to assure the success of the operation
	- use sharedpreferences to store the last time of the successful insert for each content and do not repeat download
		in a specific time around it
	- change the schematics of the article and use m.islahweb.org/all?items_per_page=50 for getting articles

	- design a priority list in downloader and all the downloads are inserted inside it and in each loop one is executed
		for example the priority of initial download is higher than secondary download and the priority of forced secondary download is highest
	- make a pending thread to run every 20 seconds when app is running in the service to look for network connection and start the download queue when the app stops there should be a variable in the service called running  that should be set to false and also a variable isConnected that shows the internet connection

	

	- solve archive time order problem

	- Work on static contents of the About jamaat use assets if necessary for the strings 


	Third phase(Polishing phase):
	- Implement the images in the images in the Icon4:
		a) see in the site  b) main page button on clicked
	- Notification for all new posts
	- A dialog showed when exit button in pressed
	- the new tag should move left to be aligned with the border of the list item
	- make a red filled circle with the number of the unread messages and put it on the main page items and news and articles tabs for showing how many items are not seen  just like telegram
	- add necessary elements based on the activity to the action bar like back and refresh
	- add network detector and give user related Toasts when the network is not available
	- reform the downloader and add start/stop mechanism to it
	- add a mechanism when the user clicks on a content and it is not downloaded moves on and stops the main download and focus on the specific download and the same for an item without picture
	- find good fonts and use them for the texts
	- use flat colors for the application
	- make the activity appear and then fill the list views and use waiting animations if necessary
	- when user clicks on the image of a news enters a loading screen and download the image and resumes the main download thread the image should have the ability to be stored in the sdcard when users prompts and should be able to zoom and move around

	Fourth phase(Feedback taking):
	- Use the site header and draw 9 patch for background of the header
	- Handle the case when the sdcard is not available - use other storages
	- view the comments in a view like Akharin khabar
	- Add a counter to the main screen buttons to show the number of new contents
	- take comment from the user in the same view
	- allow the user to rate the post
	- all these features are only available when the user is online

	

	First phase: ✔
	- write the code for the database making class and all its handlers ✔
	- use composition for adding handlers to the database making class ✔
	- write the service for downloading the web content including news,articles,interviews and announcements ✔
	- add priority system to the downloader service to download needed content first ✔
			finished at version 0.4.2  at 94/02/05

	
