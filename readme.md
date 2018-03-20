# Weather application – Assignment002
=====================================================================
The final result is as shown below<br>
![Weather application002]( https://github.com/qumingzizhengnan/weather-application/blob/002/pic/2_1.gif) <br>

##Part 1
---------------------------------------------------------------------
###[1] Check network connection
There is a java class for checking network connection.<br>
![checkNetworkConnection](https://github.com/qumingzizhengnan/weather-application/blob/002/pic/check_net.png)
<br> 
And use it in the MainAciviy.java file.<br>
If the network connection fails, the app cannot update the weather information.<br>
![checkNetworkConnection](https://github.com/qumingzizhengnan/weather-application/blob/002/pic/check_net2.png)
<br>

###[2] Get xml file about weather
Get the xml file from the website that posted the weather forecast.<br>
![get Xml file](https://github.com/qumingzizhengnan/weather-application/blob/002/pic/get_xml.png) 
<br>
The Xml file just like this:<br>
![xml file](https://github.com/qumingzizhengnan/weather-application/blob/002/pic/xmlfile.png)
<br>
###[3] Parse the xml file
This is the function for xml parsing.<br>
![parse xml file]( https://github.com/qumingzizhengnan/weather-application/blob/002/pic/xml_parse.png)
<br>
####[4] Update related information
Update the date, temperature and the type of today.<br>
![update today](https://github.com/qumingzizhengnan/weather-application/blob/002/pic/update_today.png)
The codes about update the information of next days.<br>
![update future](https://github.com/qumingzizhengnan/weather-application/blob/002/pic/update_week.png)



