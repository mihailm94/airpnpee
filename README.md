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
2. [Implementierung der Datenbank in Firebase](#dbFirebase)
3. [Implementierung der Datenbank in Android Studio](#dbJava)
   1. [build.gradle](#build.gradle)
   2. [db.java](#db.java)
   3. [Toilet.java](#Toilet.java)
      1. [Comment.java](#Comment&Rating)
      2. [Rating.java](#Comment&Rating)
4. 
5. 



## Einführung <a name="introduction"></a>

die AirPee&Pee App ist das Ergebnis unseres Softwareprojektes für den Kurs Softwareentwicklung im Wintersemester 18/19 an der HTW Berlin. Unser Team bestand aus 4 Personen. Jeder von uns hatte sich mit einer Hauptaufgabe beschäftigt und im folgenden dokumentiert.

Um die Aufgaben richtig unter uns zu verteilen, haben wir die Projekt-Management Kentnisse von dem Modul Computer Systems Engineering Projekt benutzt und den Entwicklungsvorgang in 5 Phasen unterteilt:

```
1.Konzeptphase
2.Definitionsphase
3.Entwurfsphase
4.Fertigungsphase
5.Wartungsphase
```

In der Konzeptphase haben wir uns mit der Projektidee und Lastenheft beschäftigt.

AirPee&Pee ist eine Android App, die eine Dienstleistung die beim Finden von öffentlichen Toiletten hilft, repräsentiert. Alle öffentlich zugänglichen Toiletten in der Nähe des Benutzers werden gezeigt, inklusiv Preis, Öffnunszeiten, ob sie für Behinderte zugänglich sind, Ratings und Kommentare.

Die App muss die folgenden Muss/Soll-Kriterien erfolgreich erfüllen. Nicht alle Kann-Kriterien sind erfüllt. 

#### Muss/Soll-Kriterien
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

#### Kann-Kriterien

```
A.2.1 Die AirPnP-App kann das Hinzufügen von öffentlichen Toiletten durch einen User anbieten.
A.2.2 Die AirPnP-App soll eine öffentliche Toilette automatisch entfernen wenn sie mehrmals (TBD) gemeldet worden ist.
```

Die Definitionsphäse besteht aus dem Pflichtenheft. Die Kriterien in dem Pflichtenheft beschreiben sehr einfach und strukturiert wie das App-Backend funktioniert. Dadurch wurden uns die Aufgaben, die wir erledigen müssen, klar und konnten jedem Mitglied unseres Team eine Hauptaufgabe zuweisen.

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

## Informelle Beschreibung 
.....

## UML

1. Use Cases

![use cases](https://i.imgur.com/uPlLLtv.png)

2. ERD

![ERD](https://i.imgur.com/qoYTfPa.png)

3. Class Diagram

![Class Diagram](https://i.imgur.com/nhf4shar.jpg)



## Implementierung der Datenbank in Firebase <a name="dbFirebase"></a>

Um die Daten über bestehenden öffentlichen Toiletten zu sammeln, wurden einen Schnappschuss der OpenStreetMap Datenbank - die **Planet.osm** Datei, benutzt. Planet.osm ist die Sammlung von Nodes, Ways, Relationen und Änderungssätze in der Datenbank, inklusive aller Tags. Planet.osm ist eine XML-Datei im .osm-Format, was ein OpenStreetMap spezifisches Dateiformat ist. Die Datei enthällt alle vorhandenen Daten in OpenStreetMap und beschreibt somit die komplette Erde. Mittlerweile ist die Planet.osm Datei 803GB, was für unser Projekt nicht gut geeignet ist. Kleinere Auszüge der Datenbank, die von verschiedenene Anbietern ebenfalls im .osm-Format zur Verfügung gestellt wurden, haben wir für unsere Zwecke benutzt.

Von dem Geofabrik-Downloadserver (https://download.geofabrik.de) wurde der Berlin-Auszug des Planet.osm-Schnappschusses heruntergeladen.  Geofabrik bietet diese Datei in dem `.osm.pbf` Dateiformat. Das erforderte erstmal eine Konvertierung von `.osm.pbf` zu `osm`, und somit das Dekomprimieren der Datei. Um das gewährzuleisten wurde ein Werkzeug namens **osmconvert** benutzt. Die Eingabe erfolgt durch die Kommandozeile. Um unsere Datei zu konvertieren wurde folgendes Kommando benutzt:
```
bzcat berlin-latest.osm.pbf | ./osmconvert - -o=berlin-latest.osm
```

Die Dateigröße des ursprunglichen Auszüges betrug ~51MB. Nachdem er dekomprimiert wurde, standen 773MB unfiltrierten Nodes, Ways, Relationen und Änderungssätze zur Verfügung. Um nur die für uns nützliche Informationen herauszufiltern wurde das Kommandozeilentool **osmfilter** benutzt. Das Tool ermöglicht die Definition von verschiedenen Arten von Filtern, mit denen OSM-Objekte (Nodes, Punkte) extrahiert werden können. Um Objekte eines bestimmten Typen zu behalten, wurde das folgende Kommando benutzt:
```
./osmfilter berlin-latest.osm --keep-nodes"amenity=toilets or building=toilets" -o=berlin_toilets.osm
```

Nach ein paar Minuten wurde die Ausgangsdatei generiert, deren Größe nur 302KB betrug. Die `.osm` Datei ist ein XML-Datei, die geographische Informationen im Form von "Nodes" mit zusätzlichen ergänzenden Tägs beinhaltet. Die Allgemeine Struktur eines Beispielnodes unseres Datensatzes sieht so aus:
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

Die bereitgestellten Tools zur Verarbeitung von den OpenStreetMap spezifischen Dateiformaten (`.osm, .osc, .o5m, .o5c, .osm.pbf`) haben sich als sehr nützlich und bedienerfreundlich bewiesen, und aus diesem Grund war die Konvertierung vom `.osm` zum `.xml` Format durchgeführt, erst nachdem alle unbrauchbaren Dateien rausgefiltert wurden.

Als Datenbank haben wir für unser Pojekt die Firebase-Platform gewählt. Das Einbeziehen von Firebase in Android-Studio is einfach, da diese Funktionalität als Modul in dem Softwarepaket schon zur Verfügung steht, nach entsprechender Aktivierung in den Einstellungen.
Firebase ist eine Realtime NoSQL Datenbank. Realtime bedeutet in diesem Fall, dass alle Endbenutzer (Devices) eine und dieselbe Datenbank-Instanz gemeinsam benutzen und asynchron den neusten Stand der Daten herunterladen. In der Datenbank werden die Daten in dem JavaScript Object Notation (JSON) Format gespeichert.

Die nächste Aufgabe war die Konvertierung von `.xml` zu `.json`. Nach Recherche und Ausprobieren von mehreren Programmen, die frei online zugänglich sind, kamen wir zu der Überzeugung, dass wenn wir eine dauernd stabile Ausgangsstruktur erzielen wollen, die an unseren Eingabedaten angepasst ist, mussten wir einen eigenen Parser entwickeln.

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

Der Parser wurde in Python umgesetzt, da viele Bibliotheken zur Verarbeitung von `.xml` Dateien schon zur Verfügung stehen. Die Dokumentation von `xml.etree.ElementTree` (und die C Implementation davon `xml.etree.cElementTree` ) war sehr ausführlich und wir konnten schnell uns damit vertraut machen. `ElementTree` ist vielleicht die bekannteste API zur Verarbeitung von `.xml` Dateien.



```python
import xml.etree.cElementTree as et

parsed_xml = et.parse("berlin_toilets.xml")
```

Um `.xml`Dateien zu erfassen, ist die `xml.etree.cElementTree.parse()` vorhanden. Somit konnte die weitere Bearbeitung und Manipulation der `.xml`Datei fortgesetzt werden.



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

Jedes Toilet-Objekt besitzt ein einzigartiges 'id', was wir für Übersichtlichkeit als Primärschlüssel betrachtet haben. 



Informationen über Adresse, Gebühr, Name, Öffnungszeiten weichen bei manchen Objekten ab, oder fehlen komplett. Alle Objekte besitzen immer Informationen über Längen- und Breitengrad. Jedes Toiletten-Objekt hat im Allgemeinen die folgende Struktur:

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

`toiletRef`und `userRef` laden jeweils die Listen von Toiletten- und Userobjekten herunter. Sie ermöglichen zusätzlich das Hinzufügen von Toiletten- und Userobjekten in unserer App.

 

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

Jedes Bild  bekommt einen einzigartigen Namen beim Hochladen. Die `UUID.randomUUID()` Funktion generiert einen 36-Zeichen-langen alphanumerischen String und setzt ihn als den Namen des Fotos. Die Wahrscheinlichkeit, dass zwei Fotos denselben Namen bekommen besteht, ist aber sehr, sehr, sehr klein. (https://en.wikipedia.org/w/index.php?title=Universally_unique_identifier&oldid=755882275#Random_UUID_probability_of_duplicates)
```java
StorageReference ref = storageRef.child("images/"+ UUID.randomUUID().toString());
```





Drei Listener werden zu der `putFile()` Funktion beigefügt und somit wird den Erfolg- bzw. Fehlerfall abgefangen. Der `OnProgressListener` wurde in der letzten Version des Codes nicht benutzt, wie von Google empfohlen, da der `progressDialog` die Oberfläche blockiert und keine Eingabe erlaubt. `bucketResult` ist der String, der den Datenpfad beinhaltet.

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

Ein Toiletten-Objekt kann unter den folgenden Umständen auf der Client-Seite erzeugt werden:

1. Heruterladen von Datenbank
2. Hinzufügen von einem User der AirPee&Pee App



In beiden Fällen bekommt eine Toilette die folgenden Eigenschaften :

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



Die entsprechenden Getter- und Setterfunktionen wurden auch implementiert.

```java
public double getCost();
public void setCost(double cost);

public boolean isOutoforder();
public void setOutoforder(boolean outoforder);

public String getPhotoUrl();
public void setPhotoUrl(String photoUrl);

public double getLocationLat();
public double getLocationLon();
public void setLocationLat(double locationLat);
public void setLocationLon(double locationLon);

public String getId();
public void setId(String id);

public String isFee();
public void setFee(String fee);

public String getName();
public void setName(String name);

public String getOpeninghours();
public void setOpeninghours(String opening_hours);

public String getDescription();
public void setDescription(String description);

public List<Rating> getRatings();
public void setRatings(List<Rating> ratings);

public double getTotalRating();
public void setTotalRating(double totalRating);

public boolean isPrivate();
public void setPrivate(boolean aPrivate);

public List<Comment> getComments();
public void setComments(List<Comment> comments);

public String isWheelchair();
public void setWheelchair(String wheelchair);

public String getPlz();
public void setPlz(String plz);

public String getStreet();
public void setStreet(String street);

public String getStreetNumber();
public void setStreetNumber(String streetNumber);
```

Die Funktionen sind als `public` implementiert und damit frei zugänglich innderhalb des `com.appspot.airpeepee.airpeepee` - Package. 




## Comment.java & Rating.java <a name="Comment&Rating"></a>


Es ist zu vermerken dass jedes Toiletten Objekt jeweils eine Liste von Kommentaren als auch Ratings beinhaltet. 



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

Die Konstruktoren von beiden bekommen einen String `id` zur Identifizierung in der Datenbank, ein User, der zu einem oder mehreren Ratings bzw Kommentaren gebunden ist und einen `commentText`  bzw. `userRating` . 


## Login useing firebase 

Benutzer in Firebase-Projekten
Ein Firebase-Benutzerobjekt stellt das Konto eines Benutzers dar, der sich bei Ihrem Firebase-Projekt bei einer App angemeldet hat. In der Regel haben Apps viele registrierte Benutzer, und jede App in einem Firebase-Projekt verwendet eine Benutzerdatenbank.

Eine Firebase-Benutzerinstanz ist unabhängig von einer Firebase-Auth-Instanz. Dies bedeutet, dass Sie mehrere Verweise auf verschiedene Benutzer innerhalb desselben Kontexts haben und trotzdem eine ihrer Methoden aufrufen können.

Wenn sich ein Benutzer zum ersten Mal bei unsere App anmeldet, werden die Profildaten des Benutzers mit den verfügbaren Informationen aufgefüllt:

Wenn sich der Benutzer mit einer E-Mail-Adresse und einem Kennwort angemeldet hat, wird nur die Eigenschaft der primären E-Mail-Adresse eingetragen

Wenn sich der Benutzer bei einem Anbieter für Verbundidentität wie Google oder Facebook angemeldet hat, werden die vom Anbieter zur Verfügung gestellten Kontoinformationen dazu verwendet, das Profil des Firebase-Benutzers aufzufüllen

wir haben drei funktionen email,Google und Facebook nutzen zu anmelden .
![Login Firebase](https://i.imgur.com/T2IXrFhr.png)

*Login Code*
zuerst die Packages :

```java
// Firebase Bibliothek
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

// Facebook Bibliothek
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

// Google Bibliothek
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;


```

## Google Map Api Konfiguration

Um die Umstellung zu vollziehen, braucht man einen neuen "Maps API Key".
von hier https://cloud.google.com/maps-platform/#get-started

der API-Schlüssel wird In AndroidManifest.xml eingestellt 

```xml
 <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="AIzaSyDilhmAZ-rUga7gKnXkrWWfXPhnCAhuyrA" />
```

Google Maps API aktivieren, braucht man app's SHA-1 fingerprint 

um den app's SHA-1 fingerprint zu bekommen
```shell

keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android

```
API Key erhalten und kopieren in android app setting Schlüssel absichern
####Specify Android permissions
Specify the permissions your application needs, by adding <uses-permission> elements as children of the <manifest> element in AndroidManifest.xml.

### Location permissions
permissions to the app manifest
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
