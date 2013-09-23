'''
Created on Mar 26, 2013

@author: ylyan
'''

my_file = open("newword.txt", "r")

my_list = []
eof = False

while not eof:
    curLine = my_file.readline()
    if curLine.find('*')==-1 and curLine.find(';')==-1 :
        my_list.append(curLine)
    if curLine == "":
        eof = True

print len(my_list)
print my_list[3]


words = []
    
f = open("output.txt", "w")

for word in my_list:
    f.write(word)

f.close()
