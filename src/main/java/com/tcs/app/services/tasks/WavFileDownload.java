package com.tcs.app.services.tasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class WavFileDownload {

	public static File process(String url) throws Exception {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		System.out.println(response.getStatusLine());
		HttpEntity entity = response.getEntity();
		File file = null;
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				BufferedInputStream bis = new BufferedInputStream(instream);
				String filePath = ".";
				file = new File(filePath, "tempRecordingFile.wav");
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				int inByte;
				while ((inByte = bis.read()) != -1) {
					bos.write(inByte);
				}
				bis.close();
				bos.close();
			} catch (IOException ex) {
				throw ex;
			} catch (RuntimeException ex) {
				httpget.abort();
				throw ex;
			} finally {
				instream.close();
			}
			httpclient.getConnectionManager().shutdown();
		}

		return file;
	}

}
