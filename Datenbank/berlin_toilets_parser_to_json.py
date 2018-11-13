import xml.etree.cElementTree as et


def getvalueofnode(node):
    """ return node text or None """
    return node.text if node is not None else None


def main():

    parsed_xml = et.parse("berlin_toilets.xml")
    f = file("berlin_toilets.json", "w")
    f.write("{\n")
    f.write("\t\"toilet\":{\n")
    for osm in parsed_xml.iter('node'):
        f.write("\t\t\"" + osm.attrib['id'] + "\": {\n")

        for node in osm.iter('tag'):
            if(node.attrib['k'] == "addr:postcode"):
                f.write("\t\t\t\"plz\": \""+ node.attrib['v'] + "\",\n")
            if(node.attrib['k'] == "addr:street"):
                f.write("\t\t\t\"street\": \"")
		f.write(node.attrib['v'].encode('utf-8'))
		f.write("\",\n")
            if(node.attrib['k'] == "addr:housenumber"):
                f.write("\t\t\t\"street_number\": \""+ node.attrib['v'] +"\",\n")
           #no = false, yes = true
            if(node.attrib['k'] == "fee"):
                f.write("\t\t\t\"fee\": \""+ node.attrib['v'] + "\",\n")

            if(node.attrib['k'] == "name"):
                f.write("\t\t\t\"name\": \""+ node.attrib['v'].encode('utf-8') + "\",\n")
            if(node.attrib['k'] == "opening_hours"):
                f.write("\t\t\t\"opening_hours\": \""+ node.attrib['v'] + "\",\n")
            if(node.attrib['k'] == "wheelchair"):
                f.write("\t\t\t\"wheelchair\": \""+ node.attrib['v'] + "\",\n")
        f.write("\t\t\t\"location\":{\n")
        f.write("\t\t\t\t\"lat\" : " + osm.attrib['lat'] + ",\n")
        f.write("\t\t\t\t" + "\"lon\" : " + osm.attrib['lon'] + "    \n")
        f.write("\t\t\t\t" + "}\n")
	f.write("\t\t\t},\n")
    f.write("\t}\n")
    f.write("}")
    f.close()
main()

