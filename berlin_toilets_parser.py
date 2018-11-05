import xml.etree.cElementTree as et


def getvalueofnode(node):
    """ return node text or None """
    return node.text if node is not None else None


def main():
  
    parsed_xml = et.parse("berlin_toilets.xml")
    
    print("{")
    
    for osm in parsed_xml.iter('node'):
        print("\t\"")
        print(osm.attrib['id'])
        print("\": {")
        
        print("\t\t\"address\":{")
                
        print("\t\t\t\"location\":{")
        print("\t\t\t\t\"lat\" : " + osm.attrib['lat'])
        print("\t\t\t\t" + "\"lon\" : " + osm.attrib['lon'])
        print("\t\t\t\t" + "},")
        for node in osm.iter('tag'):
    	    if(node.attrib['k'] == "addr:postcode"):	
                print("\t\t\t\"plz\": \""+ node.attrib['v'] + "\"")
       	    if(node.attrib['k'] == "addr:street"):
                print("\t\t\t\"street\": \""+ node.attrib['v'] + "\"")
	    if(node.attrib['k'] == "addr:housenumber"):
                print("\t\t\t\"street_number\": \""+ node.attrib['v'] +"\"") 
	   #no = false, yes = true
	    if(node.attrib['k'] == "fee"):
                print("\t\t\"fee\": \""+ node.attrib['v'] + "\"")   

	    if(node.attrib['k'] == "name"):
                print("\t\t\"name\": \""+ node.attrib['v'] + "\"")
	    if(node.attrib['k'] == "opening_hours"):
                print("\t\t\"opening_hours\": \""+ node.attrib['v'] + "\"")		
	    if(node.attrib['k'] == "wheelchair"):
                print("\t\t\"wheelchair\": \""+ node.attrib['v'] + "\"")

 

                
                
         
main()
