# Makefile for JabberPoint

# $Id$

SRCS = Makefile *.{java,properties,xml,jpt,txt,gif,jpg,dtd,css} jp jp.bat

JAVAC = jikes +E

compile:
		$(JAVAC) *.java

clean:
		rm -f *.class

dist:
		jar cvf /tmp/jabberpoint.jar $(SRCS)
