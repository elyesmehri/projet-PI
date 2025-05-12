import {Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent  {

  showLoading = true;

  ngOnInit() {
    // Simulate loading screen for 5 seconds
    setTimeout(() => {
      this.showLoading = false;
    }, 5000);
  }


}
