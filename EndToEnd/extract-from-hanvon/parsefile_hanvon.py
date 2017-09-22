#!/usr/local/bin/python3
# -*- coding: utf-8 -*-
'''
Created on Mar 26, 2013

@author: ylyan
'''


word_file = open("newword.txt", "r")

word_list = []
eof = False

def checkNoUnwantedCharInString(str):
    unWantedChars = [';', '*', '/', '~', '[', '}', ')']
    for ch in unWantedChars:
        if str.find(ch) != -1:
            return False
    return True

while not eof:
    curLine = word_file.readline()
    # curLine = curLine.decode("UTF-32LE")
    if len(curLine) >4 and checkNoUnwantedCharInString(curLine):
        word_list.append(curLine)

    if curLine == "":
        eof = True
print('===================================')
print('Words inmported from Hanvon: %s' % len(word_list))
print('===================================')

words = []

f = open("output.txt", "w")

for word in word_list:
    f.write(word)

f.close()
