# Teoria Współbieżności
## Laboratorium 11 - Teoria śladów cz. 2
### Magdalena Pastuła
### Data laboratorium: 15.12.2020

## 1. Zadania do wykonania.

Podczas tego laboratorium należało zaimplementować następujące problemy:

1. Wyznaczenie relacji zależności na podstawie relacji niezależności.
2. Wyznaczenie śladu \[w\] na podstawie relacji niezależności wyznaczonej w poprzednim punkcie.
3. Wyznaczenie postaci normalnej Foaty na podstawie śladu /[w/].
4. Wyznaczenie grafu zależności Diekerta.
5. Wyznaczenie postaci normalnej Foaty na podstawie grafu Diekerta.

## 2. Koncepcja rozwiązania.

Każde zadanie zostało wykonane jako metoda głównej klasy.

## 3. Implementacja i wyniki.

3.1. Wyznaczenie relacji zależności na podstawie relacji niezależności.

Wyznaczenie relacji zależności wyznaczana jest poprzez stworzenie każdej możliwej pary liter oraz sprawdzenie, czy ta para znajduje się w relacji niezależności. Jeżeli nie, to dodawana jest do relacji zależności.

Wynik dla pierwszego przykładowego wejścia:
```
[(d,d), (d,b), (c,c), (b,d), (c,d), (d,c), (c,a), (a,c), (b,b), (a,b), (b,a), (a,a)]
```
Wynik dla drugiego przykładowego wejścia:
```
[(f,d), (e,e), (d,f), (f,f), (c,e), (d,d), (e,a), (b,f), (d,b), (c,c), (a,e), (b,d), (f,b), (e,c), (c,a), (a,c), (b,b), (a,a), (e,f), (f,e), (f,a), (d,e), (a,f), (e,d), (b,c), (c,b), (a,b), (b,a)]
```

3.2. Wyznaczenie śladu \[w\] na podstawie relacji niezależności wyznaczonej w poprzednim punkcie.

Wyniki dla pierwszego przykładowego wejścia:
```
[bdaacb, bdaabc, badacb, baadbc, badabc, baadcb]
```

Wyniki dla drugiego przykładowego wejścia:
```
[acdcfbbe, acdcfebb, acdfcbbe, acdfcebb, adccfbeb, adccfbbe, adccfebb, adcfcbbe, adcfcebb, dafccbeb, acdcfbeb, daccfbbe, daccfebb, accdfbeb, acdfcbeb, adfccbeb, dacfcbbe, dacfcebb, dacfcbeb, daccfbeb, adfccbbe, adfccebb, accdfbbe, accdfebb, dafccbbe, dafccebb, adcfcbeb]
```

3.3. Wyznaczenie postaci normalnej Foaty na podstawie śladu /[w/].

Do wyznaczenia postaci normalnej Foaty wykorzystano algorytm opisany w dokumencie \[4\] z bibliografii.

Wynik dla pierwszego przykładowego wejścia:
```
(b)(ad)(a)(bc)
```

Wynik dla drugiego przykładowego wejścia:
```
(ad)(cf)(c)(be)(b)
```

3.4. Wyznaczenie grafu zależności Diekerta.

Wynik dla pierwszego przykładowego wejścia w formacie dot:

```
digraph g{
	1 -> 2
	1 -> 4
	2 -> 3
	4 -> 6
	3 -> 5
	4 -> 5
	3 -> 6
	1[label=b]
	2[label=a]
	3[label=a]
	4[label=d]
	5[label=c]
	6[label=b]
}
```

Uzyskany graf dla pierwszego przykładowego wejścia:

![Pierwszy graf](./graph1.png)

Wynik dla drugiego przykładowego wejścia w formacie dot:
```
digraph g{
	1 -> 2
	4 -> 6
	4 -> 8
	6 -> 7
	5 -> 8
	2 -> 4
	1 -> 5
	5 -> 6
	3 -> 5
	1[label=a]
	2[label=c]
	3[label=d]
	4[label=c]
	5[label=f]
	6[label=b]
	7[label=b]
	8[label=e]
}
```

Uzyskany graf dla drugiego przykładowego wejścia:   

![Drugi graf](./graph2.png)

3.5. Wyznaczenie postaci normalnej Foaty na podstawie grafu Diekerta.

W zadaniu założono, że graf wejściowy zawiera wszystkie krawędzie przechodnie, które by zostały uzyskane podczas jego wyznaczania.

Aby móc wyznaczyć postać normalną Foaty graf wejściowy jest najpierw przetwarzany, aby uzyskać z niego relacje zależności oraz słowo, dla których został wyznaczony, a następnie na tej podstawie wywoływana jest metoda z zadania 3.

Wynik dla pierwszego przykładowego wejścia:
```
(b)(ad)(a)(bc)
```

Wynik dla drugiego przykładowego wejścia:
```
(ad)(cf)(c)(be)(b)
```

## 4. Wnioski z ćwiczenia.

4.1. Wyznaczenie relacji zależności na podstawie relacji niezależności.

Uzyskane wyniki dla przykładowych wejść są poprawne.

4.2. Wyznaczenie śladu \[w\] na podstawie relacji niezależności wyznaczonej w poprzednim punkcie.

Uzyskane wyniki dla przykładowych wejść są poprawne.

4.3. Wyznaczenie postaci normalnej Foaty na podstawie śladu /[w/].

Uzyskane wyniki dla przykładowych wejść są poprawne.

4.4. Wyznaczenie grafu zależności Diekerta.

Uzyskane wyniki dla przykładowych wejść są poprawne.

4.5. Wyznaczenie postaci normalnej Foaty na podstawie grafu Diekerta.

Uzyskane wyniki dla przykładowych wejść są poprawne. Jednakże, postać normalną Foaty można było wyznaczyć bezpośrednio z grafu, przechodząc kolejno po wierzchołkach zaczynając od tych, które nie mają krawędzi wejściowych, i niezależne wstawiać do jednego nawiasu. 

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