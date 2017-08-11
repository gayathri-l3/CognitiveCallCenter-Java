import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Headers, RequestOptions} from '@angular/http';
import {Chat} from '../../interfaces/chat.interface';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {AsyncSubject} from 'rxjs/AsyncSubject';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class QuestionsChatService {
  private url: string;
  private id: string;
  private _chats$: Subject<Chat[]>;
  private chats: Chat[];
  private _watsonChat$:Subject<Chat>;
  private _chatInput$: ReplaySubject<string>;

  constructor(private _http: Http) {
    this.url = 'https://bsucincisaleshelpdeskserver.mybluemix.net/micro/conversation';
    

    //this.url = 'http://localhost:8080/conversation';
    this._chats$ = new ReplaySubject<Chat[]>();
    this._watsonChat$ = new Subject<Chat>();
    this.chats = [];
    this._chatInput$ = new ReplaySubject<string>(1);
    this.converse('init');
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


  changeInput(text: string): void{
    this._chatInput$.next(text);
  }

  converse(text): void {
    console.log('Service converse called');
    let chat: any = {}
   // chat.id = this.id;
    if (this.chats.length > 0) {
      console.log('handle user chat');
      this.handleUserChat(text);
    }
    if (text != 'init'){
      chat.text = text;
      chat.name = 'Teller';
     // chat.messageID ='';
     // chat.messageContent = text;
    
    } else {
     chat.text = '';
      chat.name = 'Teller';

      /*chat.messageID = '';
      chat.messageContent = '';
      chat.messageIntention = '';*/
     
    }

    console.log(chat);
    let body = JSON.stringify(chat);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    this._http.post(this.url, body, options)
      .catch(this.handleError)
      .subscribe(res => {
        console.log('result'+ res);
        console.log('resultjson'+ res.json());
        this.handleChat(res.json());
      })
  }

   /*private extractData(res: Response): Chat {
    let body = res.json();
    console.log('body'+body);
    let data = {
     /*  text: body,
      name: body.name,
      id: body.id,*
      text : body.messageContent,
      id: body.messageID
    }
     return data;
  }*/

  private handleError(error: any): Observable<any> {
    console.log('error'+error);
    return Observable.throw(error);
  }

  private handleChat(input: Response): void {
    console.log('inside handle chat');
    //console.log('length'+input['outputtext']);
    for(let key in input){
console.log('resultkey'+input[key].messageContent);
    
    console.log('length'+input.toString());
       if (input['outputtext'] != undefined && input['outputtext'] != '' && input['outputtext'] != null){
       //  if (input[key].messageContent != undefined && input[key].messageContent != '' && input[key].messageContent!= null){
        //console.log('input'+i+'='+input[i]);
        let result: any = {};
        result.id = 'watson';
        result.name = 'sales-helpdesk';
        result.timestamp = (new Date()).toString();
        result.text = input['outputtext'];
        //result.text = input[key].messageContent;
        this.chats.push(result);
        this._watsonChat$.next(result);
      }
    } 
    console.log(this.chats);
    this._chats$.next(this.chats);
    
  }

  private handleUserChat(input: string): void {
    var result: any = {};
    result.id = 'me';
    result.name = 'purchase-questions';
    result.timestamp = (new Date()).toString();
    result.text = [];
    result.text.push(input);
    console.log(result);
    this.chats.push(result);
    this._chats$.next(this.chats);
  }
}
