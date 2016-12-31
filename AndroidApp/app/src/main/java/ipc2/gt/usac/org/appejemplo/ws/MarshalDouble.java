package ipc2.gt.usac.org.appejemplo.ws;

/**
 * Created by r_blu on 29/12/2016.
 */

import java.io.IOException;

import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
/**
 *
 * @author Vladimir
 * Used to marshal Doubles - crucial to serialization for SOAP
 */
//CLASE QUE SE UTILIZA TAL COMO ESTA PARA PASAR TIPOS
//DOUBLE POR KSOAP.
public class MarshalDouble implements Marshal {
    public Object readInstance(XmlPullParser parser, String namespace, String name,
                               PropertyInfo expected) throws IOException, XmlPullParserException {

        return Double.parseDouble(parser.nextText());
    }


    public void register(SoapSerializationEnvelope cm) {
        cm.addMapping(cm.xsd, "Double", Double.class, this);

    }


    public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
        writer.text(obj.toString());
    }
}