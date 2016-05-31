@echo off
set /p commit="Enter Commit name: "

git add --all
git commit -m "%commit%"
git push -u origin master

pause