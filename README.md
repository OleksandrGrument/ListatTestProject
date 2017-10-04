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
<img src="https://lh3.googleusercontent.com/34cF6anTvUdnaXj0Bhwcj_1aHqNm6eb97FPvoTzYOYYeoyAHogi9gGdcDdPHC3O9gmok_QA33sMZOMmNZNyjzbPJCpwybESjWcXraQWgH2vlx9CJRAse6-cJbr3X11B09t5rLb5c1JvVuS-7joK3c11KdODkzIEO8NGTq87P-BHwHBYnY7ydw44QdkUdSPnsCbwmgYQTGbIhVDpjDgrY30aoCUSfEvCY-TdiNhDaKQE-W7A5yLFnL91Hqiffqs3chlUt9gjYVDFPxCWRM_M0qdH1CMh2Zt4M2EwCqB1M0f7zPVZg5mX7SaizkdZXxgC8gKMIfoQdqgVEe0Kepi4JyPmzV8KzZkob-_XOmMkBCdnSAbJxJG6pNpLWdfsjdiaVwSCplFBBEXVgIikkux7eXieyvbfFme5OVceGkikatAxyOD5bruV9j-6AhtqRxg80pWXLOI6MDN2px-HStS2cZ0E4TGvR2rGK_Cij04HPN9UgALOTPrDYKuWXaw5Nnn854VjLoxq_Hc21TJAYxndEN5nO9O7slndCvg4Yd61hJGaZ-NT_DlK40B_qFR5Jw-i5_ncOfp7H3xHDPuWiOVcvK0H01EZbOk_jNAX2ZFyXtQ=w320-h568-no" style=" width:100px ; height:100px " />
