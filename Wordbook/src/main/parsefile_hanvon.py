'''
Created on Mar 26, 2013

@author: ylyan
'''

my_file = open("newword.txt", "r")

my_list = []
eof = False

while not eof:
    curLine = my_file.readline()
    if curLine.find('*') == -1 and curLine.find(';') == -1 and curLine.find('/') == -1:
        my_list.append(curLine)
        print curLine
        print len(curLine)
        print "****"
    if curLine == "":
        eof = True
print len(my_list)

words = []

f = open("output.txt", "w")

for word in my_list:
    f.write(word)

f.close()
