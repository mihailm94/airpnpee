# AirPee&Pee


Projekt Softwareentwicklung

Elnaz Bezdoodeh

Osama Alabaji

Leonard Berresheim

Mihail Milchev



Inhaltsverzeichnis

1. [Einführung](#introduction)
2. [Implementierung der Datenbank in Firebase](#db)
3. 



## Einführung <a name="introduction"></a>



## Implementierung der Datenbank in Firebase <a name="db"></a>

Um die Daten über bestehenden öffentlichen Toiletten zu sammeln, wurden einen Schnappschuss der OpenStreetMap Datenbank - die **Planet.osm** Datei, benutzt. Planet.osm ist die Sammlung von Nodes, Ways, Relationen und Änderungssätze in der Datenbank, inklusive aller Tags. Planet.osm ist eine XML-Datei im .osm-Format, was ein OpenStreetMap spezifisches Dateiformat ist. Die Datei enthällt alle vorhandenen Daten in OpenStreetMap und beschreibt somit die komplette Erde. Mittlerweile ist die Planet.osm Datei 803GB, was für unser Projekt nicht gut geeignet ist. Kleinere Auszüge der Datenbank, die von verschiedenene Anbietern ebenfalls im .osm-Format zur Verfügung gestellt wurden, haben wir für unsere Zwecke benutzt. 

Von dem Geofabrik-Downloadserver (https://download.geofabrik.de) wurde der Auszug von Berlin heruntergeladen.  Geofabrik bietet diese Datei in dem `.osm.pbf` Dateiformat. Das erforderte erstmal eine Konvertierung von `.osm.pbf` zu `osm`, und somit das Dekomprimieren der Datei. Um das gewährzuleisten wurde ein Werkzeug namens **osmconvert** benutzt. Die Eingabe erfolgt durch die Kommandozeile. Um unsere Datei zu konvertieren wurde folgendes Kommando benutzt:
```
bzcat berlin-latest.osm.pbf | ./osmconvert - -o=berlin-latest.osm
```

Die Dateigröße des ursprunglichen Auszüges betrug ~51MB. Nachdem er dekomprimiert wurde, standen 773MB unfiltrierten Nodes, Ways, Relationen und Änderungssätze zur Verfügung. Um nur die für uns nützliche Informationen herauszufiltern wurde das Kommandozeilentool **osmfilter** benutzt. Das Tool ermöglicht die Definition von verschiedenen Arten von Filtern, mit denen OSM-Objekte (Nodes, Punkte) extrahiert werden können. Um Objekte eines bestimmten Typen zu behalten, wurde das folgende Kommando benutzt:
```
./osmfilter berlin-latest.osm --keep-nodes"amenity=toilets or building=toilets" -o=berlin_toilets.osm
```

Nach ein paar Minuten wurde die Ausgangsdatei generiert, deren Größe nur 302KB betrug. Die `.osm` Datei ist ein XML-Datei, die geographische Informationen in einer strukturierten Art und Weise beinhaltet. Die Allgemeine Struktur eines Beispielnodes unseres Datensatzes sieht so aus:
```
<node id="29040668" lat="52.5250054" lon="13.335496" version="16">
		<tag k="fee" v="yes"/>
		<tag k="name" v="City Toilette"/>
		<tag k="amenity" v="toilets"/>
		<tag k="operator" v="Wall"/>
		<tag k="addr:city" v="Berlin"/>
		<tag k="wheelchair" v="yes"/>
		<tag k="addr:street" v="Alt-Moabit"/>
		<tag k="addr:country" v="DE"/>
		<tag k="addr:postcode" v="10555"/>
		<tag k="opening_hours" v="24/7"/>
		<tag k="addr:housenumber" v="81"/>
		<tag k="toilets:wheelchair" v="yes"/>
	</node>
```

Die bereitgestellten Tools zur Verarbeitung von OpenStreetMap spezifischen Dateiformaten (`.osm, .osc, .o5m, .o5c, .osm.pbf`) haben sich als sehr nützlich und bedienerfreundlich bewiesen, und aus diesem Grund war die Konvertierung vom `.osm` zum `.xml` Format durchgeführt, erst nachdem alle unbrauchbaren Dateien rausgefiltert wurden.

Als Datenbank haben wir für unser Pojekt die Firebase-Platform gewählt. Das Einbeziehen von Firebase in Android-Studio is einfach, da diese Funktionalität als Modul in dem Softwarepaket schon zur Verfügung steht, nach entsprechender Aktivierung in den Einstellungen.
Firebase ist eine Realtime NoSQL Datenbank. Realtime bedeutet in diesem Fall, dass alle Endbenutzer (Devices) eine und dieselbe Datenbank-Instanz gemeinsam benutzen und asynchron den neusten Stand der Daten herunterladen. In der Datenbank werden die Daten in dem JavaScript Object Notation (JSON) Format gespeichert.

Die nächste Aufgabe war die Konvertierung von `.xml` zu `.json`. Nach Recherche und Ausprobieren von mehreren Programmen die frei online zugänglich sind, kamen zu der Überzeugung, dass wenn wir eine dauernd stabile Ausgangsstruktur erzielen wollen, die an unseren Eingabedaten angepasst ist, mussten wir unseren eigenen Parser entwickeln.

Das eigentliche Problem bestand darin , dass alle Atributen der `.osm` Datei die Struktur 
`<tag k="--Bezeichnung--" v="--Wert--" />`
trugen, was bei der Konvertierung zu JSON folgendes ergab: 

```
"tag": {
        "-k": "--Bezeichnung--",
        "-v": "--Wert--"
      }
```
Das haben wir als ungünstig betrachtet und haben mit der Entwicklung unseres Parsers angefangen. 

Der Parser wurde in Python umgesetzt, da viele Bibliotheken zur Verarbeitung von `.xml`Dateien zur Verfügung stehen. Die Dokumentation von `xml.etree.ElementTree` (und die C Implementation davon `xml.etree.cElementTree` ) war sehr ausführlich und wir konnten schnell uns damit vertraut machen. `ElementTree` ist vielleicht die bekannteste API zur Verarbeitung von `.xml` Dateien.

```python
import xml.etree.cElementTree as et

parsed_xml = et.parse("berlin_toilets.xml")
```



Um `.xml`Dateien zu erfassen, ist die `xml.etree.ElementTree.parse()` vorhanden. Somit kann die weitere Bearbeitung und Manipulation der `.xml`Datei fortgesetzt werden.

Der Parser legt eine Datei mit der `.json ` Erweiterung im Schreibemodus an, und schreibt in dieser Datei mittels der `write()` Funktion.

```pyth
f = file("berlin_toilets.json", "w")
	f.write("{\n")
	f.write("\t\"toilet\":{\n")
```

Eine Iterationsstruktur wurde anschließend benutzt um durch den Datensatz zu iterieren und entsprechend eine Liste von `Toilet` Objekten zu erstellen.

```python
for osm in parsed_xml.iter('node'):
		f.write("\t\t\"" + osm.attrib['id'] + "\": {\n")
		.
        .
        .
        for node in osm.iter('tag'):
			if(node.attrib['k'] == "addr:postcode"):
				f.write("\t\t\t\"plz\": \""+ node.attrib['v'] + "\",\n")
			if(node.attrib['k'] == "addr:street"):
				f.write("\t\t\t\"street\": \"")
				f.write(node.attrib['v'].encode('utf-8'))
				f.write("\",\n")
         	.
            .
            .
            if(node.attrib['k'] == "name"):
				f.write("\t\t\t\"name\": \""+ node.attrib['v'].encode('utf-8') + "\",\n")
            .
            .
            .
            
        .
        .
        .
        f.write("}")

		f.close()
```

Jedes Toilet-Objekt besitzt ein einzigartiges 'id', was wir als Primärschlüssel betrachten können. 



Die allgemeine Struktur eines Toiletten-Objektes hat die folgende Struktur:

```python
{
  "comments" : {
    "commentID_1" : {
      "commentText" : "this toilet is the most amazing toilet ever",
      "userID" : "dummy"
    }
  },
  "description" : "this toilet will welcome you warmly even on your worst days",
  "fee" : "yes",
  "isPrivate" : "False",
  "location" : {
    "lat" : 52.5250054,
    "lon" : 13.335496
  },
  "name" : "City Toilette",
  "opening_hours" : "24/7",
  "photoUrl" : "gs://airpeepee.appspot.com/toilet.png",
  "plz" : "10555",
  "rating" : {
    "ratID_1" : {
      "userID" : "dummy",
      "userRating" : 1
    }
  },
  "ratingTotal" : 5,
  "street" : "Alt-Moabit",
  "street_number" : "81",
  "wheelchair" : "yes"
}

```

Nachdem wir erfolgreich die ~1800 Objekten zu `.json` Konvertiert hatten, konnten wir sie in der Datenbank hochladen. Das war besonders einfach, da Firebase über die Funktion **import JSON file** verfügt. 






```
# Table of contents
1. [Introduction](#introduction)
2. [Some paragraph](#paragraph1)
    1. [Sub paragraph](#subparagraph1)
3. [Another paragraph](#paragraph2)

## This is the introduction <a name="introduction"></a>
Some introduction text, formatted in heading 2 style

## Some paragraph <a name="paragraph1"></a>
The first paragraph text

### Sub paragraph <a name="subparagraph1"></a>
This is a sub paragraph, formatted in heading 3 style

## Another paragraph <a name="paragraph2"></a>
The second paragraph text
```





