package de.mhsgr.po.RemoveXmlns;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;


public class RemoveXmlns extends AbstractTransformation {
	
	@Override
	public void transform (TransformationInput transformationInput, TransformationOutput transformationOutput) throws StreamTransformationException {
		final InputStream  inputStream  = transformationInput.getInputPayload().getInputStream();
		final OutputStream outputStream = transformationOutput.getOutputPayload().getOutputStream();
	
		final String input  = inputStreamToString(inputStream);
		final String output = input.toString().replace(" xmlns=\"\"", "");
		
		try {
			outputStream.write(output.getBytes());
		} catch (Exception e) {
			throw new StreamTransformationException(e.getMessage());
		}
	}
	
	
	private String inputStreamToString(InputStream inputStream) throws StreamTransformationException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		
		try {
			while ((length = inputStream.read(buffer)) != -1) {
			    result.write(buffer, 0, length);
			}

			return result.toString();
		} catch (Exception e) {
			throw new StreamTransformationException(e.getMessage());
		}
	}	
}

