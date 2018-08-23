package de.mhsgr.po.AddNewNamespaceTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.havemester.po.mapping.test.InputParametersImpl;
import com.havemester.po.mapping.test.InputPayloadImpl;
import com.havemester.po.mapping.test.OutputPayloadImpl;
import com.havemester.po.mapping.test.TransformationInputImpl;
import com.havemester.po.mapping.test.TransformationOutputImpl;

import com.sap.aii.mapping.api.InputParameters;
import com.sap.aii.mapping.api.InputPayload;
import com.sap.aii.mapping.api.OutputPayload;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

import de.arbeitsagentur.po.AddNewNamespace.AddNewNamespace;



public class AddNewNamespaceTest {
	public static void main(String[] args) {
		try {
			FileInputStream  is = new FileInputStream("Test\\AddNewNamespace\\sampleInvoice.xml");
			InputPayload	 il = new InputPayloadImpl (is);
			
			FileOutputStream os = new FileOutputStream("Test\\AddNewNamespace\\sampleInvoice-out.xml");
			OutputPayload	 ol = new OutputPayloadImpl (os);
			
			Map<String,Object> ipm = new HashMap<String,Object>();
			ipm.put("ns", "rrn:org.xcbl:schemas/xcbl/v3_5/xcbl35.xsd");
			ipm.put("prefix", "ns1");
			InputParameters  ip = new InputParametersImpl(ipm);
			
			TransformationInput  transformationInput  = new TransformationInputImpl (null, ip, il, null);
			TransformationOutput transformationOutput = new TransformationOutputImpl (null, null, ol, null);
			
			AddNewNamespace mapping = new AddNewNamespace();
			
			mapping.transform(transformationInput, transformationOutput);
			
			is.close();
			os.close();
			
			BufferedReader br = new BufferedReader(new FileReader (new File("Test\\AddNewNamespace\\sampleInvoice-out.xml")));

		    String line;
		    
			while ((line = br.readLine()) != null) {
		       System.out.println(line);
		    }
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
