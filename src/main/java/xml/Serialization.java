package xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by Александр on 14.06.2016.
 */
public class Serialization {
    /**
     *
     * Convert model to xml file
     */
    public static File ObjectToXML(Model model, String pathToFile) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Model.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        File f = new File(pathToFile);
        f.createNewFile();
        m.marshal(model, f);
        return f;
    }
}
