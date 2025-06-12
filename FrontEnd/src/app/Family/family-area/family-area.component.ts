import { Component, OnInit, OnDestroy, Input, TemplateRef } from '@angular/core';
import { FamilyService } from '../../../services/family.service';
import {catchError, distinctUntilChanged, EMPTY, filter, Subscription, switchMap, tap} from 'rxjs';
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
    console.log('ngOnInit: Initializing family data retrieval.');

    this.familyNameSubscription = this.familyService.currentFamilyName$
      .pipe(
        // 1. Ensure the name is not empty/null/undefined
        filter(name => {
          if (!name || name.trim() === '') {
            console.warn('ngOnInit: Family name is empty or whitespace, skipping chain.');
            this.Family_Id = 0; // Clear any old ID
            this.currentFamilyData = null; // Clear any old data
            // Optionally update a message to the user
            return false; // Do not proceed with an empty name
          }
          return true; // Proceed with a valid name
        }),
        // 2. Only emit if the name has actually changed since the last emission
        distinctUntilChanged(),
        tap(name => {
          this.familyName = name;
          console.log('Pipe Step 1 (Family Name): Received and distinct:', this.familyName);
        }),
        // 3. Resolve Family ID by Name
        switchMap(name => {
          console.log(`Pipe Step 2 (Get ID): Attempting to get ID for family: ${name}`);
          return this.familyService.getFamilyIdByName(name).pipe(
            tap(id => {
              if (id !== null) {
                this.Family_Id = id; // Store the Family_Id here
                console.log('Pipe Step 2 (Get ID): Family ID found:', id);
              } else {
                console.warn(`Pipe Step 2 (Get ID): Family ID not found for name: ${name}. Stopping chain.`);
                // If ID is not found, we typically want to stop the chain
                this.Family_Id = 0;
                this.currentFamilyData = null;
                throw new Error(`Family ID not found for: ${name}`); // Throw an error to go to the subscribe error handler
              }
            }),
            catchError(error => {
              console.error('Pipe Step 2 (Get ID) Error:', error);
              this.Family_Id = 0;
              this.currentFamilyData = null;
              // Return EMPTY or re-throw to propagate error to subscribe()
              return EMPTY; // Use EMPTY to stop the stream if ID not found or error
            })
          );
        }),
        // 4. Increment Login (if ID is available)
        switchMap(familyIdFromPreviousPipe => { // This is the ID from getFamilyIdByName
          // You already have this.Family_Id set in the previous tap, so using that is fine too
          const idToUse = this.Family_Id; // Using stored ID for clarity, or familyIdFromPreviousPipe
          if (idToUse != null) {
            console.log('Pipe Step 3 (Increment Login): Incrementing login for Family ID:', idToUse);
            return this.familyService.incrementLogin(idToUse).pipe(
              catchError(error => {
                console.error('Pipe Step 3 (Increment Login) Error:', error);
                // Decide if you want to stop the chain or continue if incrementLogin fails
                // For now, let's stop it for simplicity if it fails
                return EMPTY;
              })
            );
          } else {
            console.warn('Pipe Step 3 (Increment Login): Family ID is null, skipping incrementLogin.');
            return EMPTY; // Stop if ID is null
          }
        }),
        // 5. Get Full Family Data by ID
        switchMap(resultOfIncrementLogin => { // This parameter holds the result of incrementLogin (if any)
          // Use the stored ID, which should be available if previous steps succeeded
          const idToUse = this.Family_Id;

          if (idToUse != null) {
            console.log('Pipe Step 4 (Get Family Data): Finding a family under the ID of:', idToUse);
            return this.familyService.getFamilyById(idToUse).pipe(
              catchError(error => {
                console.error('Pipe Step 4 (Get Family Data) Error:', error);
                this.currentFamilyData = null;
                return EMPTY; // Stop if getting family data fails
              })
            );
          } else {
            console.warn('Pipe Step 4 (Get Family Data): Family ID is null, skipping getFamilyById.');
            return EMPTY; // Stop if ID is null
          }
        })
      )
      .subscribe({
        next: (familyData: any) => { // Cast to 'Family' if you have the interface
          this.currentFamilyData = familyData; // No need for 'as Family' unless you have a specific reason
          this.Our_family = familyData;
          console.log('Subscription Next: Successfully retrieved family data:', this.currentFamilyData);
          // Update any UI messages to indicate success
        },
        error: (error) => {
          console.error('Subscription Error: Error during family data retrieval process:', error);
          this.currentFamilyData = null;
          // Update UI message to show error
          this.Family_Id = 0;
        },
        complete: () => {
          console.log('Subscription Complete: Family data retrieval chain finished.');
          // This will be called when the observable sequence completes (e.g., after EMPTY is returned)
        }
      });
  }
  updateTime(): void {

    const now = new Date();
    this.currentTime = now.toLocaleString(); // Or use toLocaleTimeString() / toISOString() as needed
  }

  ngOnDestroy(): void {
  }

}
