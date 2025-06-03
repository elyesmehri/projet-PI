import {Component, OnInit, OnDestroy, Input, TemplateRef} from '@angular/core';
import { FamilyService } from '../../../services/family.service'; // Import DoctorService
import { Family } from "../family.model";
import { Router } from "@angular/router";
import { Location } from "@angular/common";
import {catchError, EMPTY, finalize, Subscription, switchMap, tap} from 'rxjs';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-personal-data-family',
  templateUrl: './personal-data-family.component.html',
  styleUrls: ['./personal-data-family.component.css']
})

export class PersonalDataFamilyComponent implements OnInit {

  errorMessage: string | undefined;

  foundFamily  = {

    id : 0,

    familyname: '',

    relative: '',
    relationship: '',

    address: '',
    phoneNumber: '',

    password: '',
    confirmPassword: '',

    advice: '',

  }

  updateFamily = {

    relative: '',
    relationship: '',
    address: '',
    phoneNumber: '',
    password: '',
    advice: '',
  };

  familyName: string = '';

  currentFamilyData: Family | null = null;

  Family_Id: number = 0;

  private familyNameSubscription: Subscription | undefined;

  message: string = '';

  constructor(private familyService: FamilyService,
              private router: Router,
              private location: Location) {
  }

  ngOnInit(): void {

    this.familyNameSubscription = this.familyService.currentFamilyName$
      .pipe(
        tap(name => {
          this.familyName = name;
          console.log('Family Name (from BehaviorSubject):', this.familyName);
        }),
        switchMap(name => {
          if (name) {
            console.log(`Attempting to get ID for family: ${name}`);
            return this.familyService.getFamilyIdByName(name).pipe(
              tap(id => {
                if (id !== null) {
                  this.Family_Id = id; // Store the Family_Id here
                  console.log('Family ID found:', id);
                } else {
                  console.warn(`Family ID not found for name: ${name}`);
                }
              })
            );
          } else {
            console.warn('Family name is empty, skipping ID retrieval and subsequent calls.');
            return new (0 as any)();
          }
        }),
        // This is the switchMap that makes the getFamilyById call
        switchMap(resultOfIncrementLogin => { // This parameter holds the result of incrementLogin
          // You might want to log or use resultOfIncrementLogin here if needed
          const idToUse = this.Family_Id; // Use the stored ID

          if (idToUse != null) {
            console.log('Finding a family under the id of : ', idToUse);
            return this.familyService.getFamilyById(idToUse); // This is the observable whose result will be passed to `next`
          } else {
            console.warn('Family ID is null, skipping getFamilyById.');
            return new (0 as any)();
          }
        })
      )
      .subscribe({
        next: (familyData: any) => { // <-- Change type here (or cast if you're sure it's Family)
          this.currentFamilyData = familyData as Family; // Explicit cast if you're certain of the structure
          console.log('Successfully retrieved family data:', this.currentFamilyData);

          this.foundFamily.id = familyData.id;
          this.foundFamily.familyname = familyData.familyname;
          this.foundFamily.relative = familyData.relative;
          this.foundFamily.relationship = familyData.relationship;
          this.foundFamily.address = familyData.address;
          this.foundFamily.phoneNumber = familyData.phoneNumber;
          this.foundFamily.password = familyData.password;
          this.foundFamily.advice = familyData.advice;

        },
        error: (error) => {

          console.error('Error during family data retrieval process:', error);
        }
      });
  }

  goHome(): void {

    this.router.navigate(['familyarea']);

  }

  onSubmit(): void {


    console.log("Updated Family Data : " + this.updateFamily);

    console.log("Phone Number : " + this.updateFamily.phoneNumber);
    console.log("Addresse : " + this.updateFamily.address);
    console.log("Advice : " + this.updateFamily.advice);
    console.log("Password : " + this.updateFamily.password);
    console.log("relative : " + this.updateFamily.relative);
    console.log("relationship : " + this.updateFamily.relationship);


    console.log('1/4');

    if (!this.foundFamily) {
      this.errorMessage = "No Family data to update.";
      return;
    }

    console.log('2/4');

    if (!this.updateFamily.phoneNumber || this.updateFamily.phoneNumber.length !== 8 || !/^\d+$/.test(this.updateFamily.phoneNumber)) {
      this.errorMessage = "Phone number must be an 8-digit number.";
      return;
    }

    console.log('3/4');

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
    if (!this.updateFamily.password || !passwordRegex.test(this.updateFamily.password)) {
      this.errorMessage = "Password must be at least 6 characters and contain at least one lowercase letter, one uppercase letter, one number, and one special character.";
      return;
    }

    this.familyService.updateFamily(this.foundFamily.id, this.updateFamily)

      .subscribe(response => {
        // 5. Handle success response from the backend
        this.message = `Family ${response.familyname || 'unknown'} updated successfully!`;
        console.log('Family updated successfully:', response);
      });
  }

  onUnsubscribe () : void  {

    this.familyService.updateFamily(this.foundFamily.id, this.updateFamily)

      .subscribe(response => {
        // 5. Handle success response from the backend
        this.message = `Family ${response.familyname || 'unknown'} updated successfully!`;
        console.log('Family updated successfully:', response);
      });


  }

}
