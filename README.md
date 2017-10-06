# listat-test-project

This is test project i have made for company 'Listat'

Task text :

A goal of the task isn’t implementing full logic of the app with detecting minor cases. The main goals are: an overall architecture of app, thread communication design, how code is readable, code style, supposed strategy of error handling, testing and so on.

Test task for Android developer

Create a multi-threaded application that perform calculation of prime numbers and show them in a RecyclerView.

Minimal supported API is 21.

The app should calculate prime numbers from some intervals specified in xml-based file placed in Asset folder. The app should perform calculation in several threads that send results to another thread as soon as a few prime numbers have been found. Another thread has to be waiting for the prime numbers, pulls them (in order how prime number were found) and sends them for displaying through another thread (storing thread). The storing thread should be implemented like a fake tcp-socket with states: establish connection, send (save to file) prime numbers at a slow pace, closed connection, reconnection if the opened connection was unexpectedly closed. After receive storing thread display founded prime number in RecyclerView.

Parsing xml should be performed without any (Android internal or 3rd party) library. field used as thread id for displaying.

Even threads output information on the right side, odd threads - on the left side as shown in the figure.

Example of input xml file: 1 100 200 2 500 888 ....

Parsing xml should be performed without any(Android internal or 3rd party) library. field used as thread id for displaying.

Even threads output information on the right side, odd threads - on the left side as shown in the figure

<a data-flickr-embed="true"  href="https://www.flickr.com/photos/152012252@N06/36818731524/in/album-72157685903489972/" title="ListatTestStructure"><img src="https://farm5.staticflickr.com/4510/36818731524_613d1b5967_z.jpg" width="320" height="568" alt="ListatTestStructure"></a>
