#!/usr/bin/python
__author__ = 'ehagnur'
'''
This function prints a tree of a given dependency graph
'''
mydict = {}
def createdict():
    global mydict
    with open('graph.txt') as gfile:
        for each_line in gfile:
            (left, right) = each_line.strip().split('->')
            if mydict.get(str(left)):
                lst = []
                lst.extend(mydict.get(str(left)))
                lst.extend(str(right))
                mydict[str(left)] = lst
            else:
                mydict[str(left)] = str(right)

def findsortedkeys():
    sortedkeys = sorted(mydict)
    for k in sortedkeys:
        print(k, mydict[k])
        if mydict[k]:
            pass

def printdep(the_list, level=0):
    for each_item in sorted(the_list):
        if each_item in mydict.keys() and len(mydict[each_item]) > 1:
            print('__|'*level, each_item)
            printdep(mydict[each_item], level+1)
        elif each_item in mydict.keys() and mydict[each_item]:
            print('__|'*level, each_item)
            if each_item < mydict[each_item] and mydict[each_item]:
                printdep(mydict[each_item], level+1)
            else:
                print('__|'*(level+1), mydict[each_item])
        else:
            print('__|'*level, each_item)


createdict()
print("printing the dictionary")
findsortedkeys()
print('-'*30)
print("Dependency Graph,")
print('-'*30)
letter = input("Type the letter you want to see the dependency: ")
if letter in sorted(mydict):
    printdep(letter)
else:
    print("Letter doesn't exist")
print('-'*30)
