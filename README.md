Userlinks for Flussonic
=======================

###How to run

1. Install Java 8 JDK and Maven 3
2. mvn clean install
3. mvn jetty:run
4. open in browser: http://localhost:9999

###Lincense

Apache License 2.0. See LICENSE, LICENSE-2.0, NOTICE.
In other words, yes, you can use it for your commercial purposes.

###Why

Flussonic have a very good authorization facility. But it turns out that some users need a quick-and-dirty solution to this problem, that reduces complexity of auth system and instead provides precise user management out of the box. This app designed for you.

###Technical details

Full stack is: Java, Wicket, Spring, Spring Security, Hibernate, SQLite, running on embedded web server Jetty.

Web design theme is "SB Admin 2":
[strartbootstrap page](http://startbootstrap.com/template-overviews/sb-admin-2),
[view source on github](https://github.com/IronSummitMedia/startbootstrap-sb-admin-2)

* Why Java? It's stable and fast. GPL with classpath exception, and we don't distribute sources of Java itself.
* Why Jetty? It may reduce memory footprint compared to enterprise servers like Wildfly or Glassfish. Tomcat leaks memory. Apache 2.0 License.
* Why Wicket? It may boost development speed of small and medium-size web applications. I still prefer Scala+Playframework2, but there we need to use pure Java to reduce memory footprint. Apache 2.0 License.
* Spring and Hibernate are established Java standards. Both Apache 2.0 License.
* Spring Security is out of the box security implementation (and Wicket native implementation is not good at all). Apache 2.0 License.
* SQLite is the only popular embedded database with good license (not GPL and AGPL). License - no license, public domain. JDBC connector is under Apache 2.0 License.
* SB Admin 3 is light theme based on Bootstrap 3 and designed in LESS. Apache 2.0 License.
