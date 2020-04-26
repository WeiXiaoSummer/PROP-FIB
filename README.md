# LP-Practica
Pràctica de l'assignatura LLENGUATGE DE PROGRAMACIÓ 


## Introducció 
Aquesta pràctica simula el joc quatre en ratlla on l'usuari pot jugar amb un Bot seleccionant la dificultat desitjada (random, greedy i smart) fent servir el llenguatge de programació *Haskell*. 

La descripció del joc ho teniu en el: [Quatre en ratlla](https://en.wikipedia.org/wiki/Connect_Four). En aquesta versió es tria aleatòriament qui comença (jugador humà o jugador  bot) i es pot especificar la mida del tauler.


### Prerequisits 

Per poder compilar el programa s'ha de tenir instalat `GHC` i també s'ha d'importar `System.Random` si no està importat. En Mac:

```bash
> brew install cabal-install
> cabal update
> cabal install --lib random
```
En Ubuntu:

```bash
> sudo apt install cabal-install
> cabal update
> cabal install random
```

## Compilació

Per compilar el programa has d'accedir al directori del fitxer i executar la següent comanda:

```bash
> ghc joc.hs
```

## Execució
Per l'execució del programa has d'introduïr la següent comanda situant en el directori del fitxer compilat:

```bash
> ./joc
```
Un exemple d'execució seria:

![](execucio.jpg) 

## Tutorial
Aquest programa permet el jugador jugar amb un dels bots dissenyats, concretament són els següents

* Random (el Bot escolleix una columna a l'atzar)
* Greedy (el Bot usa l'estratègia greedy)
* Smart (el Bot usa l'estratègia minimax)

Un cop escollida la mida del tauler i el Bot, l'usuari només ha d'introduir en cada ronda la columna on vol posar la fitxa. En el cas que s'introdueix un index no vàlid el programa s'avisarà i pots tornar a introduir de nou
![](error.jpg) 
En tot moment es veurà el tauler actualitzat i al final de la partida es mostrarà en forma de missatge qui es el guanyador.
![](winner.jpg) 
## Construït amb

* [Haskell](https://www.haskell.org) 

## Autor
* *WeiXiao Xia*

## Referències 📄
* [Haskell References](http://zvon.org/other/haskell/Outputglobal/index.html) - Per dubtes sobre funcions de Haskell
