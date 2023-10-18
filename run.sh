#!/bin/sh
ls > list.txt
pwd > head.txt
javac ListMaker.java
java ListMaker
rm ListMaker.class
rm 'ListMaker$1.class'
rm list.txt
rm head.txt