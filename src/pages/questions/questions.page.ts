import {Component, Input, ChangeDetectorRef, ViewChild, OnInit} from '@angular/core';
import {Content, NavController} from 'ionic-angular';
import {QuestionsChatService} from '../../services/questions-chat/questions-chat.service';
import {Chat} from '../../interfaces/chat.interface';
//import {AudioService} from '../../services/audio/audio.service';
//import {TokenService} from '../../services/token/token.service';
//import {MicService} from '../../services/mic/mic.service';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'questions-page',
  templateUrl: 'questions.page.html'
})

export class QuestionsPage {
	@ViewChild('questionsContent') content: Content;

  private name = 'purchase-questions';
  private voice = 'en-US_MichaelVoice';
  private input: string;
	private classification: any;
	private chats$: Observable<Chat[]>;
	private speechToken: string;
	private voiceToken: string;
 
  constructor(
		private _questionsChat: QuestionsChatService,
	/*	private _ref: ChangeDetectorRef,
		private _audio: AudioService,
		private _token: TokenService,
		private _mic: MicService,*/
    private _nav: NavController
	) {
    this.converse();
    this.subscribeToWatsonChat();
    //this.getTokens();
		this.chats$ = this._questionsChat.chats$;
    this.chats$.subscribe(res => {
      console.log(res);
    });

   }

	send(): void {
		this.converse();
		this.clearInput();
		this.updateScroll(); 
	};

  private converse(): void {
		if (this.input != undefined && this.input.trim() != ""){
      this._questionsChat.converse(this.input);
    }     
	}

  private clearInput(): void {
		this.input = '';
	}
	keydown($event): void {
		if ($event.key === 'Enter' && typeof this.input === "string" && this.input !== '') {
			this.send();
		}
	}
  

  private updateScroll(): void {
		window.setTimeout(() => {
			let bottom = this.content.getContentDimensions().scrollHeight;
			this.content.scrollTo(0, bottom * 2, 3 * 1000);
		}, 1)
	}

/*	private speak(text: string): void {
    let returnObj;
		returnObj = this._audio.speak({
			text: text,
			token: this.voiceToken,
			autoPlay: true,
			element: document.getElementById('questions-audio'),
			voice: this.voice
		});
	}
/*	private record(): void {
		let options = {
			token: this.speechToken,
			continuous: false
		}
		var duplicate = "";
		this._mic.getSpeech(options).subscribe(text => {
			if (text !== '' && duplicate == '') {
				this.input = text;
				duplicate = text;
				this.send();
			}
		})
}*/

	private subscribeToWatsonChat(): void {
		this._questionsChat.watsonChat$.subscribe(res => {
      //this.speak(res.text);
			this.updateScroll();
    });
	}

	/*private getTokens() {
		let speechToken$ = this._token.speechToken$;
		let voiceToken$ = this._token.voiceToken$;
		speechToken$.subscribe(token => {
			this.speechToken = token;
		})
		voiceToken$.subscribe(token => {
			this.voiceToken = token;
		})
	}
*/
  private closeFAQ(){
    this._nav.pop();
  }
}