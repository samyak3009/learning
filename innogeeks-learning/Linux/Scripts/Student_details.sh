
date>Student_log.txt

echo
echo "Student Details">>Student_log.txt 

echo "Name Of Student : "
read Name

echo "Student Name : $Name">>Student_log.txt
echo
echo "Roll No. Of Student : "
read RNo

echo "Roll No. : $RNo">>Student_log.txt
echo
echo "Marks Obtained : "
read OMarks
echo "Total Marks : "
read TMarks

Percentage=$((OMarks*100/TMarks))

echo "Percentage : $Percentage" >>Student_log.txt