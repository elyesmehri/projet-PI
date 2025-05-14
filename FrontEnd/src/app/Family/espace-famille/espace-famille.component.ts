import { Component } from '@angular/core';
import { FamilyService } from "../../../services/family.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-espace-famille',
  templateUrl: './espace-famille.component.html',
  styleUrls: ['./espace-famille.component.css']
})

export class EspaceFamilleComponent {

  familyName: string = '';
  password: string = '';
  loginSuccess: boolean = false;
  loginFailed: boolean = false;
  relative : string = '';

  constructor(private familyService: FamilyService, private router: Router) {}


  onLogin() {

    console.log ("family name : " + this.familyName);
    console.log ("person in charge : " + this.relative);
    console.log ("password : " + this.password);

    this.familyService.checkFamilyExists(this.familyName,
                                         this.relative,
                                         this.password). subscribe({
      next: exists => {
        if (exists) {
          console.log('✔️ Family exists');
          this.router.navigate(['/familyarea']);

        } else {
          console.log('❌ Family does not exist');
        }
      },
      error: err => {
        console.error('Error checking fields:', err);
      }
    });
  }
}
