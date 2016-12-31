package ipc2.gt.usac.org.appejemplo;

import org.junit.Test;

import ipc2.gt.usac.org.appejemplo.ws.WebServiceClient;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void consumirWebService() throws Exception {
        String respuesta = WebServiceClient.AbrirCuentaMonetaria(2000,"REIMER","CHAMALE",2020d);
        assertEquals("true",respuesta);
    }
}