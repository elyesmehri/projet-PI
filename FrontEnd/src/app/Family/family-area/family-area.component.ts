import { Component, OnInit, OnDestroy, Input, TemplateRef } from '@angular/core';
import { FamilyService } from '../../../services/family.service';
import { Subscription, switchMap, tap } from 'rxjs';
import { CheckFamilyRequest, Family } from "../family.model";

@Component({
  selector: 'app-family-area',
  templateUrl: './family-area.component.html',
  styleUrls: ['./family-area.component.css']
})

export class FamilyAreaComponent implements OnInit, OnDestroy {

  Our_family: Family | undefined;

  familyName: string = '';
  currentTime: string = '';

  currentFamilyData: Family | null = null;

  Family_Id : number = 0;

  private familyNameSubscription: Subscription | undefined;

  constructor(private familyService: FamilyService) {}

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
        switchMap(familyIdFromPreviousPipe => { // Use the parameter from the previous pipe if needed
          // Although this.Family_Id is already set, using the pipe's emitted value is cleaner
          const idToUse = this.Family_Id; // Or familyIdFromPreviousPipe if you prefer
          if (idToUse != null) {
            console.log('Incrementing login for Family ID:', idToUse);
            return this.familyService.incrementLogin(idToUse);
          } else {
            console.warn('Family ID is null, skipping incrementLogin.');
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
        },
        error: (error) => {

          console.error('Error during family data retrieval process:', error);
        }
      });

    this.updateTime();
  }

  updateTime(): void {

    const now = new Date();
    this.currentTime = now.toLocaleString(); // Or use toLocaleTimeString() / toISOString() as needed
  }

  ngOnDestroy(): void {
  }

}
