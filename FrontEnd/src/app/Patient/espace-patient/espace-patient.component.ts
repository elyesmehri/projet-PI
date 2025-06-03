import { Component } from '@angular/core';
import {PatientService} from "../../../services/patient.service";
import {Router} from "@angular/router";
import {catchError, of, tap} from "rxjs";
import {CheckPatientRequest} from "../patient.model"; // Added for navigation

@Component({
  selector: 'app-espace-patient',
  templateUrl: './espace-patient.component.html',
  styleUrls: ['./espace-patient.component.css']
})

export class EspacePatientComponent {

  patientName: string = '';
  password: string = '';
  loginFailed: boolean = false;
  loginSuccess: boolean = false;
  errorMessage: string = '';
  message: string = '';
  isLoggedIn: boolean = false;
  isLoading: boolean = false;
  loginError : string = '';


  formData = {

    patientName: '', // User enters this
    password: ''    // User enters this
  };


  constructor(private patientService: PatientService, private router: Router) {}

  onLogin (): void {


    this.loginError = '';
    this.isLoading = true;


    console.log ("Patient Name from formData : "  + this.formData.patientName);


        // --- Step 1: Validate initial form data ---
        if (!this.formData.patientName ||  !this.formData.password) {
          this.loginError = 'All fields are required.';
          this.isLoading = false;
          return;
        }

    // --- Step 2: Get Patient ID by Name ---
    this.patientService.getPatientIdByName(this.formData.patientName).pipe(
      tap(foundId => {
        // --- Step 3: Populate CheckPatientRequest with found ID and user inputs ---
        const credentials: CheckPatientRequest = {
          id: foundId,
          password: this.formData.password
        };



        this.patientService.setPatientName(this.formData.patientName);

        // --- Step 4: Call checkPatientExists with the complete credentials ---
        this.patientService.checkPatientExists(credentials).subscribe({
          next: (exists: boolean) => {

            console.log("Patient Id : " + credentials.id);
            console.log("password : " + credentials.password);

            this.isLoading = false;
            if (exists) {
              console.log('Patient login successful!');
              // Implement actual authentication flow (token storage, navigation)
              alert('Login Successful!');
              this.router.navigate(['/patientarea']);
            } else {
              this.loginError = 'Invalid Medical ID or password for the specified patient.';
              console.log('Patient login failed: Invalid credentials.');
            }
          },
          error: (err) => {
            this.isLoading = false;
            console.error('Login check error:', err);
            this.loginError = 'An error occurred during login verification. Please try again.';
          }
        });
      }),
      // Handle error from getPatientIdByName (e.g., patient not found)
      catchError(err => {
        this.isLoading = false;
        if (err.status === 404) {
          this.loginError = `Patient "${this.formData.patientName}" not found.`;
        } else {
          this.loginError = 'An error occurred while finding patient ID. Please try again.';
          console.error('Error finding patient ID:', err);
        }
        return of(null); // Return a null observable to stop the chain
      })
    ).subscribe(); // Don't forget to subscribe to the outer observable chain
  }


}
