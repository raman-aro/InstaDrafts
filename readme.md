Raman Arora
20622513 r29arora
openjdk version "12.0.2" 2019-10-20
macOS 10.13.6 (MacBook Pro 2013)

How To:

Download and unzip the file named "RamansFotag.zip"

Navigate to the folder named "RamansFotag" through the terminal.

Type and enter "make run" command in the terminal while in this folder directory. It should contain the out folder, src folder and the makefile.

This will launch an application named "Raman's Fotag"

The Top ("North) Bar containing the "Upload Image", "Filtering Star", "Clear Rating", "Clear Images" buttons and the Grid View / List View toggle is the main settings panel for the program.

	Use the "Upload Image" button to import images into the program.
	The file exploer will open in the RamansFotag folder by design. It will also only accept PNG, JPG and GIF unless the File Format is manually changed using the drop down in the file explorer.

	The 5 "Filtering Stars" are used to filter the current view for images based on their rating.

	The "Clear Ratings" button is used to clear the current main filter and show all images currently imported into the program

	The "Clear Images" button is used to remove all images previously imported into the program.

	The "Grid View"/"List View" buttons are used to toggle between the "Grid" and "List" views of the program, each containing scrollable panes if the number of images surpasses the displayable amount on screen. Each view is also resizable and the Grid view "wraps" images to the bottom of the window if necessary.

	* Note: A custom Scrollable Flow Layout known as "WrapLayout" was used for this behaviour. This was found at camick.com/java/source/WrapLayout.java.
	Special Thanks to the author of this extraordinarily useful Layout Manager! 


The Main (Center) part of the application, contains the images imported.

	The images can be enlarged by clicking on them, which opens a "Detailed View". This detailed view shows an enlarged version of the image as well as the current rating of the image and allows the user to change/clear the rating of the picture.
	This view intentionally removes the rating functionality from the main frame and reimposes it within the detailed view to ensure consistency of ratings displayed and simplicity for the user. 
	This detailed view can be exited like any window, using the "x" in the top left corner.

	Back within the Main part of the application, each image displays the name and creation date, underneath the image preview and also provides functionality to rate/clear rating of the picture.

Finally, upon closing the application using the "x" in the top left corner, the program saves the path and ratings of all currently imported images within the program into a text file called "saveFile.csv" in the main "RamansFotag" folder.
These images are reimported when the program is relaunched.





# InstaDrafts
