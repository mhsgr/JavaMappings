package de.mhsgr.po.AddNamespaceTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

import com.havemester.po.mapping.test.InputPayloadImpl;
import com.havemester.po.mapping.test.OutputPayloadImpl;
import com.havemester.po.mapping.test.TransformationInputImpl;
import com.havemester.po.mapping.test.TransformationOutputImpl;

import com.sap.aii.mapping.api.InputPayload;
import com.sap.aii.mapping.api.OutputPayload;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

import de.mhsgr.po.AddNamespace.AddNamespace;



public class AddNamespaceTest {
	public static void main(String[] args) {
		try {
			FileInputStream  is = new FileInputStream("Test\\AddNamespace\\in1.xml");
			InputPayload	 il = new InputPayloadImpl (is);
			
			FileOutputStream os = new FileOutputStream("Test\\AddNamespace\\out.xml");
			OutputPayload	 ol = new OutputPayloadImpl (os);
			
			TransformationInput  transformationInput  = new TransformationInputImpl (null, null, il, null);
			TransformationOutput transformationOutput = new TransformationOutputImpl (null, null, ol, null);
			
			AddNamespace mapping = new AddNamespace();
			
			mapping.transform(transformationInput, transformationOutput);
			
			is.close();
			os.close();
			
			BufferedReader br = new BufferedReader(new FileReader (new File("Test\\AddNamespace\\out.xml")));

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
