import { Component } from '@angular/core';
import {PatientService} from "../../../services/patient.service";
import {Router} from "@angular/router"; // Added for navigation

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

  constructor(private patientService: PatientService, private router: Router) {}

  onLogin(): void {

    this.patientService.Login(this.patientName, this.password).subscribe(
      (success) => {
        if (success) {
          this.router.navigate(['/patientarea']);
        } else {
          this.errorMessage = 'Invalid credentials';
        }
      },
      (error) => {
        this.errorMessage = 'Error: ' + error.message;
      }
    );
  }

}
