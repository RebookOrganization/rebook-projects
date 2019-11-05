.. image:: https://travis-ci.org/mokarakaya/spring-boot-multi-module-maven.svg?branch=master
   :target: https://travis-ci.org/mokarakaya/spring-boot-multi-module-maven


Rebook multi module with kafka
~~~~~~~~~~~~

This project is a multi module maven project. Modules are api and web.

Steps to run;

First please navigate to webDemoProject folder and build the project as;

 crawl-service>mvn clean install

If everything works fine navigate to web folder and run spring boot project as;

 rebook-projects>cd rebook-web

 rebook-projects/rebook-web>mvn spring-boot:run


Then go to; localhost:8082

This project has 2 spring boot projects; crawl-service, rebook-web.
When web project runs it creates itself and it also creates api project. So, both modules run in single tomcat server.
Additionally, api module can run by itself since it's not dependent to web module.


