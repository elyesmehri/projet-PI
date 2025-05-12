import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})

export class LoadingComponent implements OnInit {

  isLoading = true;
  fadeOut = false;

  welcomeMessage = 'WELCOME TO OUR APP';
  loadingMessage = 'Please wait to connect ...';

  // You can customize this duration
  loadingDuration = 5000;

  ngOnInit(): void {
    setTimeout(() => {
      this.fadeOut = true;

      setTimeout(() => {
        this.isLoading = false;
      }, 500); // matches your fade-out animation duration (CSS transition)
    }, this.loadingDuration);
  }
}
