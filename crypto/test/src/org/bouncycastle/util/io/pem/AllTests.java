package org.bouncycastle.util.io.pem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests
    extends TestCase
{
    public void testPemLength()
        throws IOException
    {
        for (int i = 1; i != 60; i++)
        {
            lengthTest("CERTIFICATE", Collections.emptyMap(), new byte[i]);
        }

        lengthTest("CERTIFICATE", Collections.emptyMap(), new byte[100]);
        lengthTest("CERTIFICATE", Collections.emptyMap(), new byte[101]);
        lengthTest("CERTIFICATE", Collections.emptyMap(), new byte[102]);
        lengthTest("CERTIFICATE", Collections.emptyMap(), new byte[103]);

        lengthTest("CERTIFICATE", Collections.emptyMap(), new byte[1000]);
        lengthTest("CERTIFICATE", Collections.emptyMap(), new byte[1001]);
        lengthTest("CERTIFICATE", Collections.emptyMap(), new byte[1002]);
        lengthTest("CERTIFICATE", Collections.emptyMap(), new byte[1003]);

        Map headers = new LinkedHashMap();

        headers.put("Proc-Type", "4,ENCRYPTED");
        headers.put("DEK-Info", "DES3,0001020304050607");
        lengthTest("RSA PRIVATE KEY", headers, new byte[103]);
    }

    private void lengthTest(String type, Map headers, byte[] data)
        throws IOException
    {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        PemWriter pWrt = new PemWriter(new OutputStreamWriter(bOut));

        PemObject pemObj = new PemObject(type, headers, data);
        pWrt.writeObject(pemObj);

        pWrt.close();

        assertEquals(bOut.toByteArray().length, pWrt.getOutputSize(pemObj));
    }

    public static void main (String[] args)
    {
        junit.textui.TestRunner.run (suite());
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite("util tests");
        suite.addTestSuite(AllTests.class);
        return suite;
    }
}