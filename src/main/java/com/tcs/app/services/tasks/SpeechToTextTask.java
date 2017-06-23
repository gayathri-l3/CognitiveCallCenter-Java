package com.tcs.app.services.tasks;

import java.io.File;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions.Builder;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;

public class SpeechToTextTask {

	public static String process(String recordingUrl) throws Exception {

		File recordingFile = null;
		if (recordingUrl == null) {
			System.out.println("Error in fetching the recording Url");
		} else {
			recordingFile = WavFileDownload.process(recordingUrl);
		}

		SpeechToText speechToText = new SpeechToText();
		speechToText.setUsernameAndPassword("7c42d64f-cbc1-42dd-bce2-c916e41fdd4e", "I0iqB588wwH2");

		RecognizeOptions recognizeOptions = new Builder()
				.contentType(HttpMediaType.AUDIO_WAV)
				.continuous(true)
				.model("en-US_NarrowbandModel")
				.smartFormatting(true)
				.build();

		SpeechResults speechResults = speechToText.recognize(recordingFile, recognizeOptions).execute();

		String textResultFromStT = getHighestConfidenceText(speechResults);

		return textResultFromStT;
	}

	private static String getHighestConfidenceText(SpeechResults speechResults) {
		String textResult = " ";
		if (speechResults.getResults().size() < 1){
			return textResult;
		} else {
			Transcript transcript = speechResults.getResults().get(speechResults.getResultIndex());
			double confidence = 0.00;
			if (transcript.getAlternatives().size()>0){
				for (SpeechAlternative alt : transcript.getAlternatives()) {
					if (alt.getConfidence() > confidence) {
						confidence = alt.getConfidence();
						textResult = alt.getTranscript();
					}
				}
			}
		}		
		return textResult;
	}
}
