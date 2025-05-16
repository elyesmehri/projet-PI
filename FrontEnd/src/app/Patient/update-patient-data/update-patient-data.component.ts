import { PatientService } from "../../../services/patient.service";
import { FamilyService } from "../../../services/family.service";
import { Router } from "@angular/router";
import { Location } from "@angular/common";
import { Component } from '@angular/core';
import { Patient } from "../patient.model";

@Component({
  selector: 'app-update-patient-data',
  templateUrl: './update-patient-data.component.html',
  styleUrls: ['./update-patient-data.component.css']
})

export class UpdatePatientDataComponent {


  foundPatient: Patient | undefined;
  errorMessage: string | undefined;

  updatePatient = {

    doctorname: '',
    patientName: '',
    age: 0,
    gender: false,
    address: '',
    phoneNumber: '',
    medical_state: '',
    password: '',
    confirmPassword: '',
    about_me: ''
  };

  constructor(private patientService: PatientService,
              private router: Router,
              private location : Location) {
  }

  ngOnInit(): void {
    this.findPatient();
  }

  findPatient(): void {
    this.patientService.getPatientByName('Lucas White').subscribe(
      (patient) => {
        this.foundPatient = patient;
        console.log("This patient data : ", this.foundPatient);
      },
      (error) => {
        console.error('Error finding patient:', error);
        this.errorMessage = 'Could not find patient.';
      }
    );
  }

  goHome(): void {
    this.location.back(); // Navigate to the home route
  }

  onSubmit(): void {
    if (!this.foundPatient) {
      this.errorMessage = "No patient data to update.";
      return;
    }

    // Basic validation
    if (!this.updatePatient.age || this.updatePatient.age < 60 || this.updatePatient.age > 120) {
      this.errorMessage = "Age must be between 60 and 120.";
      return;
    }

    if (!this.updatePatient.phoneNumber || this.updatePatient.phoneNumber.length !== 8 || !/^\d+$/.test(this.updatePatient.phoneNumber)) {
      this.errorMessage = "Phone number must be an 8-digit number.";
      return;
    }

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
    if (!this.updatePatient.password || !passwordRegex.test(this.updatePatient.password)) {
      this.errorMessage = "Password must be at least 6 characters and contain at least one lowercase letter, one uppercase letter, one number, and one special character.";
      return;
    }
  }


}
