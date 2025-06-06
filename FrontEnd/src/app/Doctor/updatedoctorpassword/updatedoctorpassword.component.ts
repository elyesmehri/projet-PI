import {Component, ViewChild} from '@angular/core';
import {DoctorService} from '../../../services/doctor.service';
import { HttpClientModule } from '@angular/common/http';
import {NgForm} from "@angular/forms";
import {catchError, of, switchMap} from "rxjs";

@Component({
  selector: 'app-updatedoctorpassword',
  templateUrl: './updatedoctorpassword.component.html',
  styleUrls: ['./updatedoctorpassword.component.css']
})

export class UpdatedoctorpasswordComponent {

  @ViewChild('passwordForm') passwordForm!: NgForm; // To access the form's state

  doctorname: string = ''; // Bound to the "Doctor Name" input
  newPassword_field: string = ''; // Bound to the "New Password" input
  confirmPassword_field: string = ''; // NEW: Bound to "Confirm New Password"

  newPassword : string = '';
  confirmPassword : string = '';

  passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  message: string = ''; // For success/error messages

  constructor(private doctorService: DoctorService) {}

  onUpdatePassword(): void {

    this.message = ''; // Clear previous messages

    // Client-side validation: Form is valid AND passwords match
    if (this.passwordForm.valid && this.newPassword_field === this.confirmPassword_field) {

      console.log ("new password : " + this.confirmPassword_field);

      // First, get the doctor's ID by name (if that's your backend's requirement)
      this.doctorService.getDoctorIdByName(this.doctorname).pipe(
        // If getDoctorIdByName succeeds, switch to the updatePassword observable
        switchMap((doctorId: number) => {
          console.log(`Doctor ID found: ${doctorId}`);
          console.log(`Updating password for Doctor: ${this.doctorname} (ID: ${doctorId}) to: ${this.newPassword_field}`);
          return this.doctorService.updateDoctorPassword(doctorId, this.newPassword_field);
        }),
        // Catch errors from either getDoctorIdByName or updateDoctorPassword
        catchError(err => {
          if (err.status === 404) {
            this.message = `Doctor "${this.doctorname}" not found. Cannot update password.`;
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
