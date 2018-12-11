# AirPee&Pee

### Projekt Softwareentwicklung - WiSe 18/19

HTW Berlin



##### [Elnaz Bezdoodeh](https://github.com/ElnazBezdoodeh)

##### [Osama Alabaji](https://github.com/osamax2)

##### [Leonard Berresheim](https://github.com/leoEAB)

##### Leonard Berresheim

##### [Mihail Milchev](https://github.com/mihailm94)





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
