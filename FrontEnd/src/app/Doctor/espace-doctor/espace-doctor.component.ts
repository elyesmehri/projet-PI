import { Component } from '@angular/core';
import { DoctorService } from '../../../services/doctor.service';
import { Router } from '@angular/router';  // Import Router
import {DoctorRequest, Doctor, CheckDoctorRequest} from '../doctor.model';
import {catchError, of, tap} from "rxjs";

@Component({
  selector: 'app-espace-doctor', //  Your selector
  templateUrl: './espace-doctor.component.html', // Your template URL
  styleUrls: ['./espace-doctor.component.css']  // Your styles URL
})

export class EspaceDoctorComponent {


  loginFailed  : string = '';
  loginSuccess : string = '';

  // Model to hold the form data from user input
  formData = {
    doctorName: '', // User enters this
    medicalID: '',  // User enters this
    password: ''    // User enters this
  };

  loginError: string = '';
  isLoading: boolean = false; // To show loading state

  constructor(private doctorService: DoctorService, private router: Router) {
  }

  onlogin(): void {

    this.loginError = '';
    this.isLoading = true;

    // --- Step 1: Validate initial form data ---
    if (!this.formData.doctorName || !this.formData.medicalID || !this.formData.password) {
      this.loginError = 'All fields are required.';
      this.isLoading = false;
      return;
    }

    // --- Step 2: Get Doctor ID by Name ---
    this.doctorService.getDoctorIdByName(this.formData.doctorName).pipe(
      tap(foundId => {
        // --- Step 3: Populate CheckDoctorRequest with found ID and user inputs ---
        const credentials: CheckDoctorRequest = {
          id: foundId, // Use the ID found from the lookup
          medicalID: this.formData.medicalID,
          password: this.formData.password
        };

        this.doctorService.setDoctorName(this.formData.doctorName);

        // --- Step 4: Call checkDoctorExists with the complete credentials ---
        this.doctorService.checkDoctorExists(credentials).subscribe({
          next: (exists: boolean) => {

            console.log ("DoctroName : " + credentials.id );
            console.log ("Medical Id : " + credentials.medicalID);
            console.log ("password : " + credentials.password);

            this.isLoading = false;
            if (exists) {
              console.log('Doctor login successful!');
              // Implement actual authentication flow (token storage, navigation)
              alert('Login Successful!');
              this.router.navigate(['/doctorarea']);
            } else {
              this.loginError = 'Invalid Medical ID or password for the specified doctor.';
              console.log('Doctor login failed: Invalid credentials.');
            }
          },
          error: (err) => {
            this.isLoading = false;
            console.error('Login check error:', err);
            this.loginError = 'An error occurred during login verification. Please try again.';
          }
        });
      }),
      // Handle error from getDoctorIdByName (e.g., doctor not found)
      catchError(err => {
        this.isLoading = false;
        if (err.status === 404) {
          this.loginError = `Doctor "${this.formData.doctorName}" not found.`;
        } else {
          this.loginError = 'An error occurred while finding doctor ID. Please try again.';
          console.error('Error finding doctor ID:', err);
        }
        return of(null); // Return a null observable to stop the chain
      })
    ).subscribe(); // Don't forget to subscribe to the outer observable chain
  }
}
