import {Component, Input, ChangeDetectorRef, ViewChild, ContentChild} from '@angular/core';
import {Content} from 'ionic-angular';
import {ChatService} from '../../services/chat/chat.service';
import {Chat} from '../../interfaces/chat.interface';
//import {AudioService} from '../../services/audio/audio.service';
//import {TokenService} from '../../services/token/token.service';
//import {MicService} from '../../services/mic/mic.service';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
@Component({
  selector: 'chat',
  templateUrl: 'chat.component.html'
})

export class ChatComponent {
	@ViewChild('chatContent') content: Content;

	@Input() name: string;
	@Input() voice: string;

  private input: string;
	private classification: any;
	private chats$: Observable<Chat[]>;
	private speechToken: string;
	private voiceToken: string;

  constructor(
		private _chat: ChatService,
		private _ref: ChangeDetectorRef
		//private _audio: AudioService,
		//private _token: TokenService,
		//private _mic: MicService
	) { }

	ngOnInit() {
		//this.getTokens();
		this.chats$ = this._chat.chats$;
		this.subscribeToWatsonChat();
		this.subscribeToChatInput();
    this.converse();
	}

	send(): void {
		this.converse();
		this.clearInput();
		this.updateScroll(); 
	};

  private converse(): void {
		let input = {
			text: this.input,
			name: this.name
		}
		if (input.text == undefined || input.text.trim() != "") this._chat.converse(input);
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

	/*private speak(text: string): void {
		this._audio.speak({
			text: text,
			token: this.voiceToken,
			autoPlay: true,
			element: document.getElementById('chat-audio'),
			voice: this.voice
		});
	}
	private record(): void {
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

	private subscribeToChatInput(): void {
		this._chat.chatInput$.subscribe(res => {
			this.input = res;
			this.send();
		})
	}
	private subscribeToWatsonChat(): void {
		this._chat.watsonChat$.subscribe(res => {
			this.classification = res.classification;
			//this.speak(res.text);
			this.updateScroll();
		})
	}

	/*private getTokens() {
		//let speechToken$ = this._token.speechToken$;
		//let voiceToken$ = this._token.voiceToken$;
		speechToken$.subscribe(token => {
			this.speechToken = token;
		})
		voiceToken$.subscribe(token => {
			this.voiceToken = token;
		})
	}*/
}