# Makefile for JabberPoint

# $Id$

SRCS = Makefile *.java *.properties jpt jpt.bat

dist:
		zip /tmp/jpt.zip $(SRCS)
