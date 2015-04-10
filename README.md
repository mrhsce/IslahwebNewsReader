# IslahwebNewsReader
============================

An android news reader application for islahweb.org that extensively covers all the contents of the web site

**********************************************
Things to learn ->
	- learn how to use tabs and view pager in android
	- learn how to transfer data between activity and service

Things to do ->

	First phase: ✔
	- write the code for the database making class and all its handlers
	- use composition for adding handlers to the database making class
	- write the service for downloading the web content
	- add priority system to the downloader service to download needed content first
	- when downloader tries to download the image if fails change the address of the image to imgAddress(1) and if the number of the failed tries 		reachs 5 then the downloader skips that address 
