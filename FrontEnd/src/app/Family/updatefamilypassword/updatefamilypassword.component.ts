import { Component, ViewChild } from '@angular/core';
import { FamilyService } from '../../../services/family.service';

import { HttpClientModule } from '@angular/common/http';
import {NgForm} from "@angular/forms";

import {catchError, of, switchMap} from "rxjs";
import {DoctorService} from "../../../services/doctor.service";

@Component({
  selector: 'app-updatefamilypassword',
  templateUrl: './updatefamilypassword.component.html',
  styleUrls: ['./updatefamilypassword.component.css']
})

export class UpdatefamilypasswordComponent {


  @ViewChild('passwordForm') passwordForm!: NgForm; // To access the form's state

  familyname: string = ''; // Bound to the "Doctor Name" input
  newPassword_field: string = ''; // Bound to the "New Password" input
  confirmPassword_field: string = ''; // NEW: Bound to "Confirm New Password"

  newPassword : string = '';
  confirmPassword : string = '';

  passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  message: string = ''; // For success/error messages

  constructor(private familyService: FamilyService) {}

  onUpdatePassword(): void {

    this.message = ''; // Clear previous messages

    // Client-side validation: Form is valid AND passwords match
    if (this.passwordForm.valid && this.newPassword_field === this.confirmPassword_field) {

      console.log ("new password : " + this.confirmPassword_field);

      // First, get the family's ID by name (if that's your backend's requirement)
      this.familyService.getFamilyIdByName(this.familyname).pipe(
        // If getFamilyIdByName succeeds, switch to the updatePassword observable
        switchMap((familyId: number) => {
          console.log(`Family ID found: ${familyId}`);
          console.log(`Updating password for Family: ${this.familyname} (ID: ${familyId}) to: ${this.newPassword_field}`);
          return this.familyService.updateFamilyPassword(familyId, this.newPassword_field);
        }),
        // Catch errors from either getFamilyIdByName or updateFamilyPassword
        catchError(err => {
          if (err.status === 404) {
            this.message = `Family "${this.familyname}" not found. Cannot update password.`;
          } else {
            this.message = 'An error occurred during password update. Please try again.';
            console.error('Error during password update process:', err);
          }
          return of(null); // Return an observable that completes, to stop the chain
        })
      ).subscribe({
        next: (response) => {
          if (response) { // Check if response is not null (due to catchError returning of(null))
            this.message = response || 'Password updated successfully!';
            this.passwordForm.resetForm();
            console.log('Password update successful (API call completed)!');
          }
        },
        error: (err) => { // This error block will only catch errors NOT handled by the pipe's catchError
          // This block might not be reached if catchError in pipe handles errors.
          // It's good practice to have it, but the pipe's catchError is more effective for chaining.
          console.error('Unexpected error in subscription:', err);
        }
      });
    } else {
      this.message = 'Please correct the form errors and ensure passwords match.';
      this.passwordForm.control.markAllAsTouched();
    }
  }
}
