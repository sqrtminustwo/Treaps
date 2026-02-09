#import "@preview/lilaq:0.5.0" as lq

#set document(
  title: "Benchmarks",
  author: "Maksym Ziborov",
  date: datetime.today(),
)

#align(center + horizon)[
  #text(size: 20pt)[Dit bestand hoef je niet te lezen, alle essentie ervan is in het 
    verslag opgenomen]
]

#set page(
    margin: (3cm),
    paper: "a4",
    numbering: "1"
)

#set par(
    justify: true
)

#set text(
  font: "New Computer Modern",
  size: 12pt,
  lang: "nl",
)

#let put_in_diagram_dim(x_name, y_name, h, w, ..plots) = {
    lq.diagram(
        height: h,
        width: w,
        xlabel: x_name,
        ylabel: y_name,
        ..plots
    )
}

#let put_in_diagram(x_name, y_name, ..plots) = {
    lq.diagram(
        height: 5cm,
        width: 8cm,
        xlabel: x_name,
        ylabel: y_name,
        ..plots
    )
}

#let draw_plot(file, column1, column2, color) = {
    let plot = lq.load-txt(read(file), delimiter: ",", header: true)
    let plotx = plot.at(column1)
    let ploty = plot.at(column2)


    lq.plot(plotx, ploty, color: color)
}

// Table of contents

#outline()

#pagebreak()

#set heading(numbering: "1.1")
#show heading: it => block[
    #counter(heading).display()
    #h(1em, weak: true)
    #underline(it.body)
    #v(0.5cm)
]

= Algemeen

In deze deel bespreek ik op welke manier ik de trees benchmark. De benchmarkfuncties
kun je terugvinden in `src/oplossing/benchmark/BenchmarkExecuter.java`. Voor het meten van de uitvoeringstijd gebruik ik
het verschil in tijd (in milliseconden) tussen het moment na de uitvoering van een reeks bewerkingen en het moment ervoor.
Voor het meten van het geheugen roep ik vóór het meten `System.gc()` aan, 
wat ervoor zorgt dat de JVM de garbage collector uitvoert.
Vervolgens meet ik het totale heapgeheugen gebruik vóór en na de reeks bewerkingen met `rt.totalMemory() - rt.freeMemory()`
en sla ik het verschil in gebruik op in kilobytes.  
Om de benchmarks iets nauwkeuriger te maken, voer ik elke test van een bepaalde grootte meestal meerdere keren uit
en neem ik het gemiddelde van de uitvoeringstijd of het geheugengebruik. Daarbij wordt voor elke uitvoering
dezelfde sampler gebruikt, maar een verschillende sample.

Wanneer we twee tree-implementaties vergelijken, worden voor de tests verschillende samplers gebruikt van dezelfde grootte.  
Dit is aanvaardbaar, aangezien een sampler willekeurige getallen genereert. Als we de test voldoende vaak uitvoeren
met een voldoende grote elementgrootte, verkrijgen we voor beide bomen een eerlijke vergelijking.  
Het is immers vrijwel onmogelijk dat bijvoorbeeld bij tien uitvoeringen van een test met grootte 100.000,
waarbij de elementen willekeurig worden gegenereerd, telkens exact die elementen worden gekozen
die op de maximale diepte van de boom moeten worden toegevoegd.

De vergelijkingsomstandigheden zijn eerlijk, omdat de elementen die moeten worden toegevoegd altijd willekeurig
worden gegenereerd met `Sampler`, en omdat voor beide trees evenveel toppen worden toegevoegd vanuit een identieke
begintoestand (beide bomen leeg of niet leeg).

Ook te vermelden dat ik hier enkel de setting bespreek en resultaten toon.  
Conclusies op basis van de resultaten zijn getrokken in het verslag.

== Gemiddelde benchmark

Vaak heb ik het over een gemiddelde benchmark, en om niet elke keer opnieuw te moeten uitleggen  
wat dat precies inhoudt, doe ik dat hier één keer. Deze benchmark is terug te vinden in  
`src/benchmark/AverageBenchmark.java` en verloopt op de volgende manier:

1. Een aantal toppen wordt toegevoegd.  
2. Een aantal toppen wordt gezocht.  
3. Een aantal toppen wordt verwijderd.  
4. De bovenstaande stappen worden herhaald.

#pagebreak()

= Semi-splay

== Semi-splay met bijhouden van ouder in vergelijking met semi-splay met stack

Deze benchmark vergelijkt twee implementaties van semi-splay: 
1. Elke bezochte top bij een bewerking wordt opgeslagen in een stapel, 
 die vervolgens wordt gebruikt voor de semi-splay-operatie.
2. Bij elke nood de ouder wordt bijgehouden om op die manier het semi-splay-pad op te bouwen.

We doen een benchmark waarbij we een aantal elementen van groottes van 100.000 tot 500.000 met een stap van 100.000 
toevoegen aan een initieel lege boom.
Na elke toevoeging wordt de semi-splay-operatie uitgevoerd (indien de diepte groter is dan 1), waarna we het gemiddelde
nemen van de uitvoeringstijden van de tien uitvoeringen.  
Zo is bijvoorbeeld de gemiddelde uitvoeringstijd ongeveer 200 ms bij het toevoegen van 200.000 toppen aan een initieel lege semi-splay-boom geïmplementeerd met een stack. Resultaten zijn te zien op @figure6.

#let semiSplayStackVsParent = figure(
    caption: [Semi-splay met bijhouden van ouder in blauw, semi-splay met stapel in rood.],
    grid(
        put_in_diagram(
            [Aantal toppen toegevoegd], 
            [Gemiddelde totale tijd (ms)],
            draw_plot(
            "addSemisplayOuderTime/avgAdd.csv",
            "size",
            "avgTime(ms)",
            blue
            ),
            draw_plot(
            "addSemisplayStackTime/avgAdd.csv",
            "size",
            "avgTime(ms)",
            red
            ) 
        ),
        put_in_diagram(
            [Aantal toppen toegevoegd], 
            [Gemiddeld geheugenverbruik (kB)],
            draw_plot(
            "addSemisplayOuderMemory/avgAdd.csv",
            "size",
            "memory(kB)",
            blue
            ),
            draw_plot(
            "addSemisplayStackMemory/avgAdd.csv",
            "size",
            "memory(kB)",
            red
            ) 
        )
    )
);

#semiSplayStackVsParent<figure6>

#pagebreak()

= Treap

Hier benchmark ik grensen voor generatie van willekeurige prioriteit in `Treap` op uitvoeringssnelheid.
Warbij grensen van 10.000, 100.000, 1.000.000, 10.000.000 en `Integer.MAX_VALUE`. Ik gebruik hiervoor gemiddlede benchmark.
Resultaten zijn terug te vinden in @treap_bound.

#let treapBound = figure(
    caption: 
    [
        Bovengrens 10.000 in paars, 100.000 in blauw, 1.000.000 in groen, 10.000.000 in geel,
        `Integer.MAX_VALUE` in rood.
    ],
    grid(
        put_in_diagram(
            [Aantal toppen in de boom op einde], 
            [Gemiddelde totale tijd (ms)],
            draw_plot(
                "treapBoundTime/avgBound=10000.csv",
                "size",
                "avgTime(ms)",
                purple
            ),
            draw_plot(
                "treapBoundTime/avgBound=100000.csv",
                "size",
                "avgTime(ms)",
                blue
            ),
            draw_plot(
                "treapBoundTime/avgBound=1000000.csv",
                "size",
                "avgTime(ms)",
                green
            ),
            draw_plot(
                "treapBoundTime/avgBound=10000000.csv",
                "size",
                "avgTime(ms)",
                yellow
            ),
            draw_plot(
                "treapBoundTime/avgBound=2147483647.csv",
                "size",
                "avgTime(ms)",
                red
            )
        ),
        // v(10pt),
        // put_in_diagram(
        //     [Aantal toppen in de boom op einde], 
        //     [Aantal bezochte toppen],
        //     draw_plot(
        //         "treapBoundVisits/avgBound=10000.csv",
        //         "size",
        //         "visits",
        //         purple
        //     ),
        //     draw_plot(
        //         "treapBoundVisits/avgBound=100000.csv",
        //         "size",
        //         "visits",
        //         blue
        //     ),
        //     draw_plot(
        //         "treapBoundVisits/avgBound=1000000.csv",
        //         "size",
        //         "visits",
        //         green
        //     ),
        //     draw_plot(
        //         "treapBoundVisits/avgBound=10000000.csv",
        //         "size",
        //         "visits",
        //         yellow
        //     ),
        //     draw_plot(
        //         "treapBoundVisits/avgBound=2147483647.csv",
        //         "size",
        //         "visits",
        //         red
        //     )
        // )
    )
);
#treapBound <treap_bound>

#pagebreak()

= MyFrequencyTreap

Exact dezelfde benchmark als voor Treap voeren wij ook uit voor `MyFrequencyTreap`.

#let myFrequencyTreapBound = figure(
    caption: 
    [
        Bovengrens 10.000 in paars, 100.000 in blauw, 1.000.000 in groen, 10.000.000 in geel,
        `Integer.MAX_VALUE` in rood.
    ],
    grid(
        put_in_diagram(
            [Aantal toppen in de boom op einde], 
            [Gemiddelde totale tijd (ms)],
            draw_plot(
                "myFrequencyTreapBoundTime/avgBound=10000.csv",
                "size",
                "avgTime(ms)",
                purple
            ),
            draw_plot(
                "myFrequencyTreapBoundTime/avgBound=100000.csv",
                "size",
                "avgTime(ms)",
                blue
            ),
            draw_plot(
                "myFrequencyTreapBoundTime/avgBound=1000000.csv",
                "size",
                "avgTime(ms)",
                green
            ),
            draw_plot(
                "myFrequencyTreapBoundTime/avgBound=10000000.csv",
                "size",
                "avgTime(ms)",
                yellow
            ),
            draw_plot(
                "myFrequencyTreapBoundTime/avgBound=2147483647.csv",
                "size",
                "avgTime(ms)",
                red
            )
        ),
    )
)
#myFrequencyTreapBound

#pagebreak()

= MyFrequencyTreap en LineairFrequencyTreap

In de volgende benchmark vergelijken wij `MyFrequencyTreap` en `LineairFrequencyTreap` in een scenario waar `LineairFrequencyTreap`
slechter zou moeten presteren. De benchmark is uitgevoerd voor groottes van  
10.000.000 tot 20.000.000 met een stap van 1.000.000, waarbij wij het aantal bezochte toppen meten.

De benchmark is terug te vinden in `src/benchmark/MyFrequencyTreapBenchmark.java` en verloopt
op de volgende manier (waar $n$ het totaal aantal elementen is):

1. Voeg $n$ elementen toe
2. Start meting.
3. Kies een willekeurig blad.
4. Zoek hetzelfde element 2.000 keer.
5. Stop meting.

Het is belangrijk te vermelden dat, ongeacht welk blad willekeurig wordt gekozen, dit in beide bomen  
hetzelfde blad is. Bovendien hebben beide bomen vóór het starten van het zoeken dezelfde structuur.  
Dit is bereikt door dezelfde seed en bovengrens voor de generatie in beide bomen te gebruiken,  
en dezelfde seed voor beide benchmarks.

#pagebreak()

// #let myFrequencyVslineairFrequency = figure(
//     caption: [LineairFrequencyTreap in rood, MyFrequencyTreap in blauw.],
//     grid(
//         columns: 2,
//         gutter: 30pt,
//         scale( 80%,
//         put_in_diagram(
//     [Aantal toppen in boom], 
//             [Aantal bezochte toppen],
//             draw_plot(
//                 "myAndlineairDiffVisits/myFrequencyTreapVisits.csv",
//                 "size",
//                 "visits",
//                 blue
//             ),
//             draw_plot(
//                 "myAndlineairDiffVisits/lineairFrequencyTreapVisits.csv",
//                 "size",
//                 "visits",
//                 red
//             )
//         )),
//         scale( 80%,
//         put_in_diagram(
//     [Aantal toppen in boom], 
//             [Geheugenverbruik (kB)],
//             draw_plot(
//                 "lineairFrequencyMemory/avg.csv",
//                 "size",
//                 "avgMemory(kB)",
//                 red
//             ),
//             draw_plot(
//                 "myFrequencyMemory/avg.csv",
//                 "size",
//                 "avgMemory(kB)",
//                 blue
//             )
//         ))
//     )
// );
#let myFrequencyVsLineairMemory = figure(
    caption: [Geheugenverbruik. LineairFrequencyTreap in rood, MyFrequencyTreap in blauw.],
    put_in_diagram(
[Aantal toppen in boom], 
        [Geheugenverbruik (kB)],
        draw_plot(
            "lineairFrequencyMemory/avg.csv",
            "size",
            "avgMemory(kB)",
            red
        ),
        draw_plot(
            "myFrequencyMemory/avg.csv",
            "size",
            "avgMemory(kB)",
            blue
        )
    )
);
#let myFrequencyVsLineairVisits = figure(
    caption: [Aantal bezoeken. LineairFrequencyTreap in rood, MyFrequencyTreap in blauw.],
      put_in_diagram(
  [Aantal toppen in boom], 
          [Aantal bezochte toppen],
          draw_plot(
              "myAndlineairDiffVisits/myFrequencyTreapVisits.csv",
              "size",
              "visits",
              blue
          ),
          draw_plot(
              "myAndlineairDiffVisits/lineairFrequencyTreapVisits.csv",
              "size",
              "visits",
              red
          )
      )
);

#let myFrequencyVslineairFrequency = figure(
    caption: [LineairFrequencyTreap in rood, MyFrequencyTreap in blauw.],
    grid(
        put_in_diagram(
    [Aantal toppen in boom], 
            [Aantal bezochte toppen],
            draw_plot(
                "myAndlineairDiffVisits/myFrequencyTreapVisits.csv",
                "size",
                "visits",
                blue
            ),
            draw_plot(
                "myAndlineairDiffVisits/lineairFrequencyTreapVisits.csv",
                "size",
                "visits",
                red
            )
        ),
        put_in_diagram(
    [Aantal toppen in boom], 
            [Gemiddeld geheugenverbruik (kB)],
            draw_plot(
                "lineairFrequencyMemory/avg.csv",
                "size",
                "avgMemory(kB)",
                red
            ),
            draw_plot(
                "myFrequencyMemory/avg.csv",
                "size",
                "avgMemory(kB)",
                blue
            )
        )
    )
);

// #let myFrequencyVslineairFrequency = figure(
//     caption: [LineairFrequencyTreap in rood, MyFrequencyTreap in blauw.],
//     grid(
//         columns: 2,
//         put_in_diagram_dim(
//     [Aantal toppen in boom], 
//             [Aantal bezochte toppen],
//             5cm,
//             6cm,
//             draw_plot(
//                 "myAndlineairDiffVisits/myFrequencyTreapVisits.csv",
//                 "size",
//                 "visits",
//                 blue,
//             ),
//             draw_plot(
//                 "myAndlineairDiffVisits/lineairFrequencyTreapVisits.csv",
//                 "size",
//                 "visits",
//                 red
//             )
//         ),
//         put_in_diagram_dim(
//     [Aantal toppen in boom], 
//             [Geheugenverbruik (kB)],
//             5cm,
//             6cm,
//             draw_plot(
//                 "lineairFrequencyMemory/avg.csv",
//                 "size",
//                 "avgMemory(kB)",
//                 red
//             ),
//             draw_plot(
//                 "myFrequencyMemory/avg.csv",
//                 "size",
//                 "avgMemory(kB)",
//                 blue
//             )
//         )
//     )
// );

#myFrequencyVslineairFrequency<frequencyBoth>

#pagebreak()

= MyTreap, Treap, SemiSplayTree

#underline("Perfect scenario voor MyTreap")

Hier benchmark ik `Treap`, `MyTreap` en `SemiSplayTree` in een perfect scenario voor `MyTreap` en `SemiSplayTree`.  
Benchmarks zijn uitgevoerd voor groottes van 100.000 tot 1.000.000.
Voor elke grootte wordt de benchmark drie keer uitgevoerd op verschillende bomen  
(en lijsten van bladen), waarna het gemiddelde van de drie resultaten wordt genomen.  
De benchmark is terug te vinden in  
`src/benchmark/MyTreapBenchmark.java` en verloopt op de volgende manier:

1. Bouw een boom op van de gegeven grootte (zie hierboven voor de groottes).  
2. Maak een lijst van toppen (meestal bladen) waarbij ook hun diepte wordt bijgehouden.  
3. Start de tijdsmeting.  
4. Zoek elke top uit de lijst `diepte + c` keer, waarbij `c` een fractie is van het totale aantal elementen.  
5. Stop de tijdsmeting.

De resultaten van deze benchmark zijn te zien in @perfectMyTreap.

#underline("Gemiddelde scenario")

Hier benchmark ik `Treap`, `MyTreap` en `SemiSplayTree` in een gemiddeld scenario.  
De resultaten van deze benchmark zijn te zien in @avgMyTreap.

#pagebreak()

#let perfectMyTreap = figure(
    caption: [Ideale scenario. MyTreap in blauw, Treap in rood, SemiSplayTree in paars, LineairFrequencyTreap in groen en MyFrequencyTreap in geel.],
    grid(
        put_in_diagram(
            [Aantal toppen in boom op einde], 
            [Gemiddelde totale tijd (ms)],
            draw_plot(
            "perfectMyTreapTime/avg.csv",
            "size",
            "avgTime(ms)",
            blue
            ),
            draw_plot(
            "perfectTreapTime/avg.csv",
            "size",
            "avgTime(ms)",
            red
            ),
            draw_plot(
            "perfectSemiSplayTime/avg.csv",
            "size",
            "avgTime(ms)",
            purple
            ),
            draw_plot(
            "perfectLineairFrequencyTime/avg.csv",
            "size",
            "avgTime(ms)",
            green
            ),
            draw_plot(
            "perfectMyFrequencyTime/avg.csv",
            "size",
            "avgTime(ms)",
            yellow
            )
        )

    )
);

#perfectMyTreap<perfectMyTreap>

#let averageMyTreap = figure(
    caption: [Gemiddelde scenario. MyTreap in blauw, Treap in rood, SemiSplayTree in paars,
LineairFrequencyTreap in groen en MyFrequencyTreap in geel.],
    grid(
        put_in_diagram(
            [Aantal toppen in boom op einde], 
            [Gemiddelde totale tijd (ms)],
            draw_plot(
            "averageMyTreapTime/avg.csv",
            "size",
            "avgTime(ms)",
            blue
            ),
            draw_plot(
            "averageTreapTime/avg.csv",
            "size",
            "avgTime(ms)",
            red
            ),
            draw_plot(
            "averageSemiSplayTime/avg.csv",
            "size",
            "avgTime(ms)",
            purple
            ),
            draw_plot(
            "averageLineairFrequencyTime/avg.csv",
            "size",
            "avgTime(ms)",
            green
            ),
            draw_plot(
            "averageMyFrequencyTime/avg.csv",
            "size",
            "avgTime(ms)",
            yellow
            )

        )
    )
);
#averageMyTreap<avgMyTreap>
