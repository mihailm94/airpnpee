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

Um die Daten über bestehenden öffentlichen Toiletten zu sammeln, wurde der Schnappschuss der OpenStreetMap Datenbank - Planet.osm benutzt. Planet.osm ist die Sammlung von Nodes, Ways, Relationen und Änderungssätze in der Datenbank, inklusive aller Tags. Planet.osm ist eine XML-Datei im .osm-Format, was ein OpenStreetMap spezifisches Dateiformat ist. Die Datei enthällt alle vorhandenen Daten in OpenStreetMap und beschreibt somit die komplette Erde. Mittlerweile ist die Planet.osm Datei 803GB, was für unser Projekt nicht gut geeignet ist. Kleinere Auszüge der Datenbank, die von verschiedenene Anbietern ebenfalls im .osm-Format zur Verfügung gestellt wurden, haben wir für unsere Zwecke benutzt. 

Von dem Geofabrik-Downloadserver ([https://download.geofabrik.de]) wurde der Auszug von Berlin heruntergeladen.  Geofabrik bietet diese Datei in dem `.osm.pbf` Dateiformat. Das erforderte erstmal eine Konvertierung von `.osm.pbf` zu `osm`, und somit das Dekomprimieren der Datei. Um das gewährzuleisten wurde ein Werkzeug namens **osmconvert** benutzt. Die Eingabe erfolgt durch die Kommandozeile. Um unsere Datei zu konvertieren wurde folgendes Kommando benutzt:
```
bzcat berlin-latest.osm.pbf | ./osmconvert - -o=berlin-latest.osm
```

Die Dateigröße des ursprunglichen Auszüges betrug ~51MB. Nachdem Sie dekomprimiert wurde, standen 773MB unfiltrierten Nodes, Ways, Relationen und Änderungssätze zur Verfügung. Um nur die für uns nützliche Informationen herauszufiltern wurde das Kommandozeilentool **osmfilter** benutzt. Das Tool ermöglicht die Definition von verschiedenen Arten von Filtern, mit denen OSM-Objekte (Nodes, Punkte) extrahiert werden können. Um Objekte einse bestimmten Typs zu behalten wurde das folgende Kommando benutzt:
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





