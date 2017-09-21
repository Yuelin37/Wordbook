#!/usr/local/bin/python3
# -*- coding: utf-8 -*-
'''
Created on Mar 26, 2013

@author: ylyan
'''


my_file = open("newword.txt", "r")

my_list = []
eof = False

while not eof:
    curLine = my_file.readline()
    # curLine = curLine.decode("UTF-32LE")
    if len(curLine) >4 and curLine.find('*') == -1 and curLine.find(';') == -1 and curLine.find('/') == -1 and curLine.find('~') == -1 and curLine.find('}') == -1 and curLine.find(')') == -1:
        my_list.append(curLine)

    if curLine == "":
        eof = True
print('===================================')
print('Words inmported from Hanvon: %s' % len(my_list))
print('===================================')

words = []

f = open("output.txt", "w")

for word in my_list:
    f.write(word)

f.close()
