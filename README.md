# AndroidLocationUsingGPS
Fetch device current location

How to use
1. Copy two files, GetUserAddress & GPSTracker into your project.
2. Give ACCESS_LOCATION permission in the manifest file.

write follwing statements

GetUserAddress gps = new GetUserAddress(this);
gps.executeGPS();

String city = gps.getCity();
String state = gps.getState();
String country = gps.getCountry();

Enjoy!
