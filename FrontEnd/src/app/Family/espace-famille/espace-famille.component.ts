import { Component } from '@angular/core';
import { FamilyService } from '../../../services/family.service';
import { Router } from '@angular/router';  // Import Router

import {catchError, of, tap} from "rxjs";
import {CheckFamilyRequest} from "../family.model";


@Component({
  selector: 'app-espace-famille',
  templateUrl: './espace-famille.component.html',
  styleUrls: ['./espace-famille.component.css']

})

export class EspaceFamilleComponent {

  familyName: string = '';
  password: string = '';
  relative: string = '';

  loginSuccess: boolean = false;
  loginFailed: boolean = false;

  constructor(private familyService: FamilyService,
              private router: Router) {
  }


  // Model to hold the form data from user input
  formData = {

    familyName: '', // User enters this
    relative: '',  // User enters this
    password: ''    // User enters this
  };

  loginError: string = '';
  isLoading: boolean = false; // To show loading state

  onlogin(): void {

        this.loginError = '';
        this.isLoading = true;

        // --- Step 1: Validate initial form data ---
        if (!this.formData.familyName || !this.formData.relative || !this.formData.password) {
          this.loginError = 'All fields are required.';
          this.isLoading = false;
          return;
        }

        // --- Step 2: Get Family ID by Name ---
        this.familyService.getFamilyIdByName(this.formData.familyName).pipe(
          tap(foundId => {
            // --- Step 3: Populate CheckFamilyRequest with found ID and user inputs ---
            const credentials: CheckFamilyRequest = {
              id: foundId,
              relative: this.formData.relative,
              password: this.formData.password
            };

            this.familyService.setFamilyName(this.formData.familyName);

            // --- Step 4: Call checkFamilyExists with the complete credentials ---
            this.familyService.checkFamilyExists(credentials).subscribe({
              next: (exists: boolean) => {

                console.log("Family Id : " + credentials.id);
                console.log("Relative : " + credentials.relative);
                console.log("password : " + credentials.password);

                this.isLoading = false;
                if (exists) {
                  console.log('Family login successful!');
                  // Implement actual authentication flow (token storage, navigation)
                  alert('Login Successful!');
                  this.router.navigate(['/familyarea']);
                } else {
                  this.loginError = 'Invalid Medical ID or password for the specified family.';
                  console.log('Family login failed: Invalid credentials.');
                }
              },
              error: (err) => {
                this.isLoading = false;
                console.error('Login check error:', err);
                this.loginError = 'An error occurred during login verification. Please try again.';
              }
            });
          }),
          // Handle error from getFamilyIdByName (e.g., family not found)
          catchError(err => {
            this.isLoading = false;
            if (err.status === 404) {
              this.loginError = `Family "${this.formData.familyName}" not found.`;
            } else {
              this.loginError = 'An error occurred while finding family ID. Please try again.';
              console.error('Error finding family ID:', err);
            }
            return of(null); // Return a null observable to stop the chain
          })
        ).subscribe(); // Don't forget to subscribe to the outer observable chain
  }
}
