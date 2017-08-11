import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Headers, RequestOptions} from '@angular/http';
import {Chat} from '../../interfaces/chat.interface';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class ChatService {
  private url: string;
  private id: string;
  private _chats$: Subject<Chat[]>;
  private chats: Chat[];
  private _watsonChat$:ReplaySubject<Chat>;
  private _chatInput$: ReplaySubject<string>;
  private _promotionFlow: boolean = false;

  constructor(private _http: Http) {
  this.url = 'https://chatbotservice.mybluemix.net/conversation';
    //this.url = 'http://localhost:9080/JavaCloudantApp/chat';
    this._chats$ = new Subject<Chat[]>();
    this._watsonChat$ = new ReplaySubject<Chat>(1);
    this.chats = [];
    this._chatInput$ = new ReplaySubject<string>(1);
  }

  get chats$(): Observable<Chat[]> {
    return this._chats$.asObservable();
  }

  get watsonChat$(): Observable<Chat> {
    return this._watsonChat$.asObservable();
  }

  get chatInput$(): Observable<string>{
    return this._chatInput$.asObservable();
  }
  //2017
  get promotionFlow(): boolean{
    return this._promotionFlow;
  }

  set promotionFlow(value: boolean){
    this._promotionFlow = value;
  }

  changeInput(text: string): void{
    this._chatInput$.next(text);
  }
  converse(chat): void {
    if (this.chats.length > 0) this.handleChat(chat, true);
    chat.id = this.id;
    //2017
    chat.promotionFlow = this.promotionFlow;
    if(this.promotionFlow){
      this.promotionFlow = false;
    }
    let body = JSON.stringify(chat);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    this._http.post(this.url, body, options)
      .map(this.extractData)
      .catch(this.handleError)
      .subscribe(res => {
        if(!this.id){
          this.id = res.id;
        }
        this.handleChat(res);
      })
  }

  private extractData(res: Response): Chat {

    let body = res.json();
    let data = {
      text: body.response,
      name: body.name,
      id: body.id,
      classification: body.classification,
      result: body.result,
      chat: body.chat,
      cart: body.cart,
      selectedTheme: body.theme,
      priceDetermined: body.priceDetermined
    }
    return data;
  }

  private handleError(error: any): Observable<any> {
    return Observable.throw(error);
  }

  private handleChat(input: any, user?: boolean): void {
    var result: any = {};
    for (let key in input) {
      result[key] = input[key];
    }
    if (user) {
      result.id = 'me';
    }
    result.timestamp = (new Date()).toString();
    if(result.text != ''){
      this.chats.push(result);
    }
    this._chats$.next(this.chats);
    if (!user) {
      this._watsonChat$.next(result);
    }
  }
}
