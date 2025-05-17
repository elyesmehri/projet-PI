import {Component, OnInit, OnDestroy, Input, TemplateRef} from '@angular/core';
import { FamilyService } from '../../../services/family.service'; // Import DoctorService
import { Subscription } from 'rxjs';
import { Family } from "../family.model";
import { Router } from "@angular/router";
import { Location } from "@angular/common";

@Component({
  selector: 'app-personal-data-family',
  templateUrl: './personal-data-family.component.html',
  styleUrls: ['./personal-data-family.component.css']
})

export class PersonalDataFamilyComponent implements OnInit {

  foundFamily: Family | undefined;
  errorMessage: string | undefined;

  updateFamily = {

    id: 0,
    FamilyName: '',

    relative  : '',
    relationship : '',
    address  : '',
    phonenumber : '',
    insurance  : '',
    password  : '',
    confirmPassword: '',

    advice  : '',
    invest : 0
  };

  updatedData = {

    relative  : '',
    relationship : '',
    address  : '',
    phonenumber : '',
    insurance  : '',
    password  : '',
    advice  : '',
    invest : 0
  };

  constructor(private FamilyService: FamilyService,
              private router: Router,
              private location : Location) {
  }

  ngOnInit(): void {
    this.findFamily();

  }

  findFamily(): void {

    this.FamilyService.getFamily('Smith').subscribe(
      (Family) => {
        this.foundFamily = Family;
        console.log("This Family data : ", this.foundFamily);
      },
      (error) => {
        console.error('Error finding Family:', error);
        this.errorMessage = 'Could not find Family.';
      }
    );
  }

  goHome(): void {
    this.location.back(); // Navigate to the home route
  }

  onSubmit(): void {

    console.log('Data to be sent for update:',  this.updatedData);

    console.log ('1/4');

    if (!this.foundFamily) {
      this.errorMessage = "No Family data to update.";
      return;
    }

    console.log ('2/4');

    if (!this.updateFamily.phonenumber || this.updateFamily.phonenumber.length !== 8 || !/^\d+$/.test(this.updateFamily.phonenumber)) {
      this.errorMessage = "Phone number must be an 8-digit number.";
      return;
    }


    console.log ('3/4');

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
    if (!this.updateFamily.password || !passwordRegex.test(this.updateFamily.password)) {
      this.errorMessage = "Password must be at least 6 characters and contain at least one lowercase letter, one uppercase letter, one number, and one special character.";
      return;
    }
/*
    this.FamilyService.patchFamily(this.foundFamily.id, this.updatedData = {

//      id : this.foundFamily.id,
      relative  : this.foundFamily.relative,
      relationship : this.foundFamily.relationship,
      address  : this.foundFamily.address,
      phonenumber : this.foundFamily.phonenumber,
      insurance  : this.foundFamily.insurance,
      password  : this.foundFamily.password,
      advice  : this.foundFamily.advice,
      invest : this.foundFamily.invest

    }).subscribe(

      (response: any) => {

        console.log ('Family updated successfully:', response);
        console.log ('Body is : ' + this.updatedData);
        // this.router.navigate(['/home']);
      },
      (error: any) => {
        console.error('Error updating Family:', error);
        this.errorMessage = 'Failed to update Family.';
        console.log(this.errorMessage, error);
      });

  */
  }
}
