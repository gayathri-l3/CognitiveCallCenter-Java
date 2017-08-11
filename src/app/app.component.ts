import { Component, ViewChild } from '@angular/core';
import { Platform, NavController } from 'ionic-angular';
import { StatusBar, Splashscreen, Geoposition } from 'ionic-native';

import { QuestionsPage } from '../pages/questions/questions.page';

//import { GPSService } from '../services/gps/gps.service';

@Component({
  template: `<ion-nav #content style="background-image: url('../assets/images/background2.jpg')" [root]="rootPage"></ion-nav>`
})
export class MyApp {
  @ViewChild('content') nav: NavController;
  rootPage = QuestionsPage;

  constructor(platform: Platform) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      StatusBar.styleDefault();
      Splashscreen.hide();
    });
  }

  ngOnInit() {
    
    //if (location != null){sendLocationInformation}
  }

}
