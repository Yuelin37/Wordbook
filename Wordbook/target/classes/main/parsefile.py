'''
Created on Mar 26, 2013

@author: ylyan
'''

my_file = open("newword.txt", "r")

my_list = []
eof = False

while not eof:
    curLine = my_file.readline()
    if curLine != "":
        my_list.append(curLine)
    else:
        eof = True

print len(my_list)
print my_list[3]


words = []
i = 0
for itme in my_list:
    if (i-3)%3 == 0:
        words.append(my_list[i])
        print i
        print my_list[i]
    i += 1
    
f = open("output.txt", "w")

for word in words:
    f.write(word)

f.close()
