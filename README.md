# androide

https://github.com/anggelo17/androide

In order to perform prefix filtering I have implemented a Trie Structure where each node represents a letter
from a to z. Moreover each node has a flag endOfWord to tell the if the current node is the end of a word or not.
Since we can found cities with the same name even in the same country; we can differentiate them having a list of id
for the same name of  city. For example  city Athens,US and  city Athens,Greece ; every time we insert the word Athens we will
insert their id also because they are different cities.
An arrayMap was also used because once we have the results(list of ids), we can get the whole information wit the id.
This map has an id as key and a object Data as value. Another reason to use arrayMap is that is  more memory efficient than a traditional HashMap.

For retrieving the cities given a prefix we have to traverse the trie in alphabetic order; looking from
a to z; in that order . In this way we can get the cities ordered in alphabetic order. We will get a list of id as a result
. this List will be used for getting the whole Data object info like name,country and coordinates.

Complexity for retrieving the cities given a prefix is O(P) + O(N); where P is the length of the prefix and
N is the total number of nodes in the trie.


FUTURE WORK

Future work will be implementing diffutils, so we don't have to update the whole items in the list view every time there is a change.

https://github.com/anggelo17/androide
