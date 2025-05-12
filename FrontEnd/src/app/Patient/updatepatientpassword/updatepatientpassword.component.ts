import { Component } from '@angular/core';
import { PatientService } from "../../../services/patient.service";
import { Router} from "@angular/router";

@Component({
  selector: 'app-updatepatientpassword',
  templateUrl: './updatepatientpassword.component.html',
  styleUrls: ['./updatepatientpassword.component.css']
})
export class UpdatepatientpasswordComponent {


  // Regex: Min 8 chars, 1 uppercase, 1 lowercase, 1 special char
  passwordPattern: string = '^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$';

  newPatientName: string = '';
  newPassword: string = '@AZE00aze@';

  message: string = '';


  constructor(private patientService: PatientService, private router: Router) {
  }

  onUpdatePassword() {

    console.log ("Patient : " + this.newPatientName);
    console.log ("Password : " + this.newPassword);


    this.patientService.updatePassword(this.newPatientName, this.newPassword).subscribe(
      (response) => {
        console.log('Password updated successfully', response);
        // You can navigate to another page after success
      },
      (error) => {
        console.error('Error updating password', error);
        // Handle the error appropriately
      }
    );
  }
}
