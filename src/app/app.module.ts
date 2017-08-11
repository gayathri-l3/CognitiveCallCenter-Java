import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler, NavController } from 'ionic-angular';
import { MyApp } from './app.component';

//Pages
//import { PurchasePage } from '../pages/purchase/purchase.page';
import { QuestionsPage } from '../pages/questions/questions.page';

//Components
import { ChatComponent } from '../components/chat/chat.component';
//import { CartComponent } from '../components/cart/cart.component';
//import { CompareModal } from '../components/compare/compare.component';
//import { ResultComponent } from '../components/result/result.component';

//Services
//import { AudioService } from '../services/audio/audio.service';
import { ChatService } from '../services/chat/chat.service';
//import { MicService } from '../services/mic/mic.service';
//import { PromotionService } from '../services/promotions/promotions.service';
//import { PromotionWexService } from '../services/promotions/promotions-wex.service';
import { QuestionsChatService } from '../services/questions-chat/questions-chat.service';
//import { RatingService } from '../services/rating/rating.service';
//import { TokenService } from '../services/token/token.service';
//import { GPSService } from '../services/gps/gps.service';

@NgModule({
  declarations: [
    MyApp,
  //  PurchasePage,
    QuestionsPage,

    ChatComponent
    //CartComponent, 
    //CompareModal, 
    //ResultComponent
  ],
  imports: [
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    //PurchasePage,
    QuestionsPage
    //CompareModal
  ],
  providers: [/*AudioService,*/ ChatService,/* MicService, TokenService, 
  PromotionService, PromotionWexService, RatingService,*/ QuestionsChatService,
  //GPSService,
  {provide: ErrorHandler, useClass: IonicErrorHandler}]
})
export class AppModule {}
