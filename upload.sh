git add -u
git add .
git tag -a v1.0 -m 'first upload'
git commit -a -m "first upload"
git push -u origin master
git status
read -p "Press any key to continue... " -n1 -s