package com.tcs.app.services.tasks;

import java.io.File;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions.Builder;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;

public class SpeechToTextTask {

	public static String process(String recordingUrl, SpeechToText speechToText) throws Exception {

		File recordingFile = null;
		if (recordingUrl == null) {
			System.out.println("Error in fetching the recording Url");
		} else {
			recordingFile = WavFileDownload.process(recordingUrl);
		}

//		SpeechToText speechToText = new SpeechToText();
//		speechToText.setUsernameAndPassword("085876cf-5d45-47ff-93eb-142797ac893b", "MO0ingDl2rb2");

		RecognizeOptions recognizeOptions = new Builder().contentType("audio/wav").continuous(true)
				.model("en-US_NarrowbandModel").smartFormatting(true).build();
		System.out.println("********Just called speech to text*********");
		SpeechResults speechResults = speechToText.recognize(recordingFile, recognizeOptions).execute();
		String textResultFromStT = getHighestConfidenceText(speechResults);
		textResultFromStT = textResultFromStT.trim();
		System.out.println("TEXT RESULTS : " + textResultFromStT );
		return textResultFromStT;
	}

	private static String getHighestConfidenceText(SpeechResults speechResults) {

		Transcript transcript = speechResults.getResults().get(speechResults.getResultIndex());
		double confidence = 0.00;
		String textResult = null;
		for (SpeechAlternative alt : transcript.getAlternatives()) {
			if (alt.getConfidence() > confidence) {
				confidence = alt.getConfidence();
				textResult = alt.getTranscript();
			}
		}
		return textResult;
	}
}
