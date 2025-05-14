#!/bin/bash
echo "Enter the name of the folder"
read direc
if [ ! -d $direc ]
	then 	
	echo "Please provide the correct name: "
else
	cd $direc	
	echo "All the files in $direc are: "
for files in $(find . -type f)
	do 
	echo $files
	done 
	fi