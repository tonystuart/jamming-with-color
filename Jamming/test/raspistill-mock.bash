#!/bin/bash
#set -x
while read line
do
	if [[ "$line" == "X" || "$line" == "x" ]]
	then
		exit 0
	fi
	ln images/Jamming.jpg Jamming.jpg
done
