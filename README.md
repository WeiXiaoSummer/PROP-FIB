# SkylineBot
Pràctica de l'assignatura LLENGUATGE DE PROGRAMACIÓ


## Introducció 
Aquesta pràctica consisteix en desenvolupar un chatbot en Telegram per a la manipulació d'Skyline via un intèrpret. L'Skyline d'una ciutat mostra una vista horitzontal dels seus edificis.

La part interessant d'aquesta pràctica és que haurem de desenvolupar desde 0 un intèrpret que és capaç de interpretar i manipular una serie d'operadors, per aconsenguir aquest objectiu ens cal definir la part gramàtica i lèxica del nostre llenguatge. 


### Prerequisits 

Per poder executar el nostre bot s'ha de tenir instalat `Python3` i `antlr4`. Podem descarregar el fitxer .jar del `antlr4` en el link següent：https://www.antlr.org/download/antlr-4.7.1-complete.jar, i podem trobar l'instrucció de l'instal·lació en aquesta pàgina:
https://github.com/antlr/antlr4/blob/master/doc/getting-started.md.

Un cop tenim instal·lada `Python3` i `antlr4` en el nostre ordinador aleshores ens cal instal·lar el python runtime per `antlr4`, podem fer servir un dels següents commandes:

```bash
> pip3 install antlr4-python3-runtime==4.7.1
> pip install antlr4-python3-runtime==4.7.1
```
I també ens cal el paquete `matplotlib` per fer plots i el paquete `python-telegram-bot` per connectar-nos al Telegram, podem fer servir els commandes següents:

```bash
> pip install matplotlib==3.2.1
> pip install python-telegram-bot==12.7
```

## Execució
Per l'execució del programa has d'introduïr la següent comanda situant en el directori del `bot.py`:

```bash
> python3 bot.py
```
Un cop executat el nostre programa aleshores ens dirigirem al Telegram per poder iniciar conversació amb el nostre bot. El meu bot és diu `SkylineBot` i el seu username és `@weixiaoBot`.

## Tutorial
El nostre bot permet dibuixar `skyline` dibuxiant rectangles. Hi ha tres maneres d'especificar un `skyline`:

* `Contrucció simple`: `(xmin, alçada, xmax)`. Amb això construirem un rectangle que va de `xmin` a `xmax` 
* `Construcció composta`: `[(xmin, alçada, xmax), ...]`. Que consiteix en una sèrie de rectangles simples
* `Construcció aleatori`: `{n, h, w, xmin, xmax}`. Construeix `n` edificis, cadascun d’ells amb una alçada aleatòria entre `0` i `h`, amb una amplada aleatòria entre `1` i `w`, i una posició d’inici i de final aleatòria entre `xmin` i `xmax`.

Els operadors permesos són:
* `skyline + skyline`: unió
* `skyline * skyline`: intersecció
* `skyline * N`: replicació `N` vegades de l’skyline
* `skyline + N`: desplaçament a la dreta de l’skyline `N` posicions.
* `skyline - N`: desplaçament a l’esquerra de l’skyline `N` posicions.
* `\- skyline`: retorna l’skyline reflectit.

Cal destacar que `N` ha de ser un nombre positiu.

## Aclariments sobre la semàntica i lèxica del llenguatge
En aquesta pràctica he definit uns regles que no estàn especificades en l'enunciat:
* Sempre s'ha de deixar espais per l'operador `-`, és a dir, `(1,2,3) - 4` està permès però `(1,2,3)-4` no. Això és per evitar confució amb el simbol negatiu `-`.
* En la descripció d'una skyline els espais són ignorats, és a dir, tant `(1,2,3)` com `(1, 2,   3)`, `(1,   2,  3)` estàn permesos.
* En l'operació de desplaçament només són vàlids els nombres naturals, ja que sino no tindrà sentit definir dos operadors de desplaçament.

## Cost dels operacions
* Unió: `O((n+m) log (n+m))`, sent `n` nombre d'edificis del skyline A i `m` el nombre d'edificis del skyline B.
* Interseccio: `O(n+m)`, sent `n` nombre d'edificis del skyline A i `m` el nombre d'edificis del skyline B.
* Desplaçament: `O(n)`, sent `n` el nombre d'edificis.
* Mirall: `O(n)`, sent `n` el nombre d'edificis.
* Replicació: `O(c*n)`, sent `c` el nombre de replicacions i `n` el nombre d'eficis.

## Construït amb

* [Python](https://www.python.org/) 

## Autor
* *WeiXiao Xia*

## Referències 📄
* [Python References](https://docs.python.org/3/reference/) - Per dubtes sobre Python
