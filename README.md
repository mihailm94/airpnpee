<img height="150px" width="250px" src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Logo_HTW_Berlin.svg/2000px-Logo_HTW_Berlin.svg.png"/>

<div align="center">
	<center>
	
# AirPee&Pee 

### Projekt Softwareentwicklung - WiSe 18/19 

HTW Berlin



##### [Elnaz Bezdoodeh](https://github.com/ElnazBezdoodeh) 

##### [Osama Alabaji](https://github.com/osamax2) 

##### [Leonard Berresheim](https://github.com/leoEAB) 

##### [Mihail Milchev](https://github.com/mihailm94) 

</center>
</div>




#### Inhaltsverzeichnis

1. [Einführung](#introduction)
	1. [Muss/Soll-Kriterien](#mussSoll)
	2. [Kann-Kriterien](#kann)
	3. [UML](#uml)
2. [Implementierung der Datenbank in Firebase](#dbFirebase)
3. [Implementierung der Datenbank in Android Studio](#dbJava)
   1. [build.gradle](#build.gradle)
   2. [db.java](#db.java)
   3. [Toilet.java](#Toilet.java)
      1. [Comment.java](#Comment&Rating)
      2. [Rating.java](#Comment&Rating)
4. [Log-in using firebase](#login)
5. [Implementierung der Karte](#karte)
	1. [Google Map Api](#api)
	2. [Location permissions](#location)
	3. [Data anzeigen auf Karte](#DataAufKarte)
		1. [Marker als Toilet](#Marker)
		2. [Toilet Informationen](#ToiletInformationen)
	4. [Places API](#Suchfunktion)
		1. [API-key](#apikey)
		2. [Place Autocomplete Request](#placeautocomplete)
		3. [SearchAcivity.java](#searchactivity)
	5. [Directions API](#Richtungsfunktion)
		1. [Server-Key](#serverkey)
		2. [Direction Request](#directionrequest)
		3. [Akexorcist Library](#akexorcistlibrary)
		4. [DirectionAcivity.java](#directionactivity)
6. [Fehlerhandler](#error)
	1. [Netzwerkfehler](#Netzwerkfehler)
7.[UI Design](#UI)
	1.[Visionäre Skizze](#skizze)
	2.[Im Designprozess](#design)
	3.[Endprodukt](#endp)
8. [Fazit](#fazit)

## Einführung <a name="introduction"></a>

Die AirPee&Pee App ist das Ergebnis unseres Softwareprojektes für den Kurs Softwareentwicklung im Wintersemester 18/19 an der HTW Berlin. Unser Team bestand aus 4 Personen. Jeder von uns hatte sich mit einer Teilaufgabe beschäftigt und im folgenden dokumentiert.

AirPee&Pee ist eine Android App die eine Dienstleistung repräsentiert. Alle öffentlich zugänglichen Toiletten in der Nähe des Benutzers werden angezeigt, inklusiv Preis, Öffnunszeiten, ob sie für Behinderte zugänglich sind, sowie Ratings und Kommentare für die jeweilige Toilette.

Um die Aufgaben richtig unter unserem Team zu verteilen, haben wir die Projekt-Management Kenntnisse von dem Modul Computer Systems Engineering Projekt benutzt und den Entwicklungsvorgang in 5 Phasen unterteilt:

```
1.Konzeptphase
2.Definitionsphase
3.Entwurfsphase
4.Fertigungsphase
5.Wartungsphase
```

In der Konzeptphase haben wir uns mit der Projektidee und Lastenheft beschäftigt.
In der Definitionsphase wurde das Pflichtenheft erstellt.
Die Entwurfsphase ist durch die Use Cases, ERD und Klassendiagramm gekennzeichnet.
Die Fertigungsphase ist die Entwicklung.
In der Wartungsphase haben wir uns damit beschäftigt den Code zu "reinigen", sowie die Wiki des Projektes zu vervollständigen.



Die App muss die folgenden Muss/Soll-Kriterien erfolgreich erfüllen. Nicht alle Kann-Kriterien sind erfüllt. 

#### Muss/Soll-Kriterien <a name="mussSoll"></a>
```
A.1.1 Die AirPnP-Funktionen sollen in einer mobilen Android-Applikation umgesetzt werden.
A.1.2 Die AirPnP-App soll über ein benutzerfreundliches GUI (Graphical User Interface) bedient werden.
A.1.3 Die AirPnP-App soll auf Informationen über öffentliche Toiletten in Berlin zugreifen.
A.1.4 Die AirPnP-App soll das Erstellen eines User-Accounts erzwingen.
A.1.5 Die AirPnP-App soll auf Standort zugreifen können.
A.1.6 Die AirPnP-App soll dem User öffentlische und private Toiletten in einem Umkreis(TBD) um seinen Standort auf einer Karte 
anzeigen.
A.1.7 Die AirPnP-App soll dem User öffentliche und private Toiletten in einem Umkreis(TBD) um einen benutzerdefinierten Standort auf einer Karte anzeigen.
A.1.8 Die AirPnP-App soll dem User die Funktion anbieten eine Toilette zu bewerten.
A.1.9 Die AirPnP-App soll das Anlegen eines Anbieter-Account anbieten.
A.1.10 Die AirPnP-App soll dem Anbieter-User das Hinzufügen einer privaten Toilette ermöglichen.
```

#### Kann-Kriterien <a name="kann"></a>

```
A.2.1 Die AirPnP-App kann das Hinzufügen von öffentlichen Toiletten durch einen User anbieten.
A.2.2 Die AirPnP-App soll eine öffentliche Toilette automatisch entfernen wenn sie mehrmals (TBD) gemeldet worden ist.
```

Die Definitionsphase besteht aus dem Pflichtenheft. Die Kriterien in dem Pflichtenheft beschreiben sehr einfach und strukturiert wie das App-Backend funktioniert.

```
B.1.1 
	B.1.1.1 Die AirPnP-App soll eine auf Android-Betriebssystem basierte mobile Anwendung sein.
	B.1.1.2 Die AirPnP-App soll laufen auf allen offiziellen Android-Versionen (Android 9.0 und früher bis 5.0)
	B.1.1.3 Für die Entwicklung der AirPnP-App soll Android-Studio benutzt werden.
	B.1.1.4 Für die Entwicklung der AirPnP-App soll Java benutzt werden.


B.1.2
	B.1.2.1 Die AirPnP-App-GUI soll die Material-Design-Libraries benutzen.
	B.1.2.2 Die AirPnP-App-GUI soll eine SignUp-Seite haben.
		B.1.2.2.1 Die AirPnP-App-GUI-SignUp-Seite soll dem User die Möglichkeit geben sich zu Registrieren über Google, Facebook oder Email.
		B.1.2.2.2 Bei Reigistrieren mit Email soll AirPnP-App den User nach einem und Passwort fragen.
		B.1.2.2.3 Bei Reigistrieren mit Google oder Facebook soll die AirPnP-App das entsprechende API aufrufen.
	B.1.2.3 Die AirPnP-App-GUI soll eine Login-Seite haben.
		B.1.2.3.1 Die AirPnP-App-Gui-Login-Seite soll dem User die Möglichkeit geben sich anzumelden über Google, Facebook oder Email.
	B.1.2.4 Die AirPnP-App-GUI soll eine Hauptseite haben.
		B.1.2.4.1 Die AirPnP-App-Gui-Hauptseite soll in einem GoogleMaps-Fenster die öffentlichen und privaten Toiletten in einem Umkreis um den gewählten Standort anzeigen.
		B.1.2.4.2 Die AirPnP-App-Gui-Hauptseite soll Navigations-Buttons besitzen
			B.1.2.4.2.1 Ein Menu-Button der das Menu aufruft.
				B.1.2.4.2.1.1 Das Menu zeigt folgende Optionen an: Profil, Settings, Switch, Abmelden, About.
			B.1.2.4.2.2 Ein Search-Feld mit Search-Button.
		B.1.2.4.3 Bei Klicken auf einen Toilette-Marker soll die AirPnP-App auf der Hauptseite ein Card anzeigen mit Informationen zu der ausgewählten Toilette.
		B.1.2.4.4 Bei Klicken auf das Information-Card soll die AirPnP-App die Toilette-Profil-Seite öffnen.
	B.1.2.5 Die AirPnP-App soll zu jeder Toilette eine Toilette-Pofil-Seite haben (TBD - siehe Dateien (bald)).
		B.1.2.5.1 Die Die AirPnP-App-Gui-Toilet-Profil-Seite soll einen Direction Button besitzten.
		B.1.2.5.2 Bei klicken auf den Direction Button, wird eine Seite aufgerufen mit Google-Navigation zu der ausgewählten Toilette.
	B.1.2.6 Nach einen bestimmten Zeitraum(TBD) nach erfolgreischer Beendigung soll ein Bewertungs-PopUp aufpoppen.
		B.1.2.6.1 Der User soll in dem Bewertungs-PopUp eine Bewertung (TBD) und Rezession abgeben können.


B.1.3
	B.1.3.1 Aus OpenStreet-Map API sollen die öffentlichen Toiletten in Berlin extrahiert werden und in einer zentralen Datenbank importiert werden.
	B.1.3.2 In der Datenbank sollen zu jeder Toilette informationen gespeichert sein zu Standort (Längengrad/Höhengrad), Preis, Bewertung, Accesibility, Öffnungszeiten. (TBC)


B.1.4 
	B.1.4.1 Bei erfolgreicher Registrierung werden die Benutzerdaten in einer Datenbank gespeichert.
		B.1.4.1.1 Benutzerdaten sind: Email, Nickname, Passwort (TBC)
	B.1.4.2 Der Zugriff auf die Funktionen der App sind erst nach Registrierung möglich.


B.1.5
	B.1.5.1 Die AirPnP-App soll Zugriff auf die GPS-Funktion der App haben.


B.1.6
	B.1.6.1 Die AirPnP-App soll die GPS-Daten (zzg. Umkreis) mit zentralen Datenbank abgleichen und entsprechend die Ergebnisse auf der GoogleMaps-Karte auf der Hauptseite Anzeigen.


B.1.7
	B.1.7.1 Die App soll dem eingebenen Standort GPS-Daten finden mit hilfe GoogleMaps API.
	B.1.7.2 Die AirPnP-App soll die GPS-Daten der eingebenen Adresse (zzg. Umkreis) mit zentralen Datenbank abgleichen und entsprechend die Ergebnisse auf der GoogleMaps-Karte auf der Hauptseite Anzeigen.


B.1.8
	B.1.8.1 Die App soll die Bewertung der User in Datenbank der User und der Toiletten gespeichert werden.


B.1.9
	B.1.9.1 Die App legt für Anbieter eine von der User-Tabelle getrennte Tabelle an.


B.1.10
	B.1.10.1 Beim Hinzufügen einer privaten Toilette durch einen Anbieter fügt die AirPnP-App die Information zu der Toilette in einer privaten-Toiletten-Tabelle hinzu.
```


## UML <a name="uml"></a>

1. Use Cases

![use cases](https://i.imgur.com/uPlLLtv.png)

2. ERD

![ERD](https://i.imgur.com/qoYTfPa.png)

3. Class Diagram

![Class Diagram](https://i.imgur.com/JJM3IWqr.jpg)

4. State Diagram
![airpp](https://user-images.githubusercontent.com/44113201/51066119-bb557480-1608-11e9-919c-f55dcd455529.jpg)



## Implementierung der Datenbank in Firebase <a name="dbFirebase"></a>

Um die Daten über bestehenden öffentlichen Toiletten zu sammeln, wurde einen Schnappschuss der OpenStreetMap Datenbank - die **Planet.osm** Datei, benutzt. Planet.osm ist die Sammlung von Nodes, Ways, Relationen und Änderungssätze als Datenbank, inklusive aller Tags. Planet.osm ist eine XML-Datei im .osm-Format, was ein OpenStreetMap spezifisches Dateiformat ist. Die Datei enthällt alle vorhandenen Daten in OpenStreetMap und beschreibt somit die komplette Erde. Mittlerweile ist die Planet.osm Datei 803GB, was für unser Projekt nicht gut geeignet ist. Kleinere Auszüge der Datenbank, die von verschiedenenen Anbietern ebenfalls im .osm-Format zur Verfügung gestellt wurden, haben wir für unseren Zweck benutzt.

Von dem Geofabrik-Downloadserver (https://download.geofabrik.de) wurde der Berlin-Auszug des Planet.osm-Schnappschusses heruntergeladen.  Geofabrik bietet diese Datei in dem `.osm.pbf` Dateiformat. Das erforderte erstmal eine Konvertierung von `.osm.pbf` zu `osm`, und somit das Dekomprimieren der Datei. Um das gewährzuleisten wurde ein Werkzeug namens **osmconvert** benutzt. Die Eingabe erfolgt durch die Kommandozeile. Um unsere Datei zu konvertieren wurde folgendes Kommando benutzt:
```
bzcat berlin-latest.osm.pbf | ./osmconvert - -o=berlin-latest.osm
```

Die Dateigröße des ursprunglichen Auszüges betrug ~51MB. Nachdem er dekomprimiert wurde, standen 773MB unfiltrierten Nodes, Ways, Relationen und Änderungssätze zur Verfügung. Um nur die für uns nützliche Informationen herauszufiltern wurde das Kommandozeilentool **osmfilter** benutzt. Das Tool ermöglicht die Definition von verschiedenen Arten von Filtern, mit denen OSM-Objekte (Nodes, Punkte) extrahiert werden können. Um Objekte eines bestimmten Typen zu behalten, wurde das folgende Kommando benutzt:
```
./osmfilter berlin-latest.osm --keep-nodes"amenity=toilets or building=toilets" -o=berlin_toilets.osm
```

Nach ein paar Minuten wurde die Ausgangsdatei generiert, deren Größe nur 302KB betrug. Die `.osm` Datei ist ein XML-Datei, die geographische Informationen im Form von "Nodes" mit zusätzlichen ergänzenden Tags beinhaltet. Die Allgemeine Struktur eines Beispielnodes unseres Datensatzes sieht so aus:
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

Die bereitgestellten Tools zur Verarbeitung von den OpenStreetMap spezifischen Dateiformaten (`.osm, .osc, .o5m, .o5c, .osm.pbf`) haben sich als sehr nützlich und bedienerfreundlich bewiesen, und aus diesem Grund haben wir die Konvertierung vom `.osm` zum `.xml` Format durchgeführt, erst nachdem alle unbrauchbaren Dateien rausgefiltert wurden.

Als Datenbank haben wir für unser Pojekt die Firebase-Platform gewählt. Das Einbeziehen von Firebase in Android-Studio is einfach, da diese Funktionalität als Modul in dem Android Studio Softwarepaket schon zur Verfügung steht, nach entsprechender Aktivierung in den Einstellungen.
Firebase ist eine Realtime NoSQL Datenbank. Realtime bedeutet, in diesem Fall, dass alle Endbenutzer (Devices) eine und dieselbe Datenbank-Instanz gemeinsam benutzen und asynchron den neusten Stand der Daten herunterladen. In der Datenbank werden die Daten in dem JavaScript Object Notation (JSON) Format gespeichert.

Die nächste Aufgabe war die Konvertierung von `.xml` zu `.json`. Nach Recherche und Ausprobieren von mehreren Programmen, die kostenlos online zugänglich sind, kamen wir zu dem Entschluss, dass wenn  eine dauerhaft stabile Ausgangsstruktur erzielt werden soll, die an unseren Eingabedaten angepasst ist, wir einen eigenen Parser entwickeln müssen.

Das eigentliche Problem bestand darin , dass alle Atribute der `.osm` Datei die Struktur 
`<tag k="--Bezeichnung--" v="--Wert--" />`
trugen, was bei der Konvertierung zu JSON folgendes ergab: 

```
"tag": {
        "-k": "--Bezeichnung--",
        "-v": "--Wert--"
      }
```
Das haben wir als ungünstig betrachtet und haben mit der Entwicklung unseres Parsers angefangen. 

Der Parser wurde in Python umgesetzt, da viele Bibliotheken zur Verarbeitung von `.xml` Dateien schon zur Verfügung stehen. Die Dokumentation von `xml.etree.ElementTree` (und die C Implementation davon `xml.etree.cElementTree` ) war sehr ausführlich und wir konnten uns schnell damit vertraut machen. `cElementTree` ist vielleicht die bekannteste Bibliothek zur Verarbeitung von `.xml` Dateien.


```python
import xml.etree.cElementTree as et

parsed_xml = et.parse("berlin_toilets.xml")
```

Um `.xml`Dateien zu erfassen, ist die Funktion `xml.etree.cElementTree.parse()` vorhanden. Somit konnte die weitere Bearbeitung der `.xml`Datei fortgesetzt werden.


Der Parser legt eine Datei mit der `.json ` Erweiterung im Schreibemodus an, und schreibt in dieser Datei mittels der `write()` Funktion weiter.

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

Jedes Toilet-Objekt besitzt eine einzigartiges 'id', die wir für ein bessere Übersichtlichkeit als Primärschlüssel betrachten können. 



Nach dem Parsen, besitzt jedes Toiletten-Objekt im Allgemeinen folgende Struktur. Informationen über Adresse, Gebühr, Name, Öffnungszeiten weichen bei manchen Objekten ab, oder fehlen komplett. Alle Objekte besitzen aber, schon im Voraus, Informationen über Längen- und Breitengrad.

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

Nachdem wir erfolgreich die ~1800 Objekten zu `.json` Konvertiert hatten, konnten wir sie in der Datenbank hochladen. Das war besonders einfach, da die Firebase-Konsole über die Funktion **import JSON file** verfügt. 






## Implementierung der Datenbank in Android Studio <a name="dbJava"></a>

Nachdem die Datenbank angelegt wurde, und alle Toiletten-Objekte sich dort befinden, konnten wir die Verbindung zwischen unserem Datenbestand und Code herstellen.





<a name="build.gradle"></a>Als erstes sind die Abhängigkeiten (Dependencies) in dem **build.gradle** (Projektebene) zu ergänzen:

```java
dependencies {
    	...
        classpath 'com.google.gms:google-services:4.0.1'
        ...
    }
```

 und in dem **build.gradle** (app-Ebene) auch:

```java
...
//Firebase
implementation 'com.google.firebase:firebase-auth:16.0.5'
implementation 'com.google.firebase:firebase-storage:16.0.5'
implementation 'com.google.firebase:firebase-database:16.0.5'
implementation 'com.google.firebase:firebase-core:16.0.5'
...
```





<a name="db.java"></a>Zwei Instanzen der Firebase-Platform  existieren im Konstruktor der `db.java` Klasse unserer App.

```java
//db instance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

//db storage instance
        final FirebaseStorage databaseStorage = FirebaseStorage.getInstance();
```

Das erste Instanz ist das Datenbank Objekt, das zweite - der Speicherbereich des Datenbank-Objektes. Um Fotos hoch- und herunterladen zu können, wird eine Instanz des Speicherbereiches der Datenbank benötigt.

Die Referenzen in unserer `db.java` Klassen kann man als Pointer zu Objekten in der Datenbank betrachten. 

```java
toiletRef = database.getReference("/toilet");
userRef   = database.getReference("/user");
storageRef = databaseStorage.getReference();
```

`toiletRef`und `userRef` laden jeweils die Listen von Toiletten- und Userobjekten herunter. Sie ermöglichen zusätzlich das Hinzufügen von Toiletten- und Userobjekten in der Datenbank.

 

Durch `toiletRef.addListenerForSingleValueEvent(new ValueEventListener() {...}` wird ein Listener zu der Datenbankreferenz beigefügt. Das ermöglicht die Ausführung der `onDataChange(){...}`  Funktion, welche eine Liste von Toiletten asynchron von der Datenbank herunterlädt. Informationen wie Gebühr, Öffnungszeiten, PLZ, Adresse, usw. werden den einzelnen Toilettenobjekten zugewiesen. 

```
...

toiletRef.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        // declare the list inside onDataChange because the function fires asynchronously
        List<Toilet> toiletList = new ArrayList<>();
        for(DataSnapshot toiletSnapshot : dataSnapshot.getChildren()) {
            String id =toiletSnapshot.getKey();
            String fee = (String) toiletSnapshot.child("fee").getValue();
            String name = (String) toiletSnapshot.child("name").getValue();
            String openingHours = (String) toiletSnapshot.child("opening_hours").getValue();
            String plz = (String) toiletSnapshot.child("plz").getValue();
            String street = (String) toiletSnapshot.child("street").getValue();
            ...
            ...
            
            DataHolder.getInstance().setData(toiletList);
            ...
            }

}
...            
```



Das Hinzufügen von einem Toiletten-Objekt erfolgt durch die `addToilet(){...}` Methode. 

```java
public static boolean addToilet(Toilet toilet)
{
    try {
        toiletRef.child(toilet.id).push();
        toiletRef.child(toilet.id).child("userId").setValue(DataHolder.getInstance().getUser().getId());
        toiletRef.child(toilet.id).child("name").setValue(toilet.getName());
        toiletRef.child(toilet.id).child("fee").setValue(toilet.isFee());
        ...
        ...
        return true;
          
    } catch (Exception e) {
            return false;
}
```



`storageRef` ist die Referenz des Speicherbereiches unserer Datenbank. Nach erfolgreichem Hochladevorgang mittels der Methode `storageRef.putFile();`, kann auf den Dateipfad der hochgeladenen Datei mit der Methode `storageRef.getPath();` zugegriffen werden.

Der Hochladevorgang ist in der `uploadImage(Uri filePath, final Context context){...}` Methode vollständig beschrieben. Der Rückgabewert dieser Methode ist ein `String`, welchem Wert den Datenpfad der hochgeladenen Datei ist.

Jedes Bild  bekommt einen einzigartigen Namen beim Hochladen. Die `UUID.randomUUID()` Funktion generiert einen 36-Zeichen-langen alphanumerischen String und setzt ihn als den Namen des Fotos. Die Wahrscheinlichkeit, dass zwei Fotos denselben Namen bekommen besteht zwar, ist aber sehr, sehr, sehr klein. (https://en.wikipedia.org/w/index.php?title=Universally_unique_identifier&oldid=755882275#Random_UUID_probability_of_duplicates)
```java
StorageReference ref = storageRef.child("images/"+ UUID.randomUUID().toString());
```





Drei Listener werden zu der `putFile()` Funktion beigefügt und somit wird der Erfolg- bzw. Fehlerfall abgefangen. Der `OnProgressListener` wurde in der letzten Version des Codes nicht benutzt, wie von Google empfohlen, da der `progressDialog` die Oberfläche blockiert und keine Eingabe während des Hochlade-Vorganges erlaubt. `bucketResult` ist der String, der den Datenpfad beinhaltet.

```java
 ...
 
 ref.putFile(filePath)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //progressDialog.dismiss();
                    Toast.makeText(context, "Successfully uploaded photo!", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //progressDialog.dismiss();
                    Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    //progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });
    bucketResult = ref.getPath();


}

return bucketResult;

...
```





<a name="Toilet.java"></a> Die `Toilet.java` Klasse beschreibt ein Toiletten-Objekt

Ein Toiletten-Objekt kann für folgende Situationen auf der Client-Seite erzeugt werden:

1. Heruterladen von Datenbank
2. Hinzufügen von einem User der AirPee&Pee App



In beiden Fällen bekommt eine Toilette die folgenden Eigenschaften. Die entsprechenden Getter- und Setterfunktionen für diese Attributen wurden auch implementiert.

```java
protected String id;
protected String fee;
protected List<Rating> Ratings;
protected double totalRating;
protected boolean isPrivate;
protected List<Comment> comments;
protected String description;
protected double locationLat;
protected double locationLon;
protected String name;
protected String openingHours;
protected boolean outoforder;
protected String plz;
protected String street;
protected String streetNumber;
protected String wheelchair;
```


## Comment.java & Rating.java <a name="Comment&Rating"></a>


Es ist zu vermerken, dass jedes Toiletten Objekt sowohl eine Liste von Kommentaren als auch Ratings beinhaltet. 



Kommentare und Ratings sind sehr ähnlich in ihrer Struktur. 

```java
public class Comment {

    public Comment(){}

    public Comment(String id,User user,String commentText){
        this.id=id;
        this.user=user;
        this.commentText=commentText;
    }
    ...
```

```java
public class Rating {

    public Rating(){}

    public Rating(String id,User user,float userRating){
        this.id=id;
        this.user=user;
        this.userRating=userRating;
    }
    ...
```

Die Konstruktoren von beiden bekommen eine String `id` (Toiletten-ID) zur Identifizierung in der Datenbank, ein User, der zu einem oder mehreren Ratings bzw Kommentaren gebunden ist und einen `commentText`  bzw. `userRating` . 


## Log-in using firebase <a name="login"></a>

Benutzer in Firebase-Projekten
Ein Firebase-Benutzerobjekt stellt das Konto eines Benutzers dar, der sich bei Ihrem Firebase-Projekt bei einer App angemeldet hat. In der Regel haben Apps viele registrierte Benutzer, und jede App in einem Firebase-Projekt verwendet eine kopie der Benutzerdatenbank.

Eine Firebase-Benutzerinstanz ist unabhängig von einer Firebase-Auth-Instanz. Dies bedeutet, dass Sie mehrere Verweise auf verschiedene Benutzer innerhalb desselben Kontexts haben und trotzdem eine ihrer Methoden aufrufen können.

Wenn sich ein Benutzer zum ersten Mal bei unserer App anmeldet, werden die Profildaten des Benutzers mit den verfügbaren Informationen aufgefüllt:

Wenn sich der Benutzer mit einer E-Mail-Adresse und einem Kennwort angemeldet hat, wird nur die Eigenschaft der primären E-Mail-Adresse eingetragen

Wenn sich der Benutzer bei einem Anbieter für Verbundidentität wie Google oder Facebook angemeldet hat, werden die vom Anbieter zur Verfügung gestellten Kontoinformationen dazu verwendet, das Profil des Firebase-Benutzers aufzufüllen

wir haben drei Funktionen - Email,Google und Facebook, die unsere Benutzer zur Anmeldung benutzen können.
![Login Firebase](https://i.imgur.com/T2IXrFhr.png)

*Login Code*

Benutzerkonto anlegen :

Zuerst werden Email und Password bestätigt (nicht Null), danach wird ein E-Mail-Bestätigung gesendet und dann wird nach erfolgreichem Anmelden die Benutzeroberfläche mit den Informationen des angemeldeten Benutzers aktualisiert und der Benutzter wird in die Datenbank hinzugefügt.


1. Email und Password wird bestätigt

```java
    ...
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    } 
 
private void createAccount(final String email, final String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information and Insert User in Data
                            DB.addUser(email ,mAuth.getUid());
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
		       }
                       .
		       .
		       .
    }

```
 2. E-Mail-Bestätigung wird gesendet mit hilfe von Toast [Toast Docs Hier](https://developer.android.com/guide/topics/ui/notifiers/toasts)
 
 ```java

 
 private void sendEmailVerification() {
       .
       .
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.verifyEmailButton).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }
 ```
3. Aktualisierung der Benutzeroberfläche : 

```java
 private void updateUI(FirebaseUser user) {
      .
      ...
            DataHolder.getInstance().setUser(new User());
            DB.findUserbyemail(user.getEmail());
            DataHolder.getInstance().getUser().setFirebaseUser(user);
      .
      ...
    }

```
4. Mit facebook und google login, wird die auth behandelt dann die UI geupdated :

```java

 private void handleFacebookAccessToken(AccessToken token) {
	....
	 FirebaseUser user = mAuth.getCurrentUser();
	  updateUI(user);
	...
}

 private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
	...
	 FirebaseUser user = mAuth.getCurrentUser();
	 updateUI(user);
	...
}

```

5. mit der SingOut Function werden alle Daten von User freigelassen

```java
 private void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        DataHolder.getInstance().setUser(null);
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI((FirebaseUser) null);
                    }
                });
        updateUI((FirebaseUser) null);
    }

```

## Implementierung der Karte <a name="karte"></a>


### Google Map Api Konfiguration <a name="api"></a>

Um eine Karte mit mehreren Toiletten (Markern) anzuzeigen, benötigen wir die Google API
Um die Umstellung zu vollziehen, braucht man einen neuen "Maps API Key".
von hier https://cloud.google.com/maps-platform/#get-started

der API-Schlüssel wird In AndroidManifest.xml eingestellt 

```xml
 <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="AIzaSyDilhmAZ-rUga7gKnXkrWWfXPhnCAhuyrA" />
```

Um die Google Maps API zu aktivieren, braucht man einen app's SHA-1 fingerprint 

Um den app's SHA-1 fingerprint zu bekommen:
```shell

keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android

```
API Key erhalten und kopieren in android app setting Schlüssel absichern
####Specify Android permissions
Specify the permissions your application needs, by adding <uses-permission> elements as children of the <manifest> element in AndroidManifest.xml.

### Location permissions <a name="location"></a>
Permissions to the app manifest
the coarse location permission:
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission
        android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
Hinweis: Wenn Ihre App auf API-Level 23 (Android 6.0) abzielt, für das Laufzeitberechtigungen erforderlich sind, sollten Sie sich auf Version 8.3 oder höher des Google Play Services-SDK beziehen.

Berechtigungen werden automatisch in Ihr Manifest eingefügt
Die folgenden Berechtigungen sind im Manifest der Google Play-Dienste definiert und werden beim Erstellen automatisch in das Manifest Ihrer App eingefügt. Sie müssen sie nicht explizit zu Ihrem manifest hinzufügen:

```xml
android.permission.INTERNET - Used by the API to download map tiles from Google Maps servers.
android.permission.ACCESS_NETWORK_STATE - Allows the API to check the connection status in order to determine whether data can be downloaded.
```

### Data anzeigen auf Karte <a name="DataAufKarte"></a>

Berlins öffentliche und private Toiletten werden auf einer Google-Karte dargestellt
und Informationen über Toiletten werden angezeigt.

#### Marker als Toilet <a name="Marker" ></a>

Daten vom Server werden zuerst geholt und werden in Objekte und Marker umgetauscht. 
Aam Ende werden sie auf die Karte gesetzt.

```java
// Data vom Server 
// Attach a SINGLE READ listener to read the data at our posts reference
        toiletRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // declare the list inside onDataChange because the function fires asynchronously
                List<Toilet> toiletList = new ArrayList<>();
                for(DataSnapshot toiletSnapshot : dataSnapshot.getChildren()) {
                    String id =toiletSnapshot.getKey();
                    String fee = (String) toiletSnapshot.child("fee").getValue();
                    ....
		    ..
		    .
                    toiletList.add(temp);
                }
		
                DataHolder.getInstance().setData(toiletList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });



// Toilet to Marker
 public void refreshMarker() {
        mMap.clear();
        MarkerOptions markerPOI;
        for (Toilet t : DataHolder.getInstance().getData()) {
            markerPOI = new MarkerOptions();
            markerPOI.position(new LatLng(t.getLocationLat(), t.getLocationLon()))
                    .title(t.getName());

            if (t.isPrivate())
                markerPOI.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            else
                markerPOI.icon(BitmapDescriptorFactory.defaultMarker());

            mMap.addMarker(markerPOI);
        }
    }
```

#### Toilet Informationen <a name="ToiletInformationen" ></a>
Jedes Toilet-Objekt besitzt mehrere Informationen: name,type,ratings,image, etc. 
die Datei wird erzeugt, wenn man auf dem Marker klickt.

```java

 public void onMapReady(GoogleMap googleMap) {
 .....
mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                m_marker=marker;
                findViewById(id.bottom_sheet).setVisibility(View.VISIBLE);
                findViewById(id.direction_btn).setVisibility(View.VISIBLE);
               // Data erzeugen 
	       putToiletInfo(marker);
                return false;
            }
        });
	....
}


 public void putToiletInfo(Marker marker) {
        TextView name = (TextView) findViewById(id.toiletName);
        TextView type = (TextView) findViewById(id.toilet_type);
        TextView totalrating = (TextView) findViewById(id.reviews);
        TextView cost=findViewById(id.view_cost);
        ImageView imageView = (ImageView) findViewById(id.imageView2);
       .
       .
       .....

    }



```
### Search API <a name="Suchfunktion"></a>

Mit hilfe unserer App soll der User in der Lage sein einen selbstgewählten Standort einzugeben und diesen dann auf der Karte angezeigt zu bekommen. Zudem soll ein Anbieter-User beim erstellen einer Privaten-Toilette auch einen Standort eingeben und die Toilette dann auf der Karte an angegebenen Standort angezeigt bekommen. Um diese Funktion der App zu gewährleisten, haben wir uns der Google Places API bedient. 

#### API-Key <a name="apikey"></a>
Als erstes musste ein API-Key erstellt werden bzw. den bereits bestehenden API-Key (der für die MAPS API benutzt wird) um die Location-API-Funktion erweitert werden.
#### Place Autocomplete Request <a name="placeautocomplete"></a>
Der User bekommt ein Suchfeld angezeigt. in dem er einen Standort eingeben kann. Im laufe der Eingabe, sollen unter dem Suchfeld Verfolständigungsvorschläge angezeigt werden. Dazu wird das Autocomplete Request der Places API benutzt. 
Das Request hat die Form einer HTTP URL, wie folgt:
```https
https://maps.googleapis.com/maps/api/place/autocomplete/output?parameters
```

#### SearchActivity.java <a name="searchactivity"></a>
Die Places API wurde in unserer App innerhalb der SearchActivity.java umgesetzt.

```java
PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
        getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
autocompleteFragment.setHint("Standort eingeben")
```

Hier wird ein autocompleteFragment erstellt, mit Textinhalt „Standort eingeben“, in dem der User dann seinen Standort eingeben kann. Dieses autocompleteFragment stammt aus der Places Bibliothek und gibt automatisch Standortvorschläge. Es muss von uns dazu kein weiterer Code geschrieben werden.

```java
autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);
```

Mit der Funktion autocompleteFragment.setBoundBias() können wir die angezeigten Ergebnisse geographisch eingrenzen. Wir haben unsere suche auf Berlin begrenzt:
```java 
private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
        new LatLng(52.352552, 13.053786), new LatLng(52.702921, 13.769575));
```

```java
autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
    @Override
    public void onPlaceSelected(Place place) {
        Toast.makeText(getApplicationContext(),place.getName().toString(),Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onError(Status status) {
        Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();
    }
});
```
Hier erstellen wir einen Listener, der dann für das vom User ausgewählte Ergebnis Informationen wiedergibt, die dann im weiteren Verlauf der App verwendet werden, und ggf. eine Fehlermeldung ausgiebt.



### Direction API <a name="Richtungsfunktion"></a>

Sobald der User im Hauptfenster eine Toilette ausgewählt hat, soll er die Möglichkeit haben eine Navigation zu der Toilette zu starten. Für diese Funktion wurde die Google Direction API benutzt.
#### Server-Key <a name="serverkey"></a>
Für die Direction API musste ein andere Art Key erstellt werden, nämlich ein Server-Key. Hier gab es anfänglich Probleme, da die Funktionen der Direction-API nur auf dem Rechner funktionnierten auf dem der Server-Key zuerst benutzt wurde.  Es musste erst ein kostenloses Google Developperkonto erstellt werden. Hier wurden jedoch trotz Kostenlosigkeit Rechnungsadresse und Kreditkarteninformation verlangt, die wir dann mit großem Wiederwillen angegeben haben.

#### Direction Request <a name="directionrequest"></a>
Ausgangssituation ist, dass der User Standort und Toilette ausgewählt hat. Sobald der User nun auf den Navigationsbutton drückt, wird ein Direction Request geschickt. 
Das Request hat Form einer HTTP URL, wie folgt:
```https
https://maps.googleapis.com/maps/api/directions/json?origin=Standort&destination=Toilette&key=YOUR_API_KEY
```

#### Akexorcist Library <a name="akexorcistlibrary"></a>
Um uns die Arbeit zu vereinfachen, haben wir uns der Akexorcist Library bedient, die (nebenbei erwähnt) sehr gut dokumentiert ist.

#### DirectionActivity.java <a name="directionactivity"></a>
Die Direction API wurde in unserer App innerhalb der SearchActivity umgesetzt.
```java
btnRequestDirection = findViewById(R.id.btn_request_direction);
btnRequestDirection.setOnClickListener(this);
```
Hier wird ein Button erstellt mit zugehörigem Listener, der sobald er vom User gedrückt wird, die Navigation zwichen Start und Zielpunkt ausgeben soll. (Start und Zielpunkt werden an anderer Stelle definiert siehe MapsActivity und SearschActivity)
```java
public void requestDirection() {
    Snackbar.make(btnRequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
    GoogleDirection.withServerKey(serverKey)
            .from(origin)
            .to(destination)
            .transitMode(TransportMode.WALKING)
            .execute(this);
}
```
requestDirection() wird ausgeführt sobald btnRequestDirection gedrückt wird.
Die Paramater sind wie folgt zu verstehen:
origin: gibt den Startpunkt an.
destination: gibt den Zielpunkt an.
WALKING: gibt die Fortbewegungsart an. In unserem Fall: Laufen.

```java
@Override
public void onDirectionSuccess(Direction direction, String rawBody) {
    Snackbar.make(btnRequestDirection, "Success with status: " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
    if(direction.isOK()){
        googleMap.clear();
        Route route = direction.getRouteList().get(0);
        googleMap.addMarker(new MarkerOptions().position(origin));
        googleMap.addMarker(new MarkerOptions().position(destination));
        ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
        googleMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.RED));
        setCameraWithCoordinationBounds(route);
        btnRequestDirection.setVisibility(View.GONE);
    } else {
        Snackbar.make(btnRequestDirection, direction.getStatus(), Snackbar.LENGTH_SHORT).show();
    }
}
@Override
public void onDirectionFailure(Throwable t) {
    Snackbar.make(btnRequestDirection, t.getMessage(), Snackbar.LENGTH_SHORT).show();
}
```
Wenn requestDirection() erfolgreich ist, wird onDirectionSuccess() ausgeführt bzw. onDirectionFailure() falls nicht.

In onDirectionSuccess() wird mit googleMap.clear() die map geleert, das heißt alle anderen öffentlischen Toilette, die ausgewählte ausgeschlossen, werden nicht mehr angezeigt.
Mit googleMap.addmarket und googleMap.addPolyline wird die Strecke auf der Karte angezeigt.
Mit setCameraCoordinationBounds wird die Sicht der Karte auf Start und Zielpunkt zugeschnitten.


## Fehlerhandler <a name="error"> </a>




### Netzwerkfehler <a name="Netzwerkfehler"> </a>
Airpee&pee hängt vom Netzwerk ab. Es muss mit dem Server kommunizieren, um Daten zu empfangen 
und kann nicht ohne Netzwerk funktionieren.
Mit hilfe ConnectivityManager wird der Netwerkfehler behandelt und ein Error Dialog wird erzeugen. [ConnectivityManager doc hier](https://developer.android.com/reference/android/net/ConnectivityManager) 

```java
 public boolean isOnline() {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            return isConnected;
        } catch (NetworkOnMainThreadException e) {
            return false; }
    }
    
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_maps);
    if(isOnline())
    {
    ....
    }
     else
        {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                Log.d(this.TAG, "Show Dialog: " + e.getMessage());
            }
        }
}
```
## UI Design <a name="UI"></a>

Die Benutzeroberfläche jeder App ist alles, was der Benutzer sehen und damit interagieren kann. Es bedeutet, dass es einfach
und leicht zu bedienen sein sollte, mit faszinierenden Themen und Farben, die den Benutzer überzeugen können, es unter tausenden anderen vorhandenen Apps auszuwählen.

#### Visionäre Skizze <a name="skizze"></a>

Am Anfang haben wir Figma (das Interface-Design-Tool) verwendet, um eine Vorstellung von unserem endgültigen Interface-Design zu erhalten. Natürlich sieht das Endprodukt nicht genau so aus, wie wir es erwartet hatten, aber es hat uns wirklich geholfen, die endgültigen Layouts zu verbessern und zu gestalten.
Außerdem bietet Android Studio eine Reihe vorkonfigurierter UI-Komponenten, z. B. strukturierte Layout Objekte und UI-Steuerelemente, mit denen Sie die grafische Benutzeroberfläche für Ihre App erstellen können.

#### Im Designprozess <a name="design"></a>

Fast alle unsere Aktivitätsklassen verfügen über ein eigenes Layout (.xml). Einige davon, wie bzw.<i>activity_maps</i> , sind die Kombination verschiedener komplexer Layouts, die den Zugriff auf verschiedene Aktivitäten vom Hauptbildschirm aus erleichtert. Zum Beispiel sind <i>bottom_sheet</i> und <i>app_bar_menu</i> im Layout der <i>activity_maps</i> enthalten.

```xml

<android.support.design.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MapsActivity">

        <ProgressBar.../>

        <fragment.../>

        <include
            layout="@layout/bottom_sheet" />

        <LinearLayout...>

    </android.support.design.widget.CoordinatorLayout>

    <include
        layout="@layout/app_bar_menu"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <android.support.design.widget.NavigationView...>

```

Einige unserer Layouts sind personalisierte Versionen der vorgefertigten pre-built UI Layouts des Android Studios. Einige werden aber von uns selbst erstellt, wie das Setting Activity Layout (<i>pref_main.xml</i>), in dem preferenceScreen anstelle von einfachem relativen / linearen Layout verwendet wurde.

```xml

<PreferenceScreen xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <PreferenceCategory android:title="Settings"...>


    <PreferenceCategory android:title="Support">


</PreferenceScreen>
```

Um zu verstehen, wie komplex eine Schnittstelle sein kann, ist hier eine Visualisierung der Ansichtshierarchie <i>bottom_sheet.xml</i>:

![view hierarchy](https://user-images.githubusercontent.com/44113201/51075335-50e51880-168a-11e9-8196-b508b26b61d7.jpg)

#### Endprodukt <a name="endp"> </a>

Jetzt vertreten wir unsere letzte und beste Version unserer App:


## Fazit <a name="fazit"> </a>

An dieser Stelle wollen wir noch kurz darauf eingehen, was wir im laufe dieses Projektes, dass für die meisten von uns das erste größere Softwareprojekt war, gelernt haben. 

In den ersten Wochen haben wir unseren Schwerpunkt auf den Projekt-Management-Anteil des Projektes gesetzt. Um von vornehinein ein klares Ziel zu setzten (Lastenheft) und dieses im Detail auszuformulieren (Pflichtenheft) und in folge dessen auch eine klare Aufgabenverteilung zu erreichen. Und um eine reibungsfreie Kommunikations sicherzustellen haben wir auch sofort ein Github-Repository erstellt sowie eine Slack-Gruppe. Dies hat sich im Laufe des Projektes als enorm Hilfreich herausgestellt. Die Kommunikation hat in unserer Gruppe hervoragend funktionniert und zusammen mit der detaillierten Plannung zu einem erfolgreichen und pünktlichen Beenden unseres Projektes geführt.

Die programmieren in Java innerhalb von android-studio hat sich, obwohl die meisten von uns keine Java-Kenntnisse besaßen, als sehr intuitiv herausgestellt und dank der sehr guten Dokumentation und starken Forenactivität zu einem schnellen Verständnis hinsichtlich der Android-Programmierung geführt.

Auf Fehler sind wir dennoch gestoßen, teilweise auch sehr nervenaufreibende. Diese hatten aber weder mit Java noch mit Android zu tun, sondern eher mit Android-Studio (Probleme bei der Simulation) und den APIs von Google.

 
