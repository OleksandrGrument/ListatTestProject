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
<img  src="https://lh3.googleusercontent.com/yJHqTIMNakTQT9cPUQBfFe0RjDPN9sahqW52pQaEyu4crD3Ciu-C65_wDmZCs0yBUguOVUIvOhOfJKvoiTgaCkTmkl4OeICW5Z-8afhtPrGXCAYGW2OSDsKUcnG5eNwX9_uyUGwD16LGTv0bRC1muDAry7hwhjGX8E-pT1aQlKB6uUgQ-sMLQw4NGBu0y6AP7Hv2k7bm-YagBXCisR9YRkaOBizIb4IpdTb_9HsgDG0nyekFJNrZMza7Lw9RTSfJrPTJJys8vjvlipGEyc5dLwsXVV5Mh_cejlzykkIcIafoUw7-r8RqYXxPJjbzaGUAc6GKCXoZ2KRrJL3KSpJ20lH4NEfrWRtP_rTBl6KuivDSzUsrEAJ9RkxUaJn2K-edQmsAQ4tlU5saH_2PR6qLhHJ2_CxciM0kL6MkOn8Ni62a50HErc2YxnCa_-NAS41IYykn_Vy7yugafji21iLxorEKZpN1ut5wgSHszWVWCPPDnVxK_G6Wq6SFyp-Bdpb5oEcSWynUDcb2yQYwJhtnzE0WKUwG3XQUJ99bAghEfxpWifAWZSe6TaczeSKuFgd8bZhkSWRrB9YrRlPitl6JXCeL8PX9NxvS5jlhdyVdpA=w320-h568-no" style=" width:100px ; height:100px " />
