package xml;

/**
 * Created by Александр on 12.06.2016.
 */
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Used to convert City's id to String for correct serialization/deserialization
 */
public class IdAdapter extends XmlAdapter<String, Integer> {

    @Override
    public Integer unmarshal(String string) throws Exception {
        return DatatypeConverter.parseInt(string);
    }

    @Override
    public String marshal(Integer value) throws Exception {
        return DatatypeConverter.printLong(value);
    }

}
