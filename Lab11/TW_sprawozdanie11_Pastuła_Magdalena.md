# Teoria Współbieżności
## Laboratorium 11 - Teoria śladów cz. 2
### Magdalena Pastuła
### Data laboratorium: 15.12.2020

## 1. Zadanie do wykonania.

## 2. Koncepcja rozwiązania.

## 3. Implementacja i wyniki.


## 4. Wnioski z ćwiczenia.


Najpierw tworzysz graf składający się wyłącznie z wierzchołków, gdzie każdy wierzchołek odpowiada jednemu symbolowi ze słowa.
Dla każdego symbolu ze słowa (iterując od końca) iterujesz dla każdego symbolu następującego po nim w tym słowie. Jeśli są zależne, to jeśli nie istnieje ścieżka z pierwszego do drugiego, to prowadzić krawędź od pierwszego do drugiego.
W pseudokodzie:
```
g = createEmptyGraph(word)
for s1 in word.reverse():
  for s2 in word from s1 to word.end():
    if s1.dependent(s2) and not g.vertexOf(s1).existsPathTo(g.vertexOf(s2)):
      createEdge(s1, s2)
```

## 5. Bibliografia.
1. [Dokumentacja formatu dot w Graphviz](http://www.graphviz.org/pdf/dotguide.pdf)
2. [Opis HashSet](https://www.geeksforgeeks.org/hashset-in-java/)
3. [Sposoby konwersji z tablicy na HashSet](https://www.baeldung.com/convert-array-to-set-and-set-to-array)
4. [Opis algorytmu znajdowania postaci normalnej Foaty](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.38.4401&rep=rep1&type=pdf)
5. [Działania na Stringach w Javie](https://docs.oracle.com/javase/tutorial/java/data/manipstrings.html)
6. [Dokumentacja stosu](https://docs.oracle.com/javase/7/docs/api/java/util/Stack.html)
7. [Dokumentacja HashMap](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html)
8. [Dokumentacja LinkedList](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html)
9. [Wyrażenia regularne w Javie](https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html)
10. [Konwersja String do Int](https://www.tutorialspoint.com/java/number_parseint.htm)