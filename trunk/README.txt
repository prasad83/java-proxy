JavaProxy (Mar 28, 2007)
========================

About:
======
A project of mine required proxying of web pages. The project was on Java Project.
I looked around for library that could solve the problem but found none at the point.

I came across CGIProxy (http://www.jmarshall.com/tools/cgiproxy/) which was interesting
it was then I decided to get something in Java.

I had used the HTMLParser library (http://htmlparser.sourceforge.net/) earlier was looking
into its documentation and found someways of implementing JavaProxy using it and finally 
got this implementation working.

This is just a very basic implementation it surely will have lot of bug to fix.
Let me know if you liked it or adding more features, write a mail if you have anything to say
on this to (prasad83.a@gmail.com)

Usage:
======
Copy the JavaProxy.war to your Tomcat/webapps folder and you should see it working.
http://localhost:8080/JavaProxy/

Build:
======
Set the tomcat variable in the build.xml and use ant to build the project.

Thank You