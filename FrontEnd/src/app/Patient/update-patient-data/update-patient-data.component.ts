import { PatientService } from "../../../services/patient.service";
import { FamilyService } from "../../../services/family.service";
import { Router } from "@angular/router";
import { Location } from "@angular/common";
import {Component, OnInit} from '@angular/core';
import { Patient } from "../patient.model";

@Component({
  selector: 'app-update-patient-data',
  templateUrl: './update-patient-data.component.html',
  styleUrls: ['./update-patient-data.component.css']
})

export class UpdatePatientDataComponent implements OnInit {

  foundPatient: Patient | undefined;
  errorMessage: string | undefined;

  updatePatient = {
    id: 0,
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

  updatedData = {
  };

  constructor(private patientService: PatientService,
              private router: Router,
              private location : Location) {
  }

  ngOnInit(): void {
    this.findPatient();

  }

  findPatient(): void {
    this.patientService.getPatientByName('Tom Fred Jr.').subscribe(
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

    console.log('Data to be sent for update:',  this.updatedData);

    console.log ('1/4');

    if (!this.foundPatient) {
      this.errorMessage = "No patient data to update.";
      return;
    }

    console.log ('2/4');

    // Basic validation
    if (!this.updatePatient.age || this.updatePatient.age < 60 || this.updatePatient.age > 120) {
      this.errorMessage = "Age must be between 60 and 120.";
      return;
    }

    console.log ('3/4');

    if (!this.updatePatient.phoneNumber || this.updatePatient.phoneNumber.length !== 8 || !/^\d+$/.test(this.updatePatient.phoneNumber)) {
      this.errorMessage = "Phone number must be an 8-digit number.";
      return;
    }

    console.log ('4/4');

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
    if (!this.updatePatient.password || !passwordRegex.test(this.updatePatient.password)) {
      this.errorMessage = "Password must be at least 6 characters and contain at least one lowercase letter, one uppercase letter, one number, and one special character.";
      return;
    }

    this.patientService.patchPatient(this.foundPatient.id, this.updatedData = {

      id : this.foundPatient.id,
      patientName: this.foundPatient.patientName,
      doctorname : this.foundPatient.doctorname,
      medicalCondition : this.foundPatient.medicalCondition,
      gender: this.foundPatient.gender,
      insurance: this.foundPatient.insurance,
      age: this.updatePatient.age,
      address: this.updatePatient.address,
      medical_state: this.updatePatient.medical_state,
      phoneNumber : this.updatePatient.phoneNumber,
      password: this.updatePatient.password,
      about_me: this.updatePatient.about_me

    }).subscribe(

    (response) => {

      console.log ('Patient updated successfully:', response);
      console.log ('Body is : ' + this.updatedData);
     // this.router.navigate(['/home']);
    },
  (error) => {
      console.error('Error updating patient:', error);
      this.errorMessage = 'Failed to update patient.';
      console.log(this.errorMessage, error);
    });
  }
}
