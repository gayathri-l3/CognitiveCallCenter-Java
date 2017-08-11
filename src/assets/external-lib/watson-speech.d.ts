declare module WatsonSpeech{
  module TextToSpeech{
    function synthesize(obj);
  }
  module SpeechToText{
    function recognizeMicrophone(obj);
    function recognizeFile(obj);
  }
}