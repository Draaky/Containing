package containing;
 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XML_Parser {
    
    
    //making a list with the object Containers
    List<Container> ZeeContainers = new ArrayList<Container>();
    List<Container> BinnenContainers = new ArrayList<Container>();
    List<Container> TreinContainers = new ArrayList<Container>();
    List<Container> VrachtContainers = new ArrayList<Container>();
    int error;
   public XML_Parser()
   {
    try {
        //opening the xml file
	File fXmlFile = new File("E:\\School\\Github\\Containing\\XML\\XML\\xml7.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
 
	//normalizing document (all on one line)
	doc.getDocumentElement().normalize();
 
        //printing first name of the xml file
	//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        
        //making a nodelist on tag record = container
	NodeList nList = doc.getElementsByTagName("record");
        
        //looping through te node list
	for (int temp = 0; temp < nList.getLength(); temp++) {
            
                //making a node of index of the list
		Node nNode = nList.item(temp);
 
		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
 
                //if the type == ELEMENT_NODE
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        
                        //making an Element
			Element eElement = (Element) nNode;
                        
                        //making a new container
                        Container eContainer = new Container();
                        
                        //making a string of the total tag vertrek, because the tags of date and time of departure are equal to arrival
                        String vertrektotaal = eElement.getElementsByTagName("vertrek").item(0).getTextContent();
                        //splitting the string into an string array on newline
                        String[] v = vertrektotaal.split("\\n");
                        //since there are a few empty lines there are some empty indexess 
                        String vdatumD = v[2];
                        String vdatumM = v[3];
                        String vdatumJ = v[4];
                        String testvertrekvan = v[7];
                        String testvertrektot = v[8];
                        String vsoortvervoer = v[10];
                        String vvervoerbedrijf = v[11];
                        
                        //Adding the diffrent values of the xml file to their respective Container variables
                        eContainer.containerID = eElement.getAttribute("id");
                        eContainer.adatumD = Integer.parseInt(eElement.getElementsByTagName("d").item(0).getTextContent());
                        eContainer.adatumM = Integer.parseInt(eElement.getElementsByTagName("m").item(0).getTextContent());
                        eContainer.adatumJ = Integer.parseInt(eElement.getElementsByTagName("j").item(0).getTextContent());
                        eContainer.aVan = Double.parseDouble(eElement.getElementsByTagName("van").item(0).getTextContent());
                        eContainer.aTot = Double.parseDouble(eElement.getElementsByTagName("tot").item(0).getTextContent());
                        eContainer.asoortvervoer = eElement.getElementsByTagName("soort_vervoer").item(0).getTextContent();
                        eContainer.avervoerbedrijf = eElement.getElementsByTagName("bedrijf").item(0).getTextContent();
                        eContainer.eigenaar = eElement.getElementsByTagName("naam").item(0).getTextContent();
                        eContainer.containerNr = Integer.parseInt(eElement.getElementsByTagName("containernr").item(0).getTextContent());
                        eContainer.vVan = Double.parseDouble(testvertrekvan);
                        eContainer.vTot = Double.parseDouble(testvertrektot);
                        eContainer.vdatumD = Integer.parseInt(vdatumD);
                        eContainer.vdatumM = Integer.parseInt(vdatumM);
                        eContainer.vdatumJ = Integer.parseInt(vdatumJ);
                        eContainer.vsoortvervoer = vsoortvervoer;
                        eContainer.vvervoerbedrijf = vvervoerbedrijf;
                        eContainer.x = Float.parseFloat(eElement.getElementsByTagName("x").item(0).getTextContent());
                        eContainer.y = Float.parseFloat(eElement.getElementsByTagName("y").item(0).getTextContent());
                        eContainer.z = Float.parseFloat(eElement.getElementsByTagName("z").item(0).getTextContent());
                        
                        
                        if ("trein".equals(eContainer.asoortvervoer))
                        {
                            TreinContainers.add(eContainer);
                        }
                        else if ("zeeschip".equals(eContainer.asoortvervoer))
                        {
                            ZeeContainers.add(eContainer);
                        }
                        else if ("vrachtauto".equals(eContainer.asoortvervoer))
                        {
                            VrachtContainers.add(eContainer);
                        }
                        else if ("binnenschip".equals(eContainer.asoortvervoer))
                        {
                            BinnenContainers.add(eContainer);
                        }
                        else
                        {
                            System.out.println(eContainer.asoortvervoer);
                            error++;
                        }
                        //obsolete testing of the xml parser
			//System.out.println("Container id: " + eElement.getAttribute("id"));
			//System.out.println("Aankomst");
                        //System.out.println("Datum: " + eElement.getElementsByTagName("d").item(0).getTextContent() + "/" + eElement.getElementsByTagName("m").item(0).getTextContent() + "/" + eElement.getElementsByTagName("j").item(0).getTextContent());
                        //System.out.println("Tijd");
                        //System.out.println("Aankomst: " + eElement.getElementsByTagName("aankomst").item(0).getTextContent());
                        //System.out.println("tot: " + eElement.getElementsByTagName("tot").item(0).getTextContent());
                        //System.out.println("Soort vervoer: " + eElement.getElementsByTagName("soort_vervoer").item(0).getTextContent());
                        //System.out.println("Bedrijf: " + eElement.getElementsByTagName("bedrijf").item(0).getTextContent());
                        //System.out.println("positie");
                        //System.out.println("x: " + eElement.getElementsByTagName("x").item(0).getTextContent());
                        //System.out.println("y: " + eElement.getElementsByTagName("y").item(0).getTextContent());
                        //System.out.println("z: " + eElement.getElementsByTagName("z").item(0).getTextContent());
                        //System.out.println("Eigenaar");
                        //System.out.println("Naam: " + eElement.getElementsByTagName("naam").item(0).getTextContent());
                        //System.out.println("Containernr.: " + eElement.getElementsByTagName("containernr").item(0).getTextContent());
                        //System.out.println("Vertrek");
                        //System.out.println("Datum: " + eElement.getElementsByTagName("d").item(0).getTextContent() + "/" + eElement.getElementsByTagName("m").item(0).getTextContent() + "/" + eElement.getElementsByTagName("j").item(0).getTextContent());
                        //System.out.println("Tijd");
                        //System.out.println("Vertrek: " + eElement.getElementsByTagName("vertrek").item(0).getTextContent());
                        //System.out.println("tot: " + eElement.getElementsByTagName("tot").item(0).getTextContent());
                        //System.out.println("Soort vervoer: " + eElement.getElementsByTagName("soort_vervoer").item(0).getTextContent());
                        //System.out.println("Bedrijf: " + eElement.getElementsByTagName("bedrijf").item(0).getTextContent());
                        //System.out.println("Afmetingen (l x b x h)" + eElement.getElementsByTagName("l").item(0).getTextContent() + " x " + eElement.getElementsByTagName("b").item(0).getTextContent() + " x " + eElement.getElementsByTagName("h").item(0).getTextContent());
                        //System.out.println("Gewicht");
                        //System.out.println("Leeg: " + eElement.getElementsByTagName("leeg").item(0).getTextContent());
                        //System.out.println("Gevuld: " + eElement.getElementsByTagName("inhoud").item(0).getTextContent());
                        //System.out.println("Inhoud");
                        //System.out.println("Naam: " + eElement.getElementsByTagName("naam").item(0).getTextContent());
                        //System.out.println("Soort: " + eElement.getElementsByTagName("soort").item(0).getTextContent());
                        //System.out.println("Gevaar: " + eElement.getElementsByTagName("gevaar").item(0).getTextContent());
                        //System.out.println("ISO: " + eElement.getElementsByTagName("ISO").item(0).getTextContent());
	}
            
            
        
        }
        //obsolete counting of 
        //int count = Containers.size();
        //System.out.println(count);
        //catching the exception
    } 
    catch (Exception e) {
	e.printStackTrace();
    }
   }
}
  
  


